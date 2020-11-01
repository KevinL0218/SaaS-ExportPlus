package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.Packing;
import cn.itcast.domain.cargo.ShippingOrder;
import cn.itcast.domain.cargo.ShippingOrderExample;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.service.cargo.PackingService;
import cn.itcast.service.cargo.ShippingOrderService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        criteria.andCreateByEqualTo(getLoginUser().getUserName());
        PageInfo<ShippingOrder> pageInfo = shippingOrderService.findByPage(shippingOrderExample, pageNum, pageSize);
        request.setAttribute("pageInfo",pageInfo);
        return "cargo/shipping/shipping-list";
    }

    /**
     *进入生产委托书
     */
    @RequestMapping("/toAdd")
    public String toAdd(String packingListId){
        //根据装箱id查询装箱单
        Packing packing = packingService.selectByPrimaryKey(packingListId);
        //获取报运单id集合
        String exportIds = packing.getExportIds();
        String[] split = exportIds.split(",");
        String exportId = split[0];
        //通过报运单id查询报运单
        Export export = exportService.findById(exportId);
        request.setAttribute("packingListId",packingListId);
        request.setAttribute("packing",packing);
        request.setAttribute("export",export);

        return "cargo/shipping/shipping-add";
    }

    /**
     *新增委托书
     */
    @RequestMapping("/add")
    public String add(ShippingOrder shippingOrder,String packingListId){

        shippingOrder.setShippingOrderId(packingListId);

        return "redirect:/cargo/shipping/list";
    }


    /**
     *查看
     */
    @RequestMapping("/toView")
    public String toView(String shippingOrderId){

        return "cargo/shipping/shipping-view";
    }

}
