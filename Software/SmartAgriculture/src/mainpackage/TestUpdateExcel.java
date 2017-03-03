/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DungNguyenz
 */
public class TestUpdateExcel extends Thread {

    private XSSFWorkbook workBook;
    private XSSFSheet sheet;
    private int rowID;

    private File file;
    private FileInputStream fileInputStream;
    private FileOutputStream fileOutputStream;
    
    private String disk;
    private String folderName;
    private String fileName;
    
    private DateFormat dateFormat;
    
    private ArrayList<Integer> list;

    public TestUpdateExcel() {
        workBook = new XSSFWorkbook();
        sheet = workBook.createSheet("Data");

        rowID = 0;
        
        list = new ArrayList<>();
        
         dateFormat = new SimpleDateFormat("EEE, dd-MM-yyyy");
        
        disk = "D:";
        folderName = "Environment Data";
        fileName = dateFormat.format(new Date()) + ".xlsx";
        file = new File(String.format("%s\\%s\\%s", disk, folderName, fileName));
//        try {
//            fileOutputStream = new FileOutputStream(file);
//            workBook.write(fileOutputStream);
//            fileOutputStream.close();
//
//            fileInputStream = new FileInputStream(file);
//            workBook = new XSSFWorkbook(fileInputStream);
//            sheet = workBook.getSheet("Data");
//            XSSFRow row = sheet.createRow(rowID);
//            XSSFCell cell = row.createCell(rowID);
//            cell.setCellValue(5);
//            fileInputStream.close();
//        }
//        catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }

        start();
    }

    public void run() {
        while (true) {
            
            for (int test: list) {
                System.out.println(test);
            }
            
            if (!file.exists()) {
                Row row = sheet.createRow(rowID);
                Cell cell = row.createCell(0);
                cell.setCellValue(rowID);
                writeFile();
            }
            else if (file.exists()) {
                workBook = readFile();
                sheet = workBook.getSheet("Data");
                rowID = sheet.getLastRowNum();
                System.out.println("Row ID = " + rowID);
                rowID++;
                Row row = sheet.createRow(rowID);
                Cell cell = row.createCell(0);
                cell.setCellValue(rowID);
                writeFile();
            }

            try {
                Thread.sleep(500);
            }
            catch (InterruptedException ex) {
                System.out.println("Exceptionnnn thread");
            }
        }
    }

    public void writeFile() {
        try {
            fileOutputStream = new FileOutputStream(file);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
    }

    public XSSFWorkbook readFile() {
        XSSFWorkbook workBook = null;
        try {
            fileInputStream = new FileInputStream(file);
            workBook = new XSSFWorkbook(fileInputStream);
            fileInputStream.close();
        }
        catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        return workBook;
    }

    public static void main(String[] args) {
        TestUpdateExcel test = new TestUpdateExcel();
    }

}
