/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import com.rapplogic.xbee.api.XBeeException;
import java.awt.event.*;
import java.awt.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import org.openstreetmap.gui.jmapviewer.JMapViewer;

/**
 *
 * @author DungNguyenz
 */
public class FrmXuatThongTin extends javax.swing.JFrame {

    /**
     * Creates new form FrmXuatThongTin
     */
    private MasterNode masterNode;
//    private DefaultTableModel model; // No longer use this, it was included in MasterNode class

    private Timer addDataGraph;

    private volatile boolean getData;

    private int zoomDistance;

    private volatile boolean displayMap;

    private RunThread getDataThread; // Main thread for getting data

    private OutputExcelThread outputExcelThread; // Thread uses for outputting data to Excel File

    private JPanel displayPanel;

    private DateFormat dateFormat;

    private DateFormat timeFormat;

    private final int ZOOM_SIZE = 18;

    private FrmPumpActivator frmPumpActivator; // Display the Frame for activating the pump

    public FrmXuatThongTin() throws XBeeException {

        initComponents();

        zoomDistance = 15;
        
        txtaTestPacket.setEditable(false);

        masterNode = new MasterNode();

        dateFormat = new SimpleDateFormat("EEE, dd-MM-yyyy");
        timeFormat = new SimpleDateFormat("HH:mm:ss");

        tblDatabase.setModel(masterNode.getModel().getTableModel());
        cbbGraphSelection.setModel(masterNode.getModel().getComboBoxModel());

        AddDataForGraph listenerGraph = new AddDataForGraph();
        addDataGraph = new Timer(5000, listenerGraph);

        getDataThread = new RunThread();
        outputExcelThread = new OutputExcelThread();
        getData = false;
        getDataThread.start();
        outputExcelThread.start();

        frmPumpActivator = new FrmPumpActivator();
        frmPumpActivator.getCbbChooseNode().setModel(masterNode.getModel().getComboBoxModel());
        frmPumpActivator.getBtnActivate().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                int nodeID = frmPumpActivator.getCbbChooseNode().getSelectedIndex() - 1;
                int nodeAddress = masterNode.getNodeList().get(nodeID).getAddress();

                int pumpSpeed = Integer.parseInt(frmPumpActivator.getTxtSpeed().getText());
                int pumpTime = Integer.parseInt(frmPumpActivator.getTxtTime().getText());

                masterNode.activeMotor(nodeAddress, pumpSpeed, pumpTime);
                frmPumpActivator.dispose();
            }
        });

        btnRunMotor.setToolTipText("Run the motor manually (only for node 0)");
        btnRunMotor.setEnabled(false);

        btnZoomIn.setEnabled(false);
        btnZoomOut.setEnabled(false);

        txtaDataInfo.setText("Press Start button to\nstart getting data");

        /* Display date and time */
        timeFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat = new SimpleDateFormat("EEE, dd-MM-yyyy");
        Thread displayTimeThread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    String time = timeFormat.format(new Date());
                    String date = dateFormat.format(new Date());
                    lblDisplayDate.setText(date + ", " + time);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException exc) {

                    }
                }
            }
        });
        displayTimeThread.start();
    }

    /* WARNING : initTable(), updateTable(), clearModel TEMPORARY NOT IN USE */
    /* Initialize the table, create columns */
    /*
     public void initTable() {
     String[] columnNames = new String[]{
     "Node ID","Address","Location","Temperature","Humidity","Timer"
     };
     for (String name : columnNames) {
     model.addColumn(name);
     }
     }
     */
    /* Update the table, create rows depend on database */
    /*
     public void updateTable() {
     clearModel();
     for (NodeInformation node : masterNode.getNodeList()) {
     String address = String.format("0x%x", node.getAddress());
     Object[] nodeData = new Object[] {
     node.getID(), address, node.getLocation().toString(), node.getTemperature(),
     node.getHumidity(), node.getInactivityTimer()
     };
     model.addRow(nodeData);           
     }
     }
     */
    /* Clear the table */
    /*
     public void clearModel() {
     for (int i = 0; i < model.getRowCount(); i++) {
     model.removeRow(i);
     }
     }
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnMap = new javax.swing.JPanel();
        map = new org.openstreetmap.gui.jmapviewer.JMapViewer();
        btnZoomIn = new javax.swing.JButton();
        btnZoomOut = new javax.swing.JButton();
        pnCommand = new javax.swing.JPanel();
        btnStartStop = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnRunMotor = new javax.swing.JButton();
        lblCommand = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaDataInfo = new javax.swing.JTextArea();
        lblDataInformation = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblDisplayDate = new javax.swing.JLabel();
        pnTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDatabase = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtaTestPacket = new javax.swing.JTextArea();
        pnGraph = new javax.swing.JPanel();
        cbbGraphSelection = new javax.swing.JComboBox();
        pnDisplayGraph = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblLogoBachKhoa = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        pnMap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnMap.add(map, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 510));

        btnZoomIn.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btnZoomIn.setText("Zoom in");
        btnZoomIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomInActionPerformed(evt);
            }
        });
        pnMap.add(btnZoomIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 520, -1, -1));

        btnZoomOut.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btnZoomOut.setText("Zoom out");
        btnZoomOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomOutActionPerformed(evt);
            }
        });
        pnMap.add(btnZoomOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 520, -1, -1));

        pnCommand.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));

        btnStartStop.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btnStartStop.setText("Start");
        btnStartStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartStopActionPerformed(evt);
            }
        });
        pnCommand.add(btnStartStop);

        btnExit.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btnExit.setText("Exit");
        btnExit.setMaximumSize(new java.awt.Dimension(85, 37));
        btnExit.setMinimumSize(new java.awt.Dimension(85, 37));
        btnExit.setPreferredSize(new java.awt.Dimension(85, 37));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });
        pnCommand.add(btnExit);

        btnRunMotor.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        btnRunMotor.setText("Run motor");
        btnRunMotor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRunMotorActionPerformed(evt);
            }
        });
        pnCommand.add(btnRunMotor);

        pnMap.add(pnCommand, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 50, 210, 100));

        lblCommand.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblCommand.setText("Command");
        pnMap.add(lblCommand, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 30, -1, -1));

        txtaDataInfo.setColumns(20);
        txtaDataInfo.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txtaDataInfo.setRows(5);
        jScrollPane1.setViewportView(txtaDataInfo);

        pnMap.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 190, 210, 270));

        lblDataInformation.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblDataInformation.setText("Node Information");
        pnMap.add(lblDataInformation, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 170, -1, -1));

        lblDate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblDate.setText("Date");
        pnMap.add(lblDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 480, -1, -1));
        pnMap.add(lblDisplayDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 510, -1, -1));

        jTabbedPane1.addTab("Map", pnMap);

        pnTable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDatabase.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblDatabase.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tblDatabase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Node ID", "Address", "Location", "Temperature", "Humidity", "Signal Strength", "Timer"
            }
        ));
        jScrollPane2.setViewportView(tblDatabase);

        pnTable.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 1080, 170));

        txtaTestPacket.setColumns(20);
        txtaTestPacket.setRows(5);
        jScrollPane3.setViewportView(txtaTestPacket);

        pnTable.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 300, 260));

        jTabbedPane1.addTab("Table", pnTable);

        pnGraph.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbbGraphSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "--Chọn Node--" }));
        cbbGraphSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbGraphSelectionActionPerformed(evt);
            }
        });
        pnGraph.add(cbbGraphSelection, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, -1, -1));

        pnDisplayGraph.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnGraph.add(pnDisplayGraph, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1130, 530));

        jTabbedPane1.addTab("Graph", pnGraph);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 117, 1140, 610));

        lblTitle.setFont(new java.awt.Font("Wide Latin", 1, 48)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 51, 51));
        lblTitle.setText("Smart Agriculture");
        getContentPane().add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        lblLogoBachKhoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/mainpackage/LogoBachKhoa.png"))); // NOI18N
        getContentPane().add(lblLogoBachKhoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 0, 130, 140));

        setBounds(0, 0, 1150, 768);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRunMotorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRunMotorActionPerformed

        /*
         String message = "Please enter the node ID of the available nodes to active the pump: \n";

         for (NodeInformation node : masterNode.getNodeList()) {
         message += String.format("Node %d - Address: 0x%x\n", node.getID(), node.getAddress());
         }

         int nodeIDChoosed = Integer.parseInt(JOptionPane.showInputDialog(message));
         message = null;
         if (nodeIDChoosed < 0 || nodeIDChoosed >= masterNode.getNodeList().size()) {
         JOptionPane.showMessageDialog(rootPane, "Please enter only available nodes!");
         return;
         }

         int nodeAddress = masterNode.getNodeList().get(nodeIDChoosed).getAddress();
         int speedMotor = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "Please enter the speed for the pump: "));
         int timeMotor = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "Please enter the running time for the pump (second): "));

         masterNode.activeMotor(nodeAddress, speedMotor, timeMotor);
         */
        frmPumpActivator.setVisible(rootPaneCheckingEnabled);
    }//GEN-LAST:event_btnRunMotorActionPerformed

    private void cbbGraphSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbGraphSelectionActionPerformed

        pnDisplayGraph.removeAll();

        int nodeID = cbbGraphSelection.getSelectedIndex() - 1;

        if (nodeID < 0) {
            JOptionPane.showMessageDialog(rootPane, "Please select a node");
        }
        else {

            DefaultCategoryDataset humidityDataSet = masterNode.getModel().getListDataSetForGraph().get(nodeID).getHumidityDataSet();
            JFreeChart humidityChart = ChartFactory.createLineChart("Humidity", "Time", "%", humidityDataSet);
            ChartPanel humidityPanel = new ChartPanel(humidityChart);
            humidityPanel.setPreferredSize(new Dimension(550, 500));

            DefaultCategoryDataset temperatureDataSet = masterNode.getModel().getListDataSetForGraph().get(nodeID).getTemperatureDataSet();
            JFreeChart temperatureChart = ChartFactory.createLineChart("Temperature", "Time", "Degree", temperatureDataSet);
            ChartPanel temperaturePanel = new ChartPanel(temperatureChart);
            temperaturePanel.setPreferredSize(new Dimension(550, 500));

            pnDisplayGraph.add(humidityPanel);
            pnDisplayGraph.add(temperaturePanel);
            pnDisplayGraph.updateUI(); // Show the components immediately

            /* TEMPORARY DISCARD
             JPanel displayPanel = new JPanel();
             displayPanel.setSize(new Dimension(700, 900));
             displayPanel.add(humidityPanel);
             displayPanel.add(temperaturePanel);

             JFrame displayFrame = new JFrame("Node" + nodeID);
             displayFrame.setContentPane(displayPanel);
             displayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
             displayFrame.pack();
             displayFrame.setVisible(true);
             */
        }
    }//GEN-LAST:event_cbbGraphSelectionActionPerformed

    private void btnZoomInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomInActionPerformed

        int zoomLevel = map.getZoom();
        if (zoomLevel < JMapViewer.MAX_ZOOM) {
            map.zoomIn();
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Cannot zoom in anymore");
        }
    }//GEN-LAST:event_btnZoomInActionPerformed

    private void btnZoomOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnZoomOutActionPerformed

        int zoomLevel = map.getZoom();
        if (zoomLevel > JMapViewer.MIN_ZOOM) {
            map.zoomOut();
        }
        else {
            JOptionPane.showMessageDialog(rootPane, "Cannot zoom out anymore");
        }
    }//GEN-LAST:event_btnZoomOutActionPerformed

    private void btnStartStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartStopActionPerformed
        if (getData == true) {
            stopGettingData();
            txtaDataInfo.setText("Press Start button to\nstart getting data");
        }
        else {
            startGettingData();
            btnRunMotor.setEnabled(true);
            btnZoomIn.setEnabled(true);
            btnZoomOut.setEnabled(true);
        }
    }//GEN-LAST:event_btnStartStopActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void startGettingData() {
        masterNode.sendCommandToNode(0x1001, MasterNode.ACTIVE_NODE_COMMAND);
        masterNode.sendCommandToNode(0x1002, MasterNode.ACTIVE_NODE_COMMAND);
        masterNode.sendCommandToNode(0x1003, MasterNode.ACTIVE_NODE_COMMAND);
        
        btnStartStop.setText("Stop");

