/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

/* Libraries for XBee */
import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.RxResponse16;
import com.rapplogic.xbee.api.wpan.TxRequest16;
import com.rapplogic.xbee.api.wpan.TxStatusResponse;

import java.awt.Color;

import java.util.ArrayList;

import javax.swing.Timer;

import java.awt.event.*;

/**
 * This class belongs to the master node The master node will in charge of
 collecting all the information of other nodes in the network Each node in the
 network will command the information to the master node Master node has an array
 that contains the information of all nodes in the network
 */
public class MasterNode {

    private XBee xbee; // Use for sending and receiving data, via packets
    private XBeeAddress16 destination; //contains the address of the node we want to command to
    private TxRequest16 tx;

    private ArrayList<NodeInformation> node; //Contains info of all nodes in the network

    private int nodeID;

    private final int XBEE_BAUDRATE = 9600;

    private DisplayModel model;

    private final String COM_PORT = "COM4";
    
    private int totalPacketsTransmitted;
    
    /* Two commands for sending to end devices */
    public static final byte ACTIVE_MOTOR_COMMAND = 0;
    public static final byte ACTIVE_NODE_COMMAND = 1;
    public static final byte DEACTIVE_NODE_COMMAND = 2;

    public MasterNode() {

        model = new DisplayModel();

        node = new ArrayList<NodeInformation>();

        nodeID = 1;
        xbee = new XBee();
        try {
            xbee.open(COM_PORT, XBEE_BAUDRATE); //Try to connect to COM Port, Baud Rate = 9600
        }
        catch (XBeeException ex) {
            System.out.println("Can't open COM PORT");
        }
        xbee.addPacketListener(new PacketListener() {

            @Override
            public void processResponse(XBeeResponse response) {
                getInformationAllNode(response);
            }

        });

        /* Increase the Inactivity Timer of every nodes every 2 second */
        Timer timer = new Timer(2000, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                triggerTheInactivityTimer();
            }
        });
        timer.start();
        //destination = new XBeeAddress16(0x56, 0x78);
    }

    /**
     * Stop the node from collecting environmental data at specific address
     *
     * @param highAddr 8 high bytes of the node's address
     * @param lowAddr 8 low bytes of the node's address
     */
    public void stopNodeAtAddress(int highAddr, int lowAddr) {
        destination = new XBeeAddress16(highAddr, lowAddr);
        tx = new TxRequest16(destination, new int[]{0}); //Prepare the tx packet with destination and value (0 in this case)
        TxStatusResponse status = new TxStatusResponse();
        try {
            status = (TxStatusResponse) xbee.sendSynchronous(tx); //Send the command to the node specify by its address
        }
        catch (XBeeException ex) {
            System.out.println("Time out, cant send the packet");
        }
        if (status.isSuccess()) { //Send command successfully
            String out = String.format(("Command sent. Stop the Node at address (%x,%x)"), lowAddr, highAddr);
            System.out.println(out);
        }
    }

    /**
     * Stop all the node from collecting environmental data
     */
    public void stopAllNode() {
        for (int i = 0; i < node.size(); i++) {
            int lowAddr = node.get(i).getAddress() & 0xff;
            int highAddr = (node.get(i).getAddress() >> 8) & 0xff;
            destination = new XBeeAddress16(highAddr, lowAddr); //Set the address of each node
            tx = new TxRequest16(destination, new int[]{0}); //Prepare the command to command ( 0 in this case )
            try {
                xbee.sendSynchronous(tx); //Send the command 0 to each node
            }
            catch (XBeeException exc) {
                System.out.println("Time out. Can't stop node " + i);
            }
        }
    }

    /**
     * Get the information of all nodes
     */
    public void getInformationAllNode(XBeeResponse response) {
//        XBeeResponse response = null;

        boolean isNodeInNetwork = false;
//        try {
//            response = xbee.getResponse(); //Get the response of the node
//        }
//        catch (XBeeException exc) {
//            System.out.println("Timeout. Can't get response");
//        }

        if (response.getApiId() == ApiId.RX_16_RESPONSE) {
//            System.out.println("Packet received: ");
//            System.out.println(response);

            RxResponse16 response16 = (RxResponse16) response;
            /* Each element of RX packet is 8 bit */
            int highAddr = response.getProcessedPacketBytes()[3]; //Get 8 high bits of address in the packet
            int lowAddr = response.getProcessedPacketBytes()[4]; //Get 8 low bits of address in the packet

            int[] addr = response16.getSourceAddress().getAddress();
//            int highAddr = addr[0];
//            int lowAddr = addr[1];
            /* Address contains 32 bits */
            int address = (highAddr << 8) + lowAddr;

            int[] data = response16.getData();
            /* Get the humidity of the node */
            int humidity = response.getProcessedPacketBytes()[7]; //Get humidity
//            int humidity = data[0];

            /* Get the temperature of the node */
            int lowTemp = response.getProcessedPacketBytes()[8];
            int highTemp = response.getProcessedPacketBytes()[9];

//            int lowTemp = data[1];
//            int highTemp = data[2];
            int temperature = (highTemp << 8) + lowTemp;
            double realTemp = (double) temperature / 100;

            /* Get the location of the node */
            int lowLowLat = response.getProcessedPacketBytes()[10];
            int highLowLat = response.getProcessedPacketBytes()[11];
            int lowHighLat = response.getProcessedPacketBytes()[12];
            int highHighLat = response.getProcessedPacketBytes()[13];
            int lowLowLong = response.getProcessedPacketBytes()[14];
            int highLowLong = response.getProcessedPacketBytes()[15];
            int lowHighLong = response.getProcessedPacketBytes()[16];
            int highHighLong = response.getProcessedPacketBytes()[17];

//            int lowLowLat = data[3];
//            int highLowLat = data[4];
//            int lowHighLat = data[5];
//            int highHighLat = data[6];
//            int lowLowLong = data[7];
//            int highLowLong = data[8];
//            int lowHighLong = data[9];
//            int highHighLong = data[10];
            long lat = (highHighLat << 24) + (lowHighLat << 16) + (highLowLat << 8) + lowLowLat;
            long lon = (highHighLong << 24) + (lowHighLong << 16) + (highLowLong << 8) + lowLowLong;
            double realLat = (double) lat / 1000000;
            double realLong = (double) lon / 1000000;
            NodeLocation location = new NodeLocation(realLat, realLong);

            /* Get received signal strength of the node */
            int RSSI = response16.getRssi();
            
            int packetsTransmitted = response.getProcessedPacketBytes()[18];
            int times = response.getProcessedPacketBytes()[19];
            int totalPacketsTransmitted = packetsTransmitted + 255 * times;

            if (node.isEmpty()) { //There's no node in the network
                node.add(new NodeInformation(0, address, location, humidity, realTemp, RSSI));
                node.get(0).increaseNumOfPacketsReceived();
                node.get(0).setNumOfPacketsTransmitted(totalPacketsTransmitted);
                
                model.getMapMarkerList().add(node.get(0).getMapMarker()); //Add node marker to display on map

                String hexAddress = String.format("0x%x", address);

                model.getTableModel().addRow(new Object[]{ // Add a new row to the table
                    0, hexAddress, location, realTemp + " degree", humidity + "%", RSSI + " dbM", node.get(0).getInactivityTimer()
                });

                model.getComboBoxModel().addElement("Node 0"); // Add the node's label to the combo box

                model.getListDataSetForGraph().add(model.new GraphDataSet(0)); // Create a dataset for displaying node info on a graph

            }
            else {
                for (int i = 0; i < node.size(); i++) {
                    if (address == node.get(i).getAddress()) { //If node was already in the network
                        node.get(i).setInactivyTimer(0); // Reset the Timer
                        node.get(i).updateNodeInformation(location, humidity, realTemp, RSSI); //Update the information of node
                        
                        node.get(i).increaseNumOfPacketsReceived();
                        node.get(i).setNumOfPacketsTransmitted(totalPacketsTransmitted);

                        model.getMapMarkerList().add(i, node.get(i).getMarker()); //Update the node marker                    

                        /* Update the tblModel */
                        model.getTableModel().setValueAt(location, i, 2);
                        model.getTableModel().setValueAt(realTemp + " degree", i, 3);
                        model.getTableModel().setValueAt(humidity + "%", i, 4);
                        model.getTableModel().setValueAt(RSSI + " dbM", i, 5);

                        isNodeInNetwork = true;
                    }
                }
                if (isNodeInNetwork == false) //If node was not in the network yet
                {
                    node.add(new NodeInformation(nodeID, address, location, humidity, realTemp, RSSI)); //Create new node
                    
                    node.get(nodeID).increaseNumOfPacketsReceived();
                    node.get(nodeID).setNumOfPacketsTransmitted(totalPacketsTransmitted);

                    model.getMapMarkerList().add(node.get(nodeID).getMarker()); //Create a new node marker

                    String hexAddress = String.format("0x%x", address);

                    model.getTableModel().addRow(new Object[]{ // Create a new row in the table
                        nodeID, hexAddress, location, realTemp + " degree", humidity + "%", RSSI + " dbM", node.get(nodeID).getInactivityTimer()
                    });

                    model.getComboBoxModel().addElement("Node " + nodeID);

                    model.getListDataSetForGraph().add(model.new GraphDataSet(nodeID));

                    nodeID++;

                } // end if (isNodeInNetwork == false)

            } // end if (node.isEmpty())

        } // end if (response.getApiId() == ApiId.RX_16_RESPONSE)
        
        checkInactivityNode();
        checkEnvironment();
        
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < node.size(); i++) {
            out += "Node " + i + ": \n";
            out += node.get(i).toString();
        }
        return out;
    }

    /**
     * Return the array that contains all node in the network
     */
    public ArrayList<NodeInformation> getNodeList() {
        return node;
    }

    /**
     * Return the instance of class DisplayModel that contains all the model
     */
    public DisplayModel getModel() {
        return model;
    }

    /**
     * Increase the timer of all nodes by 1 and update the table
     */
    private void triggerTheInactivityTimer() {
        for (int i = 0; i < node.size(); i++) {
            node.get(i).increaseInactivityTimer();
            model.getTableModel().setValueAt(node.get(i).getInactivityTimer(), i, 6);
        }
    }

    /**
     * Remove some inactivity nodes out of the database if the master node no
     * longer receive data from these nodes
     */
    private void checkInactivityNode() {
        for (int i = 0; i < node.size(); i++) {
            int nodeTimer = node.get(i).getInactivityTimer();
            if (nodeTimer >= 150) {
                node.remove(i);
                model.getTableModel().removeRow(i);
            }
        }
    }

    /**
     * Check the environment Temp > 40 => Change node's marker to red and active
     * the pump
     */
    private void checkEnvironment() {
        for (NodeInformation nodeInNetwork : node) {
            if (nodeInNetwork.getTemperature() >= 37.0) {

                nodeInNetwork.getMapMarker().setBackColor(Color.RED);

                int speedPump;
                int timePump;
                if (nodeInNetwork.getTemperature() >= 41.0) {
                    speedPump = 200;
                    timePump = 5;
                }
                else {
                    speedPump = 180;
                    timePump = 3;
                }
                activeMotor(nodeInNetwork.getAddress(), speedPump, timePump);
            }
            else {
                nodeInNetwork.getMapMarker().setBackColor(Color.YELLOW);
            }
        }
    }

    /**
     * Send the signal (number 0 in this case) to active the motor with the
     * given time
     *
     * @param address The address of the node which need to be pumped
     * @param speed Speed of pump
     * @param time Activation time for the pump, measured in second
     */
    public void activeMotor(int address, int speed, int time) {
        int highAddr = (address >> 8) & 0xff;
        int lowAddr = address & 0xff;
        destination = new XBeeAddress16(highAddr, lowAddr);

        int[] command = new int[3];
        command[0] = ACTIVE_MOTOR_COMMAND;
        command[1] = speed;
        command[2] = time;

        tx = new TxRequest16(destination, command);

        try {
            xbee.sendSynchronous(tx); //Send the command 0 to each node
            System.out.println("Activation motor command sent.");
        }
        catch (XBeeException exc) {
            System.out.println("Time out. Can't active the pump.");
        }

    }
    
    public void sendCommandToNode(int address, int typeOfCommand) {
        int highAddr = (address >> 8) & 0xff;
        int lowAddr = address & 0xff;
        destination = new XBeeAddress16(highAddr,  lowAddr);
        
        int[] command = new int[] {typeOfCommand};       
               
        tx = new TxRequest16(destination, command);
        try {
            xbee.sendSynchronous(tx);
            System.out.println(String.format("Active node 0x%x command sent.", address));
        }
        catch (XBeeException exc) {
            System.out.println(String.format("Time out. Can't active node 0x%x.", address));
        }      
    }
}
