/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jfree.data.category.DefaultCategoryDataset;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;

/**
 *
 * @author DungNguyenz
 */
public class DisplayModel {

    private ArrayList<MapMarker> mapMarkerList; //List contain marker of each individual node

    private DefaultTableModel tblModel; // Model for the database table

    private DefaultComboBoxModel cbbModel; // Model for the combo box which is used for displaying selected graph

    private ArrayList<GraphDataSet> listDataSetForGraph; // A list that contains all dataset of all node's graph   

    /**
     * Represents a dataset for one node's graph
     */
    public class GraphDataSet {

        private int nodeGraphID;
        private DefaultCategoryDataset temperatureDataSet;
        private DefaultCategoryDataset humidityDataSet;

        public GraphDataSet(int nodeGraphID) {
            this.nodeGraphID = nodeGraphID;
            temperatureDataSet = new DefaultCategoryDataset();
            humidityDataSet = new DefaultCategoryDataset();
        }

        public int getID() {
            return nodeGraphID;
        }

        public DefaultCategoryDataset getTemperatureDataSet() {
            return temperatureDataSet;
        }

        public DefaultCategoryDataset getHumidityDataSet() {
            return humidityDataSet;
        }
    }

    public DisplayModel() {
        mapMarkerList = new ArrayList<>(); //List<> is interface, ArrayList<> is class that implements List<>

        tblModel = new DefaultTableModel();
        initModel();

        cbbModel = new DefaultComboBoxModel();
        cbbModel.addElement("--Chọn Node--");

        listDataSetForGraph = new ArrayList<>();

    }
    
     /**
     * Initialize a table with 6 columns
     */
    public void initModel() {
        String[] columnNames = new String[]{
            "Node ID", "Address", "Location", "Temperature", "Humidity", "RSSI", "Timer"
        };
        for (String name : columnNames) {
            tblModel.addColumn(name);
        }
    }
    
    /**
     * Return a list containing all the node's marker
     */
    public ArrayList<MapMarker> getMapMarkerList() {
        return mapMarkerList;
    }
    
     /**
     * Get the tblModel of a table for displaying database
     */
    public DefaultTableModel getTableModel() {
        return tblModel;
    }

    /**
     * Get the cbbModel of a combo box
     */
    public DefaultComboBoxModel getComboBoxModel() {
        return cbbModel;
    }
    
    /**
     * Get the list dataset for graph
     */
    public ArrayList<GraphDataSet> getListDataSetForGraph() {
        return listDataSetForGraph;
    }
    
}
