package com.review.task.utils;

import com.review.task.entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ExcelUtil {

    String SHEET_NAME = "Users";
    List<String> HEADERS = List.of("Id", "UserName", "Name", "DOB", "Gender", "Address", "Contact Number", "PinCode", "Access Role");

    static byte[] writeUserIntoExcel(List<User> employees) {
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = getSheetWithHeaders(workbook);

            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

            int rowCount = 1;
            for (User user : employees) {
                Row rowData = sheet.createRow(rowCount);
                rowData.createCell(0).setCellValue(user.getId());
                rowData.createCell(1).setCellValue(user.getUsername());
                rowData.createCell(2).setCellValue(user.getName());

                rowData.createCell(3).setCellStyle(dateStyle);
                rowData.getCell(3).setCellValue(user.getDob());

                rowData.createCell(4).setCellValue(user.getGender().toString());
                rowData.createCell(5).setCellValue(user.getAddress());

                rowData.createCell(6).setCellValue(user.getContactNumber());
                rowData.createCell(7).setCellValue(user.getPinCode());
                rowData.createCell(8).setCellValue(user.getAccessRole().toString());
                rowCount++;
            }
            for (int i = 0; i < employees.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Sheet getSheetWithHeaders(Workbook workbook) {
        Sheet sheet = workbook.createSheet(SHEET_NAME);
        Row row = sheet.createRow(0);
        for (int i = 0; i < HEADERS.size(); i++) {
            row.createCell(i).setCellValue(HEADERS.get(i));
        }
        return sheet;
    }


}
