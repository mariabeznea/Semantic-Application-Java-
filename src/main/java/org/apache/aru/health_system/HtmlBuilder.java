/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.aru.health_system;

import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Maria Catalina
 */
public class HtmlBuilder {
 
    public DefaultListModel<String> createDoctorListItem(ArrayList data) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        
        if (data == null ){
            listModel.addElement("No results found.");
            return listModel; 
        } 
        String[] attributes = (String[]) data.get(0);
         
        if (data.size() <= 1 ) {
            listModel.addElement("No results found.");
            return listModel;
        }
        
        for (int i = 1; i < data.size(); i++) {
            String[] item = (String[]) data.get(i);
            
            // Compiling string
            String info = "<html><style type=\"text/css\">"
                    + ".test { margin-bottom: 3px; background-color: #E6E6FA; width: 370 px; padding: 7px; }"
                    + ".name { font-weight: bold; }"
                    + "</style><div class=\"test\">";
            for (int j = 0; j < attributes.length; j++) {
                
                switch (attributes[j]) {
                    case "Names":
                        info += "<div class=\"name\">" + item[j] + "</div><br>";
                        break;
                    default:
                        info += attributes[j] + ": " + item[j] + "<br>";
                        break;
                }
            }
            info += "</div></html>";
            
            // Adding the item to the listModel
            listModel.addElement(info);
        }
        
        return listModel;
    }
    
    public DefaultListModel<String> createHospitalListItem(ArrayList data) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        String[] attributes = (String[]) data.get(0);
        
        if (data.size() <= 1) {
            listModel.addElement("No results found.");
            return listModel;
        }
        
        for (int i = 1; i < data.size(); i++) {
            String[] item = (String[]) data.get(i);
            
            // Compiling string
            String info = "<html><style type=\"text/css\">"
                    + ".test { margin-bottom: 3px; background-color: #E6E6FA; width: 370 px; padding: 7px; }"
                    + ".name { font-weight: bold; }"
                    + ".text {font-style: italic; }"
                    + "</style><div class=\"test\">";
            for (int j = 0; j < attributes.length; j++) {
                
                switch (attributes[j]) {
                    case "Hospital_name":
                        info += "<div class=\"name\">" + item[j] + "</div><br>";
                        break;
                    case "About":
                        info += "<div class =\"text\">"+ item[j] + "</div><br>";
                        break;
                    default:
                        info += attributes[j] + ": " + item[j] + "<br>";
                        break;
                }
            }
            info += "</div></html>";
            
            // Adding the item to the listModel
            listModel.addElement(info);
        }
        
        return listModel;
    }   
    
     public DefaultListModel<String> createDepartmentListItem(ArrayList data) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        String[] attributes = (String[]) data.get(0);
        
        if (data.size() <= 1) {
            listModel.addElement("No results found.");
            return listModel;
        }
        
        for (int i = 1; i < data.size(); i++) {
            String[] item = (String[]) data.get(i);
            
            // Compiling string
            String info = "<html><style type=\"text/css\">"
                    + ".test { margin-bottom: 3px; background-color: #E6E6FA; width: 370 px; padding: 7px; }"
                    + ".name { font-weight: bold; }"
                    + ".text {font-style: italic; }"
                    + "</style><div class=\"test\">";
            for (int j = 0; j < attributes.length; j++) {
                
                switch (attributes[j]) {
                    case "Department_name":
                        info += "<div class=\"name\">" + item[j] + "</div><br>";
                        break;
                    case "Summary":
                        info += "<div class =\"text\">"+ item[j] + "</div><br>";
                        break;
                    default:
                        info += attributes[j] + ": " + item[j] + "<br>";
                        break;
                }
            }
            info += "</div></html>";
            
            // Adding the item to the listModel
            listModel.addElement(info);
        }
        
        return listModel;
    }   
     
    public DefaultListModel<String> createTreatmentListItem(ArrayList data, boolean showDoctors) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        String[] attributes = (String[]) data.get(0);
      
        
        if (data.size() <= 1) {
            listModel.addElement("No results found.");
            return listModel;
        }

        for (int i = 1; i < data.size(); i++) {
            String[] item = (String[]) data.get(i);
             
            
            // Compiling string
            String info = "<html><style type=\"text/css\">"
                    + ".test { margin-bottom: 3px; background-color: #E6E6FA; width: 370 px; padding: 7px; }"
                    + ".name { font-weight: bold; }"
                    + ".text {font-style: italic; }"
                    + "</style><div class=\"test\">";
            for (int j = 0; j < attributes.length; j++) {
               
                switch (attributes[j]) {
                    case "Title":
                        info += "<div class=\"name\">" + item[j] + "</div><br>";
                        break;
                    case "Name" :
                        break;
                    default:
                        info += attributes[j] + ": " + item[j] + "<br>";
                        break;
                }
            }
            info += "</div></html>";
            
            // Adding the item to the listModel
            listModel.addElement(info);
        }
    
        //Add doctors text in the list 
        if (showDoctors) {
            String[] item = (String[]) data.get(1);
            listModel.addElement("<html><style type=\"text/css\">"
                        + ".name { font-style: italic; text-decoration: underline; }"
                        + "</style><div class=\"test\">"
                        + "<span class=\"name\">Treated by doctors: </span>"
                        + item[2]
                        + "</div></html>");
        }
        
        return listModel;
    }    
    
}
