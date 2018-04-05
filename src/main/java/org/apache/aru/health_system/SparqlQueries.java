/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.aru.health_system;

import java.awt.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

/**
 *
 * @author Maria Catalina
 */
public class SparqlQueries {

    private ArrayList createData(ResultSet rs, String[] properties) {
        ArrayList data = new ArrayList();
      
        // Adding array of attribute names for interpretation
        data.add(properties);
        
        // Adding items to the ArrayList
        while (rs.hasNext()) {
            String[] item = new String[properties.length];
            QuerySolution sol = rs.nextSolution();
            
            // Adding properties to item array
            for (int i = 0; i < properties.length; i++) {
                try{
                    item[i] = sol.get(properties[i]).toString();
                }
                catch(java.lang.NullPointerException e){
                    return null;
                }
            }
            // Adding to data ArrayList
            data.add(item);
        }

        return data;
    }
    
    private ResultSet getSparqlQuery(String queryStr) {
        // Create Query
        Query query = QueryFactory.create(queryStr);

        // Remote execution.
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://localhost:8171/health/query", query);
        // Set the DBpedia specific timeout.
        ((QueryEngineHTTP) qexec).addParam("timeout", "10000");

        // Execute. 
        return qexec.execSelect();
    }
    
    public ArrayList listDoctors(String queryStr) {
     
        // Execute. 
        ResultSet rs = getSparqlQuery(queryStr);
        
        //Create array of attribute names
        String[] properties = {"Names", "Specialties", "Telephone", "Schedule", "Primary_Location", "Languages"};
        return createData(rs, properties);
    }

    public ArrayList listHospitals(String queryStr) {
        
        // Execute. 
        ResultSet rs = getSparqlQuery(queryStr);
  
        //Create array of attribute names
        String[] properties = {"Hospital_name", "About", "Address", "Schedule"};
        return createData(rs, properties);
    }
  
    public ArrayList listDepartments(String queryStr) {
    
        // Execute. 
        ResultSet rs = getSparqlQuery(queryStr);
        
        //Create array of attribute names
        String[] properties = {"Department_name", "Summary"};
        return createData(rs, properties);
    }
    
    public ArrayList listTreatments(String queryStr) {
   
        // Execute. 
        ResultSet rs = getSparqlQuery(queryStr);
  
        //Create array of attribute names
        String[] properties = {"Title", "Quantity", "Name"};
        return createData(rs, properties);
    }
}
