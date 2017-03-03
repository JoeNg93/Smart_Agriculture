/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import javax.swing.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSheetConditionalFormatting;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author DungNguyenz
 */
public class TestOutputExcel {

    private DateFormat dayFormat;
    private DateFormat outputFormat;
    private DateFormat timeFormat;
    private boolean isNewDay;
    private List<Integer> list;
    private FileOutputStream out;
    private String day;
    private int count;
    private HSSFWorkbook workbook;
    private boolean fileExist;
    private Map<Date, Integer> map;

    public TestOutputExcel() {
        dayFormat = new SimpleDateFormat("dd-MM-yy");

        outputFormat = new SimpleDateFormat("dd-MM-yy");

        timeFormat = new SimpleDateFormat("HH:mm:ss");

        isNewDay = false;

        list = new ArrayList<Integer>();

        day = outputFormat.format(new Date());
        
        map = new TreeMap<Date, Integer>();

        count = 0;

        fileExist = false;

        Timer time = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                map.put(new Date(), count);
                count++;
            }
        });
        time.start();

    }

    public void gogo() {
        checkNewDay();
        createNewFile();
        File file = new File("D:\\Data" + day + ".xls");
        try {
            out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            System.out.println("Export file successfully");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void checkNewDay() {
        if (!day.equals(dayFormat.format(new Date()))) {
            day = dayFormat.format(new Date());
            map.clear();
            //fileExist = false;
        }
    }

    public void updateFile(File file) {
        FileInputStream fIP = null;
        try {
            fIP = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        try {
            workbook = new HSSFWorkbook(fIP);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        HSSFSheet sheet = workbook.getSheet("Data");

        int rowCount = list.size();
        int cellCount = 2;
        for (int i = sheet.getLastRowNum(); i < rowCount; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < cellCount; j++) {
                Cell cell = row.createCell(j);
                if (j == 0) {
                    cell.setCellValue(timeFormat.format(new Date()));
                } else if (j == 1) {
                    cell.setCellValue(list.get(i));
                }
            }
        }
        list.clear();
//        try {
//            fIP.close();
//        } catch (IOException ex) {
//            Logger.getLogger(TestOutputExcel.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void createNewFile() {
        //fileExist = true;

        workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        String[] firstRow = new String[]{"Time", "Count"};

        Set<Date> keySet = map.keySet();
        int rowID = 0;

        /* Version 1 */
//        for (Date date : keySet) {
//            Row row = sheet.createRow(rowID++);
//            int cellCount = 2;
//            for (int i = 0; i < cellCount; i++) {
//                Cell cell = row.createCell(i);
//                if (rowID == 0) {
//                    cell.setCellValue(firstRow[i]);
//                } else {
//                    if (i == 0)
//                        cell.setCellValue(timeFormat.format(date));
//                    else if (i == 1)
//                        cell.setCellValue(map.get(date));
//                }
//            }
//        }
        
        /* Version 2*/
        for (Entry<Date, Integer> entry : map.entrySet()) {
            Row row = sheet.createRow(rowID++);
            int cellCount = 2;
            for (int i = 0; i < cellCount; i++) {
                Cell cell = row.createCell(i);
                if (rowID == 0) {
                    cell.setCellValue(firstRow[i]);
                } else {
                    if (i == 0)
                        cell.setCellValue(timeFormat.format(entry.getKey()));
                    else if (i == 1)
                        cell.setCellValue(entry.getValue());
                }
            }
        }

    }

}
