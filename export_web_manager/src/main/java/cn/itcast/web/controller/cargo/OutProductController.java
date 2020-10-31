package cn.itcast.web.controller.cargo;

import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.vo.ContractProductVo;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.log4j.Log4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/cargo/contract")
@Log4j
public class OutProductController extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    /**
     * 出货表导出，进入导出页面（可以选择船期时间）
     */
    @RequestMapping("/print")
    public String print(){
        return "cargo/print/contract-print";
    }

    /**
     * 出货表导出 A  普通导出 XSSF
     */
    @RequestMapping("/printExcel_11")
    @ResponseBody
    public void printExcel_11(String inputDate) throws IOException {
        //1. 导出第一行
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        // 合并单元格：开始行、结束行、开始列、结束列（0、0、1、8）
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
        // 设置行宽度
        sheet.setColumnWidth(0,256*5);
        sheet.setColumnWidth(1,256*21);
        sheet.setColumnWidth(2,256*9);
        sheet.setColumnWidth(3,256*11);
        sheet.setColumnWidth(4,256*15);
        sheet.setColumnWidth(5,256*26);
        sheet.setColumnWidth(6,256*15);
        sheet.setColumnWidth(7,256*15);
        sheet.setColumnWidth(8,256*15);
        // 创建第一行
        Row row = sheet.createRow(0);
        // 设置行高
        row.setHeightInPoints(36);
        // 创建第一行第二列
        Cell cell = row.createCell(1);
        // inputDate = 2020-09
        String bigTitle = inputDate.replaceAll("-0","-").replace("-","年") + "月份出货表";
        // 设置内容
        cell.setCellValue(bigTitle);
        // 设置单元格样式
        cell.setCellStyle(this.bigTitle(workbook));

        //2. 导出第二行
        String[] titles = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        row = sheet.createRow(1);
        for (int i = 0; i < titles.length; i++) {
            cell = row.createCell(i+1);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(this.title(workbook));
        }

        //3. 导出数据行
        List<ContractProductVo> list = contractProductService.findByShipTime(inputDate+"%");
        if (list != null && list.size()>0) {
            int index = 2;
            for (ContractProductVo cp : list) {
                // 创建行，从第3行开始
                row = sheet.createRow(index++);

                cell = row.createCell(1);
                cell.setCellValue(cp.getCustomName());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(2);
                cell.setCellValue(cp.getContractNo());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(3);
                cell.setCellValue(cp.getProductNo());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(4);
                cell.setCellValue(cp.getCnumber());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(5);
                cell.setCellValue(cp.getFactoryName());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(6);
                cell.setCellValue(cp.getDeliveryPeriod());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(7);
                cell.setCellValue(cp.getShipTime());
                cell.setCellStyle(this.text(workbook));

                cell = row.createCell(8);
                cell.setCellValue(cp.getTradeTerms());
                cell.setCellStyle(this.text(workbook));
            }
        }

        //4. 导出下载
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        // 设置下载响应头
        response.setHeader("content-disposition","attachment;fileName=export.xlsx");
        // 把excel文件流，写入到哪个输出流？ response输出流
        workbook.write(response.getOutputStream());
        workbook.close();
    }


    /**
     * 出货表导出 B  模板导出：读取一个有样式的excel模板，往其中填充数据
     */
    @RequestMapping("/printExcel_22")
    @ResponseBody
    public void printExcel_22(String inputDate) throws IOException {
        // 【获取excel文件流，传入工作簿】
        InputStream in = session.getServletContext()
                .getResourceAsStream("/make/xlsprint/tOUTPRODUCT.xlsx");
        Workbook workbook = new XSSFWorkbook(in);

        // 【获取工作表】
        Sheet sheet = workbook.getSheetAt(0);

        // 【获取第一行】
        Row row = sheet.getRow(0);
        // 【获取第一行第二列】
        Cell cell = row.getCell(1);
        String bigTitle = inputDate.replaceAll("-0","-").replace("-","年") + "月份出货表";
        cell.setCellValue(bigTitle);

        //【获取第三行的每一个单元格的样式】
        CellStyle[] cellStyles = new CellStyle[8];
        row = sheet.getRow(2);
        for (int i = 0; i < cellStyles.length; i++) {
            cellStyles[i] = row.getCell(i+1).getCellStyle();
        }

        //3. 导出数据行
        List<ContractProductVo> list = contractProductService.findByShipTime(inputDate+"%");
        if (list != null && list.size()>0) {
            int index = 2;
            for (ContractProductVo cp : list) {
                // 创建行，从第3行开始
                row = sheet.createRow(index++);

                cell = row.createCell(1);
                cell.setCellValue(cp.getCustomName());
                cell.setCellStyle(cellStyles[0]);

                cell = row.createCell(2);
                cell.setCellValue(cp.getContractNo());
                cell.setCellStyle(cellStyles[1]);

                cell = row.createCell(3);
                cell.setCellValue(cp.getProductNo());
                cell.setCellStyle(cellStyles[2]);

                cell = row.createCell(4);
                cell.setCellValue(cp.getCnumber());
                cell.setCellStyle(cellStyles[3]);

                cell = row.createCell(5);
                cell.setCellValue(cp.getFactoryName());
                cell.setCellStyle(cellStyles[4]);

                cell = row.createCell(6);
                cell.setCellValue(cp.getDeliveryPeriod());
                cell.setCellStyle(cellStyles[5]);

                cell = row.createCell(7);
                cell.setCellValue(cp.getShipTime());
                cell.setCellStyle(cellStyles[6]);

                cell = row.createCell(8);
                cell.setCellValue(cp.getTradeTerms());
                cell.setCellStyle(cellStyles[7]);
            }
        }

        //4. 导出下载
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        // 设置下载响应头
        response.setHeader("content-disposition","attachment;fileName=export.xlsx");
        // 把excel文件流，写入到哪个输出流？ response输出流
        workbook.write(response.getOutputStream());
        workbook.close();
    }



    /**
     * 出货表导出 C  SXSSF 导出大文件数据，避免内存溢出
     */
    @RequestMapping("/printExcel")
    @ResponseBody
    public void printExcel(String inputDate) throws IOException {
        //1. 导出第一行
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        // 合并单元格：开始行、结束行、开始列、结束列（0、0、1、8）
        sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
        // 设置行宽度
        sheet.setColumnWidth(0,256*5);
        sheet.setColumnWidth(1,256*21);
        sheet.setColumnWidth(2,256*9);
        sheet.setColumnWidth(3,256*11);
        sheet.setColumnWidth(4,256*15);
        sheet.setColumnWidth(5,256*26);
        sheet.setColumnWidth(6,256*15);
        sheet.setColumnWidth(7,256*15);
        sheet.setColumnWidth(8,256*15);
        // 创建第一行
        Row row = sheet.createRow(0);
        // 设置行高
        row.setHeightInPoints(36);
        // 创建第一行第二列
        Cell cell = row.createCell(1);
        // inputDate = 2020-09
        String bigTitle = inputDate.replaceAll("-0","-").replace("-","年") + "月份出货表";
        // 设置内容
        cell.setCellValue(bigTitle);
        // 设置单元格样式
        cell.setCellStyle(this.bigTitle(workbook));

        //2. 导出第二行
        String[] titles = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
        row = sheet.createRow(1);
        for (int i = 0; i < titles.length; i++) {
            cell = row.createCell(i+1);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(this.title(workbook));
        }

        //3. 导出数据行
        List<ContractProductVo> list = contractProductService.findByShipTime(inputDate+"%");
        if (list != null && list.size()>0) {
            int index = 2;
            for (ContractProductVo cp : list) {
                for (int i = 1; i < 10000; i++) {
                    // 创建行，从第3行开始
                    row = sheet.createRow(index++);

                    cell = row.createCell(1);
                    cell.setCellValue(cp.getCustomName());

                    cell = row.createCell(2);
                    cell.setCellValue(cp.getContractNo());

                    cell = row.createCell(3);
                    cell.setCellValue(cp.getProductNo());

                    cell = row.createCell(4);
                    cell.setCellValue(cp.getCnumber());

                    cell = row.createCell(5);
                    cell.setCellValue(cp.getFactoryName());

                    cell = row.createCell(6);
                    cell.setCellValue(cp.getDeliveryPeriod());

                    cell = row.createCell(7);
                    cell.setCellValue(cp.getShipTime());

                    cell = row.createCell(8);
                    cell.setCellValue(cp.getTradeTerms());
                }
            }
        }

        //4. 导出下载
        // 设置编码
        response.setCharacterEncoding("UTF-8");
        // 设置下载响应头
        response.setHeader("content-disposition","attachment;fileName=export.xlsx");
        // 把excel文件流，写入到哪个输出流？ response输出流
        workbook.write(response.getOutputStream());
        workbook.close();
    }









    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }
}
