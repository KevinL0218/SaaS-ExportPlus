package cn.itcast.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class App07 {

    /**
     * 写
     * |-- Workbook 接口
     *    |-- HSSFWorkbook  操作excel03
     *    |-- XSSFWorkbook  操作excel07
     */
    @Test
    public void write() throws Exception {
        //1. 创建工作簿
        Workbook workbook = new XSSFWorkbook();
        //2. 创建工作表
        Sheet sheet = workbook.createSheet();
        //3. 创建行: 第一行
        Row row = sheet.createRow(0);
        //4. 创建单元格： 第一行的第一列
        Cell cell = row.createCell(0);
        //5. 设置内容
        cell.setCellValue("第一行第一列");
        //6. 导出
        workbook.write(new FileOutputStream("f://test.xlsx"));
        workbook.close();
    }

    // 读
    @Test
    public void read() throws Exception {
        //1. 根据文件流，创建工作簿
        Workbook workbook = new XSSFWorkbook(new FileInputStream("f://test.xlsx"));
        //2. 获取工作簿
        Sheet sheet = workbook.getSheetAt(0);
        //3. 获取第一行
        Row row = sheet.getRow(0);
        //4. 获取第一行第一列
        Cell cell = row.getCell(0);
        //5. 获取内容
        System.out.println("第一行第一列：" + cell.getStringCellValue());
        System.out.println("获取总行数："+sheet.getPhysicalNumberOfRows());
        System.out.println("获取第一行总列数：" + row.getPhysicalNumberOfCells());

        workbook.close();
    }
}
