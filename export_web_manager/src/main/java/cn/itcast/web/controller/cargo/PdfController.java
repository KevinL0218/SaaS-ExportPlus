package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.Export;
import cn.itcast.domain.cargo.ExportProduct;
import cn.itcast.domain.cargo.ExportProductExample;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.vo.ContractProductVo;
import cn.itcast.web.controller.BaseController;
import cn.itcast.web.util.BeanMapUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.log4j.Log4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/cargo/export")
@Log4j
public class PdfController extends BaseController {

    /**
     * 导出PDF（1） HelloWorld  + 中文字体
     */
    @RequestMapping("/exportPdf1")
    @ResponseBody  // 不进行跳转
    public void exportPdf1(HttpServletRequest request) throws Exception {

        //1. 获取jasper文件流
        InputStream in = request.getSession()
                .getServletContext().getResourceAsStream("/jasper/test02.jasper");
        //2. 创建JasperPrint对象，用于往模板中填充数据
        //参数1：jasper文件流
        //参数2：通过map方式往模板中填充数据
        //参数3：通过数据源的方式往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, new HashMap<>(), new JREmptyDataSource());
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        in.close();
    }

    /**
     * 导出PDF（2） 往模板填充数据 A 通过map填充
     * 注意：map的key对应的是模板设置中的Parameter参数名称
     */
    @RequestMapping("/exportPdf2")
    @ResponseBody  // 不进行跳转
    public void exportPdf2(HttpServletRequest request) throws Exception {

        //1. 获取jasper文件流
        InputStream in = request.getSession()
                .getServletContext().getResourceAsStream("/jasper/test03_parameter.jasper");

        //构造map集合
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "球球");
        map.put("email", "ballball@export.com");
        map.put("companyName", "字节跳动");
        map.put("deptName", "研发部");

        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, new JREmptyDataSource());
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        in.close();
    }


    @Autowired
    private DataSource dataSource;

    /**
     * 导出PDF（3） 往模板填充数据 B 数据源填充（1）jdbc数据源
     * 注意：map的key对应的是模板设置中的Parameter参数名称
     */
    @RequestMapping("/exportPdf3")
    @ResponseBody  // 不进行跳转
    public void exportPdf3(HttpServletRequest request) throws Exception {

        //1. 获取jasper文件流
        InputStream in = request.getSession()
                .getServletContext().getResourceAsStream("/jasper/test04_jdbc.jasper");

        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, null, dataSource.getConnection());
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        in.close();
    }

    /**
     * 导出PDF（4） 往模板填充数据 B 数据源填充（2）JavaBean数据源
     * 注意：map的key对应的是模板设置中的Parameter参数名称
     */
    @RequestMapping("/exportPdf4")
    @ResponseBody  // 不进行跳转
    public void exportPdf4(HttpServletRequest request) throws Exception {

        //1. 获取jasper文件流
        InputStream in = request.getSession()
                .getServletContext().getResourceAsStream("/jasper/test05_javabean.jasper");

        /**
         * 构造list集合，封装数据
         * 方式1：List<Map<String,Object>>  要求map的key要与模板中Field字段名称一致
         * 方式2：List<User>  要求对象的属性要与模板中Field字段名称一致
         */
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //构造map集合
            Map<String, Object> map = new HashMap<>();
            map.put("userName", "球球" + i);
            map.put("email", "ballball@export.com");
            map.put("companyName", "字节跳动");
            map.put("deptName", "研发部");
            // 添加到集合
            list.add(map);
        }

        // 构造集合对象，用于封装列表数据（对应的是模板中的Field）
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, null, dataSource);
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        in.close();
    }


    /**
     * 导出PDF（5） 分组报表，根据企业名称自动进行分组
     */
    @RequestMapping("/exportPdf5")
    @ResponseBody  // 不进行跳转
    public void exportPdf5(HttpServletRequest request) throws Exception {

        //1. 获取jasper文件流
        InputStream in = request.getSession()
                .getServletContext().getResourceAsStream("/jasper/test06_group.jasper");

        // 构造list集合，封装数据
        List<Map<String, Object>> list = new ArrayList<>();
        for (int j = 1; j <= 2; j++) { // 企业
            for (int i = 0; i < 5; i++) {
                //构造map集合
                Map<String, Object> map = new HashMap<>();
                map.put("userName", "球球" + i);
                map.put("email", "ballball@export.com");
                map.put("companyName", "字节跳动" + j);
                map.put("deptName", "研发部");
                // 添加到集合
                list.add(map);
            }
        }

        // 构造集合对象，用于封装列表数据（对应的是模板中的Field）
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, null, dataSource);
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        in.close();
    }


    /**
     * 导出PDF（6） 图形报表
     */
    @RequestMapping("/exportPdf6")
    @ResponseBody  // 不进行跳转
    public void exportPdf6(HttpServletRequest request) throws Exception {

        //1. 获取jasper文件流
        InputStream in = request.getSession()
                .getServletContext().getResourceAsStream("/jasper/test07_chart.jasper");

        // 构造list集合，封装数据
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //构造map集合
            Map<String, Object> map = new HashMap<>();
            // 注意：title、value对应的是模板中的Field，且类型也要对应
            map.put("title", "球球" + i);
            map.put("value", new Random().nextInt(100));

            // 添加到集合
            list.add(map);
        }

        // 构造集合对象，用于封装列表数据（对应的是模板中的Field）
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //2. 创建JasperPrint对象，用于往模板中填充数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, null, dataSource);
        //3. 导出
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        in.close();
    }

    @Reference
    private ExportService exportService;
    @Reference
    private ExportProductService exportProductService;
    /**
     * 导出PDF, 导出报运详情
     */
    @RequestMapping("/exportPdf")
    @ResponseBody  // 不进行跳转
    public void exportPdf(String id) throws Exception {

        //1. 获取jasper文件流
        InputStream in = request.getSession()
                .getServletContext().getResourceAsStream("/jasper/export.jasper");

        //A. 根据包运单id，查询获取报运单对象
        Export export = exportService.findById(id);
        // 对象转换为map
        Map<String, Object> map = BeanMapUtils.beanToMap(export);

        //B. 根据报运单id，查询商品
        ExportProductExample example = new ExportProductExample();
        example.createCriteria().andExportIdEqualTo(id);
        // 注意：集合中对象的属性，要于Field中的字段名称一致
        List<ExportProduct> list = exportProductService.findAll(example);

        // 构造集合对象，用于封装列表数据（对应的是模板中的Field）
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);

        //2. 创建JasperPrint对象，用于往模板中填充数据
        // 参数2：通过map封装报运单，填充到模板
        // 参数3：通过集合封装商品，填充到模板
        JasperPrint jasperPrint = JasperFillManager.fillReport(in, map, dataSource);
        //3. 导出、下载
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-disposition","attachment;fileName=export.pdf");
        ServletOutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        in.close();
    }


}
