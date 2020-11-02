package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.service.cargo.PackingService;
import cn.itcast.service.cargo.ShippingOrderService;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.util.BeanMapUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.io.InputStream;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("/cargo/shipping")
@Log4j
public class ShippingController extends BaseController {
    @Reference
    private ShippingOrderService shippingOrderService;
    @Reference
    private PackingService packingService;
    @Reference
    private ExportService exportService;

    /**
     * 委托管理，委托列表
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "5") Integer pageSize){
        //创建查询对象
        ShippingOrderExample shippingOrderExample = new ShippingOrderExample();
        // 根据创建时间倒序排序
        shippingOrderExample.setOrderByClause("create_time desc");
        ShippingOrderExample.Criteria criteria = shippingOrderExample.createCriteria();
        //添加查询条件
        criteria.andCreateByEqualTo(getLoginUser().getId());
        PageInfo<ShippingOrder> pageInfo = shippingOrderService.findByPage(shippingOrderExample, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);
        return "cargo/shipping/shipping-list";
    }


    /**
     *进入生产委托书前判断装箱状态
     */
    @RequestMapping("/judge")
    @ResponseBody
    public Boolean judge(String id){
        Packing packing = packingService.selectByPrimaryKey(id);
        if(packing.getState() == 0){
            return true;
        }
        return false;
    }

    /**
     *进入生产委托书
     */
    @RequestMapping("/toAdd")
    public String toAdd(String id){
        //根据装箱id查询装箱单
        Packing packing = packingService.selectByPrimaryKey(id);
        //获取报运单id集合
        String exportIds = packing.getExportIds();
        String[] split = exportIds.split(",");
        String exportId = split[0];
        //通过报运单id查询报运单
        Export export = exportService.findById(exportId);
        request.setAttribute("packingListId",id);
        request.setAttribute("packing",packing);
        request.setAttribute("export",export);

        return "cargo/shipping/shipping-add";
    }

    /**
     *新增委托书
     */
    @RequestMapping("/add")
    public String add(ShippingOrder shippingOrder,String packingListId){
        //获取装箱单
        Packing packing = packingService.selectByPrimaryKey(packingListId);

        //设置委托id、装期
        shippingOrder.setShippingOrderId(packingListId);
        shippingOrder.setLoadingDate(packing.getPackingTime());
        //设置效期
        long time = System.currentTimeMillis() + (24 * 60 * 60 * 1000);
        shippingOrder.setLimitDate(new Date(time));

        //设置份数、运输要求、运费说明
        shippingOrder.setCopyNum("1");
        shippingOrder.setSpecialCondition(packing.getDescription());
        shippingOrder.setFreight(packing.getPackingMoney().toString());

        //设置状态、创建人id，创建人所在部门id、创建日期
        shippingOrder.setState(0);
        shippingOrder.setCreateBy(getLoginUser().getId());
        shippingOrder.setCreateDept(getLoginUser().getDeptId());
        shippingOrder.setCreateTime(new Date());

        //添加委托单
        shippingOrderService.save(shippingOrder);

        //修改装箱单状态为1
        packing.setState(1);
        packingService.update(packing);


        // 修改报运单状态为已委托
        String exportIds = packing.getExportIds();
        if (exportIds != null && exportIds.length() > 0) {
            //获取报运单数组
            String[] split = exportIds.split(",");
            for (int i = 0; i < split.length; i++) {
                Export export = exportService.findById(split[0]);
                //修改报运单状态为4-已委托
                export.setState(4);
                exportService.update(export);
            }
        }
        return "redirect:/cargo/shipping/list";
    }


