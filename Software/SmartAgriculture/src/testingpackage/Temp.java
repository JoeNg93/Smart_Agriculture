///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package testingpackage;
//
//import com.rapplogic.xbee.api.XBeeException;
//import java.awt.event.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.Timer;
//import javax.swing.table.DefaultTableModel;
//import mainpackage.FrmXuatThongTin;
//import mainpackage.MasterNode;
//import mainpackage.NodeInformation;
//
///**
// *
// * @author DungNguyenz
// */
//public class Temp extends javax.swing.JFrame {
//
//    /**
//     * Creates new form FrmXuatThongTin
//     */
//    private MasterNode masterNode;
//    private DefaultTableModel model;
//
//    public Temp() throws XBeeException {
//        masterNode = new MasterNode();
//        model = new DefaultTableModel();
//        initTable();
//        initComponents();
//    }
//    
//    /* Initialize the table, create columns */
//    public void initTable() {
//        String[] columnNames = new String[]{
//            "Node ID","Address","Location","Temperature","Humidity","Timer"
//        };
//        for (String name : columnNames) {
//            model.addColumn(name);
//        }
//    }
//    
//    /* Update the table, create rows depend on database */
//    public void updateTable() {
//        clearModel();
//        for (NodeInformation node : masterNode.getNodeList()) {
//            String address = String.format("0x%x", node.getAddress());
//            Object[] nodeData = new Object[] {
//                node.getID(), address, node.getLocation().toString(), node.getTemperature(),
//                node.getHumidity(), node.getInactivityTimer()
//            };
//            model.addRow(nodeData);           
//        }
//    }
//    
//    public void clearModel() {
//        for (int i = 0; i < model.getRowCount(); i++) {
//            model.removeRow(i);
//        }
//    }
//
//    /**
//     * This method is called from within the constructor to initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is always
//     * regenerated by the Form Editor.
//     */
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
//    private void initComponents() {
//
//        jTabbedPane1 = new javax.swing.JTabbedPane();
//        jPanel1 = new javax.swing.JPanel();
//        btnStop = new javax.swing.JButton();
//        jScrollPane1 = new javax.swing.JScrollPane();
//        txtaOutput = new javax.swing.JTextArea();
//        btnStart = new javax.swing.JButton();
//        map = new org.openstreetmap.gui.jmapviewer.JMapViewer();
//        jLabel1 = new javax.swing.JLabel();
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//
//        btnStop.setText("Stop");
//        btnStop.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnStopActionPerformed(evt);
//            }
//        });
//
//        txtaOutput.setColumns(20);
//        txtaOutput.setRows(5);
//        jScrollPane1.setViewportView(txtaOutput);
//
//        btnStart.setText("Start");
//        btnStart.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                btnStartActionPerformed(evt);
//            }
//        });
//
//        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
//        jPanel1.setLayout(jPanel1Layout);
//        jPanel1Layout.setHorizontalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel1Layout.createSequentialGroup()
//                .addGap(36, 36, 36)
//                .addComponent(map, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(167, 167, 167))
//                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(59, 59, 59)
//                        .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
//                        .addGap(64, 64, 64))))
//        );
//        jPanel1Layout.setVerticalGroup(
//            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
//                .addGap(96, 96, 96)
//                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
//                    .addComponent(btnStop, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
//                    .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addGap(87, 87, 87))
//            .addGroup(jPanel1Layout.createSequentialGroup()
//                .addGap(60, 60, 60)
//                .addComponent(map, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//
//        jTabbedPane1.addTab("tab1", jPanel1);
//
//        jLabel1.setText("Smart Agriculture");
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
//            .addGroup(layout.createSequentialGroup()
//                .addGap(339, 339, 339)
//                .addComponent(jLabel1)
//                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
//                .addGap(33, 33, 33)
//                .addComponent(jLabel1)
//                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
//                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE))
//        );
//
//        pack();
//    }// </editor-fold>                        
//
//    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {                                         
//        // TODO add your handling code here:
//        RunMethod listener = new RunMethod();
//        Timer runn = new Timer(4000, listener);
//        runn.start();
//
//
//    }                                        
//
//    private void btnStopActionPerformed(java.awt.event.ActionEvent evt) {                                        
//        // TODO add your handling code here:
//        System.exit(0);
//    }                                       
//    public class RunMethod implements ActionListener {
//
//        public void actionPerformed(ActionEvent e) {
//            masterNode.getInformationAllNode();
//            map.setDisplayPosition(masterNode.getMapMarkerList().get(0).getCoordinate(), 15);
//            map.setMapMarkerList(masterNode.getMapMarkerList());
//            //map.setMapMarkerVisible(true);
//            System.out.println(masterNode);
//
//        }
//
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FrmXuatThongTin.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrmXuatThongTin.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrmXuatThongTin.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrmXuatThongTin.class
//                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    new Temp().setVisible(true);
//
//                } catch (XBeeException ex) {
//                    Logger.getLogger(FrmXuatThongTin.class
//                            .getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });
//    }
//
//    // Variables declaration - do not modify                     
//    private javax.swing.JButton btnStart;
//    private javax.swing.JButton btnStop;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JPanel jPanel1;
//    private javax.swing.JScrollPane jScrollPane1;
//    private javax.swing.JTabbedPane jTabbedPane1;
//    private org.openstreetmap.gui.jmapviewer.JMapViewer map;
//    private javax.swing.JTextArea txtaOutput;
//    // End of variables declaration                   
//}
