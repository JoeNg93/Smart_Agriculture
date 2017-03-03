/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 *
 * This class contains location of the node specify by latitude and longtitude
 */
public class NodeLocation {

    private double latitude;
    private double longtitude;
    private Coordinate coordinate;

    public NodeLocation(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        coordinate = new Coordinate(latitude, longtitude);
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        String output = String.format("%f,%f (lat,lon)", latitude, longtitude);
        return output;
    }
}
