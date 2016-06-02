package com.samodeika.xls;

import com.samodeika.common.Dog;
import com.samodeika.common.Processor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XlsProcessorImpl implements Processor, XlsProcessor {

    @Override
    public List<Dog> processFile(InputStream in) {
        List<Dog> dogs = new ArrayList<>();
        try {
            XSSFWorkbook workbook= new XSSFWorkbook(in);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                // skip header
                if (isHeader(row)) {
                    continue;
                }
                Dog dog = new Dog();
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
                    if (i == 0) {
                        dog.setName(row.getCell(i).getStringCellValue());
                    } else if (i == 1) {
                        dog.setAge(Integer.parseInt(row.getCell(i).getStringCellValue()));
                    } else if (i == 2) {
                        dog.setWeight(Double.parseDouble(row.getCell(i).getStringCellValue()));
                    }
                }
                dogs.add(dog);
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return dogs;
    }

    private static boolean isHeader(Row row) {
        row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
        String s = row.getCell(1).getStringCellValue();
        return s.contains("Name") || s.contains("Age") || s.contains("Weight");
    }

    @Override
    public String downloadFile(List<Dog> dogs) {

        return getXls(dogs).toString();
    }

    @Override
    public XSSFWorkbook getXls(List<Dog> dogs) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("persons");
        int rownum = 0;
        addHeader(sheet, rownum++);
        for (Dog dog: dogs) {
            XSSFRow row = sheet.createRow(rownum++);
            int column = 0;
            XSSFCell cell = row.createCell(column++);
            cell.setCellValue(dog.getName());
            cell = row.createCell(column++);
            cell.setCellValue(dog.getAge());
            cell = row.createCell(column++);
            cell.setCellValue(dog.getWeight());
        }

        return workbook;
    }

    private void addHeader(XSSFSheet sheet, int rownum) {
        XSSFRow row = sheet.createRow(rownum);
        int column = 0;
        XSSFCell cell = row.createCell(column++);
        cell.setCellValue("Name");
        cell = row.createCell(column++);
        cell.setCellValue("Age");
        cell = row.createCell(column++);
        cell.setCellValue("Weight");
    }

}
