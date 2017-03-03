/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.awt.Color;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

/**
 * This class contains information of each node in the network Each node has its
 * address, location (latitude,longtitude), humidity, temperature
 */
public class NodeInformation {

    private int ID;
    private double temperature; //Temperature at node
    private int humidity; //Humidity at node
    private int address; //address of the node
    private int inactivityTimer; //Timer of each node for inactivity checking
    private int receivedSignalStrength; // Signal Strength
    private NodeLocation location; //Location of node
    private MapMarkerDot marker; //Dot marker that will be displayed on the map

    private int numOfPacketsReceived;
    private int numOfPacketsTransmitted;

    public NodeInformation(int ID, int address, NodeLocation location, int humidity, double temperature, int receivedSignalStrength) {
        this.ID = ID;
        this.address = address;
        this.location = location;
        this.humidity = humidity;
        this.temperature = temperature;
        this.receivedSignalStrength = receivedSignalStrength;
        inactivityTimer = 0;
        marker = new MapMarkerDot("Node " + ID, location.getCoordinate());
        marker.setColor(Color.YELLOW);

        numOfPacketsReceived = 0;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public NodeLocation getLocation() {
        return location;
    }

    public void setLocation(NodeLocation location) {
        this.location = location;
    }

    public int getID() {
        return ID;
    }

    public int getInactivityTimer() {
        return inactivityTimer;
    }

    public void setInactivyTimer(int value) {
        inactivityTimer = value;
    }

    public void increaseInactivityTimer() {
        inactivityTimer++;
    }

    public int getReceivedSignalStrength() {
        return receivedSignalStrength;
    }

    public void setReceivedSignalStrength(int receivedSignalStrength) {
        this.receivedSignalStrength = receivedSignalStrength;
    }

    public MapMarkerDot getMapMarker() {
        return marker;
    }

    @Override
    public String toString() {
        String output = String.format((""
                + "Address: 0x%x\n"
                + "Location: %s\n"
                + "Humidity: %d%%\n"
                + "Temperature: %.2f degree\n"),
                                      address, location.toString(), humidity, temperature);
        return output;
    }

    public void updateNodeInformation(NodeLocation location, int humidity, double temperature, int receivedSignalStrength) {
        this.location = location;
        this.humidity = humidity;
        this.temperature = temperature;
        this.receivedSignalStrength = receivedSignalStrength;
        marker.setLat(location.getLatitude());
        marker.setLon(location.getLongtitude());
    }

    public MapMarkerDot getMarker() {
        return marker;
    }

    public void increaseNumOfPacketsReceived() {
        numOfPacketsReceived++;
    }

    public int getNumOfPacketsReceived() {
        return numOfPacketsReceived;
    }

    public void setNumOfPacketsTransmitted(int numOfPacketsTransmitted) {
        this.numOfPacketsTransmitted = numOfPacketsTransmitted;
    }

    public int getNumOfPacketsTransmitted() {
        return numOfPacketsTransmitted;
    }

}
