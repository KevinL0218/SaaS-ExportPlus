package cn.itcast.web.controller.stat;

import cn.itcast.dao.stat.StatDao;
import cn.itcast.service.stat.StatService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {
    @Reference
    private StatService statService;

    /**
     * 进入统计分析的页面：stat-factory.jsp、stat-online.jsp、stat-sell.jsp
     * http://localhost:8080/stat/toCharts.do?chartsType=factory
     * http://localhost:8080/stat/toCharts.do?chartsType=sell
     * http://localhost:8080/stat/toCharts.do?chartsType=online
     */
    @RequestMapping("/toCharts")
    public String toCharts(String chartsType) {//factory
        return "stat/stat-"+chartsType;
    }

    // stat-factory.jsp 生产厂家页面异步请求返回json格式数据
    @RequestMapping("/factroySale")
    @ResponseBody
    public List<Map<String, Object>> factroySale(){
        List<Map<String, Object>> list = statService.factorySale(getLoginCompanyId());
        return list;
    }

    // stat-sell.jsp 页面异步请求返回json格式数据
    @RequestMapping("/productSale")
    @ResponseBody
    public List<Map<String, Object>> productSale(){
        List<Map<String, Object>> list = statService.productSale(getLoginCompanyId(),5);
        return list;
    }


    // stat-online.jsp 页面异步请求返回json格式数据
    @RequestMapping("/online")
    @ResponseBody
    public List<Map<String, Object>> online(){
        List<Map<String, Object>> list = statService.online();
        return list;
    }
}
