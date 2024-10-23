package com.bbs.cloud.admin.common.util;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public class ExcelUtil<T> {

    public static void exportExcel(HttpServletResponse response,
                            String fileName,
                            String sheetName,
                            String[] headers,
                            String[] propertys,
                            List data) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        XSSFRow header = sheet.createRow(0);
        for(int i = 0; i < headers.length; i ++) {
            header.createCell(i).setCellValue(headers[i]);
        }
        int rowIndex = 1;


        for(Object object : data) {
            String json = JsonUtils.objectToJson(object);
            HashMap<String, String> map = JsonUtils.jsonToPojo(json, HashMap.class);
            XSSFRow row = sheet.createRow(rowIndex ++);
            for(int i = 0; i < propertys.length; i ++) {
                row.createCell(i).setCellValue(String.valueOf(map.get(propertys[i])));
            }
        }
        ServletOutputStream outputStream = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName +".xlsx");
            //将Excel文档写入响应流中
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
