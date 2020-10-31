package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.service.cargo.PackingService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Log4j
@RequestMapping("/cargo/packing")
public class PackingController extends BaseController {

    // 合同
    @Reference
    private ExportService exportService;
    // 合同货物
    @Reference
    private ExportProductService exportProductService;
    // 装箱
    @Reference
    private PackingService packingService;

    /**
     * 进入添加装箱页面前判断
     */
    @RequestMapping("/goPacking")
    @ResponseBody
    public Map<String, String> goPacking(String exportIds){
        Map<String, String> map = new HashMap<>();
        List<String> Ids = Arrays.asList(exportIds.split(","));
        if (Ids != null && Ids.size() > 0) {
            for (String id : Ids) {
                Export export = exportService.findById(id);
                if (export.getState() != 2) {
                    String id1 = export.getId();
                    map.put("flag", "1");
                    map.put("msg", "选择的报运单[" + id1 + "]非已保运状态！");
                    return map;
                }
            }
            // 查询出第一个报运单，用于比较
            Export exporte = exportService.findById(Ids.get(0));
            // 所有报运单的装运港
            String shipmentPort = exporte.getShipmentPort();
            // 所有报运单的目的港
            String destinationPort = exporte.getDestinationPort();
            // 所有报运单的收货人
            String consignee = exporte.getConsignee();
            // 根据报运单id查询所有报运单
            for (int i = 0; i < Ids.size(); i++) {
                // 查询所有报运单
                Export export = exportService.findById(Ids.get(i));
                    /* 这里根据多个出口报运单进行的装箱，有一个前提：多个报运单的装运港、目的港、收货人必须一致
                    （三个字段分别是shipment_port、destination_port、consignee）*/
                if (!shipmentPort.equals(export.getShipmentPort())) {
                    map.put("flag", "1");
                    map.put("msg", "多个报运单的装运港不同.");
                    return map;
                }
                if (!destinationPort.equals(export.getDestinationPort())) {
                    map.put("flag", "1");
                    map.put("msg", "多个报运单的目的港不同.");
                    return map;
                }
                if (!consignee.equals(export.getConsignee())) {
                    map.put("flag", "1");
                    map.put("msg", "多个报运单的收货人不同.");
                    return map;
                }
            }
        }
        return map;
    }

    /**
     * 进入添加装箱页面
     */
    @RequestMapping("/toPacking")
    public ModelAndView toPacking(String exportIds) {
        ModelAndView model = new ModelAndView();
        Packing packing = new Packing();
        try {
            log.info("执行进入装箱页面开始...");
            // 报运单中合同的合同号
            String exportNos = "";
            List<String> Ids = Arrays.asList(exportIds.split(","));
            // 装箱中的所有报运单的总体积
            Double totalVolume = 0d;
            // 装箱中的所有报运单的总净重
            Double netWeights = 0d;
            // 装箱中的所有报运单的总毛重
            Double grossWeights = 0d;
            // 装箱费用
            Double packingMoney = 0d;
            for (String id : Ids) {
                Export export = exportService.findById(id);
                // 根据报运单id查询所有货物
                ExportProductExample exportProductExample = new ExportProductExample();
                exportProductExample.createCriteria().andExportIdEqualTo(export.getId());
                List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);
                if (exportProductList != null && exportProductList.size() > 0) {
                    for (ExportProduct exportProduct : exportProductList) {
                        // 计算装箱中的所有报运单的总毛重
                        if (exportProduct.getGrossWeight() != null){
                            grossWeights += exportProduct.getGrossWeight() * exportProduct.getCnumber();
                        }
                        // 计算装箱中的所有报运单的总净重
                        if (exportProduct.getNetWeight() != null){
                            netWeights += exportProduct.getNetWeight() * exportProduct.getCnumber();
                        }
                        // 计算装箱中的所有报运单的总体积
                        if (exportProduct.getSizeHeight() != null && exportProduct.getSizeLength() != null && exportProduct.getSizeWidth() != null){
                            totalVolume += exportProduct.getSizeHeight() * exportProduct.getSizeWidth() * exportProduct.getSizeLength() * exportProduct.getCnumber();
                        }
                        // 储存所有报运单中的合同号
                        exportNos += export.getCustomerContract() + ",";
                    }
                }
            }
            // 计算装箱费用
            packingMoney = grossWeights * 520;
            // 给装箱单对象赋值，用于页面显示
            exportIds = exportIds.substring(0, exportIds.length() - 1);
            packing.setExportIds(exportIds);
            packing.setTotalVolume(totalVolume);
            packing.setGrossWeights(grossWeights);
            packing.setNetWeights(netWeights);
            packing.setPackingMoney(packingMoney);
            // 把合同号中的空格转换为逗号
            exportNos = exportNos.replaceAll(" ,", ",");
            // 去掉合同号字符串最后一个逗号
            exportNos = exportNos.substring(0, exportNos.length() - 1);
            packing.setExportNos(exportNos);
            model.setViewName("cargo/packing/pack-toPack");
            model.addObject("packing", packing);
            log.info("执行进入装箱页面结束...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行进入装箱页面出现异常！", e);
        }
        return model;
    }

    /**
     * 装箱列表显示
     */
    @RequestMapping("/list")
    public ModelAndView list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize) {
        ModelAndView model = new ModelAndView();
        try {
            log.info("执行装箱列表显示开始...");
            PackingExample packingExample = new PackingExample();
            PageInfo<Packing> pageInfo = packingService.findByPage(packingExample, pageNum, pageSize);
            model.addObject("pageInfo", pageInfo);
            model.setViewName("cargo/packing/pack-list");
            log.info("执行装箱列表显示结束...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行装箱列表显示出现异常！", e);
        }
        return model;
    }

    /**
     * 添加装箱
     */
    @RequestMapping("/toAdd")
    public ModelAndView toAdd(Packing packing) {
        ModelAndView model = new ModelAndView();
        try {
            log.info("执行添加装箱开始...");
            packing.setCreateBy(getLoginUser().getId());
            packing.setCreateDept(getLoginUser().getDeptId());
            packing.setCompanyId(getLoginCompanyId());
            packing.setCompanyName(getLoginCompanyName());
            packingService.insertSelective(packing);
            model.setViewName("redirect:/cargo/packing/list");
            log.info("执行添加装箱结束...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行添加装箱出现异常！", e);
        }
        return model;
    }

    /**
     * 删除装箱
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Map<String, String> delete(String id) {
        Map<String, String> map = new HashMap<>();
        try {
            log.info("执行删除装箱开始...");
            boolean flag = packingService.deleteByPrimaryKey(id);
            if (flag){
                map.put("msg", "删除成功！");
            } else {
                map.put("msg", "删除失败：当前删除的装箱单已委托！");
            }
            log.info("执行删除装箱结束...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行删除装箱出现异常！", e);
        }
        return map;
    }

    /**
     * 查看装箱单信息
     */
    @RequestMapping("/toView")
    public ModelAndView toView(String id) {
        ModelAndView model = new ModelAndView();
        try {
            log.info("执行查看装箱单信息开始...");
            Packing packing = packingService.selectByPrimaryKey(id);
            model.addObject("packing", packing);
            model.setViewName("cargo/packing/pack-view");
            log.info("执行查看装箱单信息结束...");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("执行查看装箱单信息出现异常！", e);
        }
        return model;
    }

}