//        getDataThread = new RunThread();
        getData = true;
        displayMap = true;

//        getDataThread.start();
        addDataGraph.start();
    }

    private void stopGettingData() {
        masterNode.sendCommandToNode(0x1001, MasterNode.DEACTIVE_NODE_COMMAND);
        masterNode.sendCommandToNode(0x1002, MasterNode.DEACTIVE_NODE_COMMAND);
        masterNode.sendCommandToNode(0x1003, MasterNode.DEACTIVE_NODE_COMMAND);
        
        btnStartStop.setText("Start");

        getData = false;
//        try {
//            getDataThread.join(2000);
//        }
//        catch (InterruptedException e) {
//            
//        }
//        getDataThread = null;

        addDataGraph.stop();
    }

    public class AddDataForGraph implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            for (int i = 0; i < masterNode.getNodeList().size(); i++) {

                String time = timeFormat.format(new Date()); // Get the time when executing this command

                /* Get humidity and display on the graph */
                double humidity = masterNode.getNodeList().get(i).getHumidity();
                DefaultCategoryDataset humidityDataSet = masterNode.getModel().getListDataSetForGraph().get(i).getHumidityDataSet(); // Get the reference of Humidity Data Set
                if (humidityDataSet.getColumnCount() >= 7) {
                    humidityDataSet.clear(); // Clear the data set if there are more than 8 values
                }
                humidityDataSet.addValue(humidity, "Humidity", time);

                /*Get temperature and display on the graph */
                double temperature = masterNode.getNodeList().get(i).getTemperature();
                DefaultCategoryDataset temperatureDataSet = masterNode.getModel().getListDataSetForGraph().get(i).getTemperatureDataSet(); // Get the reference of Temp Data Set
                if (temperatureDataSet.getColumnCount() >= 8) {
                    temperatureDataSet.clear(); // Clear the data set if there are more than 8 values
                }
                temperatureDataSet.addValue(temperature, "Temperature", time);

            }
        }
    }

    /* Getting data by using Thread */
    private class RunThread extends Thread {

        @Override
        public void run() {
            while (true) {
                if (getData == true) {
//                    masterNode.getInformationAllNode();
                    displayTestPacketReceivedSent();
                    if (getData == true && masterNode.getNodeList().size() != 0) {
                        txtaDataInfo.setText(masterNode.toString());
                        System.out.println(masterNode);
                    }

                    if (displayMap == true && masterNode.getNodeList().size() != 0) { // Choose to display map (only 1 time at the beginning of the program)
                        map.setDisplayPosition(masterNode.getModel().getMapMarkerList().get(0).getCoordinate(), ZOOM_SIZE);
                        map.setMapMarkerList(masterNode.getModel().getMapMarkerList());
                        displayMap = false;
                    }

                    //System.out.println(masterNode);
                }
            }
        }
    }
    
    private void displayTestPacketReceivedSent() {
        StringBuilder strBuilder = new StringBuilder();
        for (NodeInformation node: masterNode.getNodeList()) {
            strBuilder.append(String.format("Node 0x%x\nPacket sent: %d\nPacket received: %d\n", node.getAddress(), node.getNumOfPacketsTransmitted(), node.getNumOfPacketsReceived()));
        }
        txtaTestPacket.setText(strBuilder.toString());
    }

    /**
     * Thread used for outputting environment data to excel file for analyzing
     * purpose
     */
    private class OutputExcelThread extends Thread {

        private XSSFWorkbook workBook;
        private XSSFSheet sheet;

        private File file;
        private FileInputStream fileInputStream;
        private FileOutputStream fileOutputStream;

        private int rowID;

        private final String DISK = "C:";
        private final String FOLDER_NAME = "Environment Data";
        private String fileName;

        private final int UPDATE_TIME = 2; // Specify how many seconds the data will be updated to the excel file

        String[] firstRow;
        Object[] cellData;

        public OutputExcelThread() {
            firstRow = new String[]{"Location", "Temperature", "Humidity", "RSSI", "Time"};
            rowID = 0;
        }

        public void run() {
            while (true) {
                if (getData == true) {
                    for (NodeInformation node : masterNode.getNodeList()) {
                        String date = dateFormat.format(new Date());
                        int nodeAddress = node.getAddress();

                        String hexAddress = String.format("0x%x", nodeAddress);
                        NodeLocation location = node.getLocation();
                        double temperature = node.getTemperature();
                        String humidity = String.valueOf(node.getHumidity()) + "%";
                        String RSSI = String.valueOf(node.getReceivedSignalStrength()) + " dBm";
                        String time = timeFormat.format(new Date());

                        fileName = String.format("Node %s, %s.xlsx", hexAddress, date);
                        String path = String.format("%s\\%s\\%s", DISK, FOLDER_NAME, fileName);
                        file = new File(path);

                        cellData = new Object[]{location, temperature, humidity, RSSI, time};

                        if (!file.exists()) { // If the file don't exists, initialize it first
                            workBook = new XSSFWorkbook();
                            sheet = workBook.createSheet("Data"); // Create a sheet called "Data"

                            Row row = sheet.createRow(0);
                            for (int i = 0; i < firstRow.length; i++) {
                                Cell cell = row.createCell(i);
                                cell.setCellValue(firstRow[i]);
                            }

                            writeFile();
                        }
                        else if (file.exists()) { // Update the file if the file already exists
                            workBook = readFile();
                            sheet = workBook.getSheet("Data");

                            rowID = sheet.getLastRowNum(); // get the index of the last row
                            rowID++; // Increase by 1 to create a new row index
                            Row row = sheet.createRow(rowID);
                            for (int i = 0; i < cellData.length; i++) {
                                Cell cell = row.createCell(i);
                                cell.setCellValue(String.valueOf(cellData[i]));
                            }

                            writeFile();
                        }
                    }

                    try {
                        Thread.sleep(UPDATE_TIME * 1000);
                    }
                    catch (InterruptedException exc) {
                        System.out.println(exc.getMessage());
                    }
                }
            }
        }

        /**
         * Writes environment data to the excel file
         */
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

        /**
         * Reads environment data from the existed excel file
         *
         * @return The workbook of the excel file
         */
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
    } // end OutputExcelThread class

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        }
        catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmXuatThongTin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmXuatThongTin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmXuatThongTin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmXuatThongTin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FrmXuatThongTin().setVisible(true);

                }
                catch (XBeeException ex) {
                    Logger.getLogger(FrmXuatThongTin.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnRunMotor;
    private javax.swing.JButton btnStartStop;
    private javax.swing.JButton btnZoomIn;
    private javax.swing.JButton btnZoomOut;
    private javax.swing.JComboBox cbbGraphSelection;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblCommand;
    private javax.swing.JLabel lblDataInformation;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblDisplayDate;
    private javax.swing.JLabel lblLogoBachKhoa;
    private javax.swing.JLabel lblTitle;
    private org.openstreetmap.gui.jmapviewer.JMapViewer map;
    private javax.swing.JPanel pnCommand;
    private javax.swing.JPanel pnDisplayGraph;
    private javax.swing.JPanel pnGraph;
    private javax.swing.JPanel pnMap;
    private javax.swing.JPanel pnTable;
    private javax.swing.JTable tblDatabase;
    private javax.swing.JTextArea txtaDataInfo;
    private javax.swing.JTextArea txtaTestPacket;
    // End of variables declaration//GEN-END:variables
}