    /**
     *查看委托单
     */
    @RequestMapping("/toView")
    public String toView(String id){
        //通过委托单id获取委托单
        ShippingOrder shippingOrder = shippingOrderService.findById(id);
        //获取装箱单(装箱单id=委托单id)
        Packing packing = packingService.selectByPrimaryKey(id);
        //获取报运单id集合
        String exportIds = packing.getExportIds();
        String[] split = exportIds.split(",");
        String exportId = split[0];
        //通过报运单id查询报运单
        Export export = exportService.findById(exportId);
        request.setAttribute("shippingOrder",shippingOrder);
        request.setAttribute("packing",packing);
        request.setAttribute("export",export);
        return "cargo/shipping/shipping-view";
    }

    /**
     * 删除委托单
     */
    @RequestMapping("delete")
    @ResponseBody
    public Map<String,String> delete(String id){
        Map<String, String> map = new HashMap<>();
        boolean flag = shippingOrderService.delete(id);
        if(flag){
            map.put("msg","删除成功!");
        }
        else {
            map.put("msg","删除失败：当前删除的装箱单已开票,不能删除！");
        }
        //获取装箱单(装箱单id=委托单id),修改装箱单状态为0
        Packing packing = packingService.selectByPrimaryKey(id);
        packing.setState(0);
        packingService.update(packing);

        //修改报运单状态为2-已保运 （0-草稿 1-已上报 2-已报运 3-装箱 4-委托 5-发票 6-财务）
        String exportIds = packing.getExportIds();
        if(exportIds != null && exportIds.length()>0){
            String[] split = exportIds.split(",");
            for (int i = 0; i < split.length; i++) {
                Export export = exportService.findById(split[i]);
                export.setState(2);
                exportService.update(export);
            }
        }
        return map;
    }

    /**
     * 导出PDF, 导出报运详情
     */
    @RequestMapping("/shippingPdf")
    @ResponseBody  // 不进行跳转
    public void shippingPdf(String id) throws Exception {


        //1. 获取jasper文件流
        InputStream in = request.getSession()
                .getServletContext().getResourceAsStream("/jasper/shipping.jasper");

        //根据id查询委托单
        ShippingOrder shippingOrder = shippingOrderService.findById(id);
        String shippingOrderId = shippingOrder.getShippingOrderId();
        String orderType = shippingOrder.getOrderType();
        String shipper = shippingOrder.getShipper();
        String consignee = shippingOrder.getConsignee();
        String notifyParty = shippingOrder.getNotifyParty();
        Date createTime = shippingOrder.getCreateTime();
        String lcNo = shippingOrder.getLcNo();
        String portOfLoading = shippingOrder.getPortOfLoading();
        String portOfTrans = shippingOrder.getPortOfTrans();
        String portOfDischarge = shippingOrder.getPortOfDischarge();
        Date loadingDate = shippingOrder.getLoadingDate();
        Date limitDate = shippingOrder.getLimitDate();
        String isBatch = shippingOrder.getIsBatch();
        String isTrans = shippingOrder.getIsTrans();
        String copyNum = shippingOrder.getCopyNum();
        String remark = shippingOrder.getRemark();
        String specialCondition = shippingOrder.getSpecialCondition();
        String freight = shippingOrder.getFreight();

        //构造map集合
        Map<String,Object> map = new HashMap<>();
        map.put("shippingOrderId",shippingOrderId);
        map.put("orderType",orderType);
        map.put("shipper",shipper);
        map.put("consignee",consignee);
        map.put("notifyParty",notifyParty);
        map.put("createTime",createTime);
        map.put("lcNo",lcNo);
        map.put("portOfLoading",portOfLoading);
        map.put("portOfTrans",portOfTrans);
        map.put("portOfDischarge",portOfDischarge);
        map.put("loadingDate",loadingDate);
        map.put("limitDate",limitDate);
        map.put("isBatch",isBatch);
        map.put("isTrans",isTrans);
        map.put("copyNum",copyNum);
        map.put("remark",remark);
        map.put("specialCondition",specialCondition);
        map.put("freight",freight);



        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in,map,new JREmptyDataSource());
        //3. 导出、下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-disposition","attachment;fileName=shipping.pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());

        in.close();
    }
}
