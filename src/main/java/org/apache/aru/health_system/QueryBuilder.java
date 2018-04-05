/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.aru.health_system;

/**
 *
 * @author Maria Catalina
 */
public class QueryBuilder {
    
    private final String prefix = "PREFIX health: <http://www.mcb159.com/ontology#>";
    private String speciality = "";
    private String specialityHospitals = "";
    private String gender = "";
    private String location = "";
    private String language = "";
    private String address = "";
    private String openingHours = "00";
    private String closingHours = "24";
    private String symptoms = "";

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    
    public void setSpecialityHospitals(String specialityHospitals) {
        this.specialityHospitals = specialityHospitals;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public void setClosingHours(String closingHours) {
        this.closingHours = closingHours;
    }
    
    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }
    
    public String getSymptoms() {
        return symptoms;
    }

    
    public String buildDoctorsQuery() {
        if (this.speciality.equals("") && this.location.equals("") && this.language.equals("") && this.gender.equals("")) {
            return prefix + "SELECT DISTINCT (GROUP_CONCAT(DISTINCT ?Name) as ?Names) (GROUP_CONCAT(DISTINCT ?Department_name; SEPARATOR=\", \") as ?Specialties) (GROUP_CONCAT(DISTINCT ?Phone) as ?Telephone) (GROUP_CONCAT(DISTINCT ?Availability) as ?Schedule) (GROUP_CONCAT(DISTINCT ?Location) as ?Primary_Location) \n  (GROUP_CONCAT(DISTINCT ?Language; SEPARATOR=\", \")as ?Languages)" +
            "WHERE { \n" +
            "?doctor a health:Doctors;\n" +
            "health:has_speciality ?Specialities.\n" +
            "?doctor health:Name ?Name.\n" +
            "?doctor health:Phone ?Phone.\n" +  
            "?doctor health:Availability ?Availability.\n" +
            "?doctor health:Location ?Location.\n" +
            "?doctor health:Language ?Language.\n" +
            "?Specialities health:Department_name ?Department_name.\n" +
            "}\n" +
            "GROUP BY ?doctor\n" +
            "ORDER BY ASC(?Name)\n" +
            "LIMIT 25";
        }
        
        // Building Query for search filters
        String queryFirst = this.prefix + "SELECT DISTINCT (GROUP_CONCAT(DISTINCT ?Name) as ?Names) (GROUP_CONCAT(DISTINCT ?other_departments; SEPARATOR=\", \") as ?Specialties) (GROUP_CONCAT(DISTINCT ?Phone) as ?Telephone) (GROUP_CONCAT(DISTINCT ?Availability) as ?Schedule) (GROUP_CONCAT(DISTINCT ?Location) as ?Primary_Location) (GROUP_CONCAT(DISTINCT ?other_languages; SEPARATOR=\", \")as ?Languages) "
                + "WHERE { "
                + "?doctor a health:Doctors; "
                + "health:Name ?Name; "
                + "health:Phone ?Phone; "
                + "health:Language ?Language; "
                + "health:Language ?other_languages; "
                + "health:Gender ?Gender; "
                + "health:Location ?Location; "
                + "health:Availability ?Availability;"
                + "health:has_speciality  ?Specialities."
                + "?Specialities health:Department_name ?other_departments." ;
                
        String queryMiddle = "";
        if (!this.speciality.equals("")) {
            queryMiddle = "?doctor health:has_speciality  ?" + this.speciality + ". "
                    + "?" + this.speciality + " a health:" + this.speciality + "; "
                    + "health:Department_name ?Department_name.";
        }
        
        String queryEnd =(this.gender.equals("") ? "" : "FILTER ((?Gender IN(\"" + this.gender + "\"))) " )
                + (this.language.equals("") ? "" : "FILTER (regex(str(?Language), \"" + this.language + "\")) " )
                + (this.location.equals("") ? "" : "FILTER (regex(str(?Location), \"" + this.location + "\")) " )
                + "} "
                + "GROUP BY ?doctor "
                + "ORDER BY ASC(?Name) "
                + "LIMIT 25";
        
        return queryFirst + queryMiddle + queryEnd;
    }
    
    public String buildHospitalsQuery() {
        if (this.specialityHospitals.equals("") && this.location.equals("") && this.openingHours.equals("00") && this.closingHours.equals("24")) {
            return prefix + "SELECT ?Hospital_name ?About ?Address ?Schedule\n" +
            "WHERE {  \n" +
            "?hospital health:Hospital_name ?Hospital_name.\n" +
            " ?hospital health:About ?About.\n" +
            " ?hospital health:Address ?Address.\n" +
            " ?hospital health:Schedule ?Schedule.\n" +
            " }\n" +
            "ORDER BY ASC(?Hospital_name)\n" +
            "LIMIT 15";
        }
        
        //Building query for search filters
        String queryFirst = this.prefix + "SELECT DISTINCT ?Hospital_name ?About ?Address ?Schedule \n" +
        "WHERE {  \n" +
        "  ?hospital  health:Hospital_name ?Hospital_name.\n" +
        "  ?hospital health:About ?About.\n" +
        "  ?hospital health:Schedule ?Schedule.\n" +
        "  ?hospital health:Closing_time ?Closing_time.\n" +
        "  ?hospital health:Closing_time ?Opening_time.\n" +
        "  ?hospital health:Address ?Address.\n" ;
   
        String queryMiddle = "";
        if (!this.specialityHospitals.equals("")) {
            queryMiddle = "?hospital health:has_department  ?" + this.specialityHospitals + ". "
                    + "?" + this.specialityHospitals + " a health:" + this.specialityHospitals + "; "
                    + "health:Department_name ?Department_name.";
        }
        
        String queryEnd = (this.address.equals("") ? "" : "FILTER (regex(str(?Address), \"" + this.address + "\")) " )
                + "  FILTER(?Opening_time >= \"" + this.openingHours + ":00:00\" )."
                + "  FILTER(?Closing_time <= \"" + this.closingHours + ":00:00\" )."
                + "} "
                + "ORDER BY ASC(?Hospital_name) "
                + "LIMIT 25";
        
        return queryFirst + queryMiddle + queryEnd;
    }
    
    
    public String buildDepartmentsQuery() {
       String prefix_rdfs = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";
            
            return prefix_rdfs + prefix + "SELECT ?Department_name ?Summary " +
            "WHERE {  \n" +
            "?subclass rdfs:subClassOf health:Specialities." +
            "?subclass health:Department_name ?Department_name." +
            "?subclass health:Summary ?Summary." +
            " }" +
            "ORDER BY ASC(?Department_name)" +
            "LIMIT 12";
       
    }
    
    public String buildTreatmentsQuery() {
        if (this.symptoms.equals("")) {
            return prefix + "SELECT DISTINCT (GROUP_CONCAT(DISTINCT ?Titles) as ?Title) (GROUP_CONCAT(DISTINCT ?Quantities) as ?Quantity) (GROUP_CONCAT(DISTINCT ?Names; SEPARATOR=\", \")as ?Name)" +
            "WHERE {  \n" +
            "?Medicines a health:Medicines.\n" +
            "?Medicines health:Title ?Titles.\n" +
            "?Medicines health:Quantity ?Quantities.\n" +
            "?symptoms health:checked_by_doctor ?Doctors." +
            "?Doctors health:Name ?Names." +
            "}\n" +
            "GROUP BY ?Medicines "+       
            "ORDER BY ASC(?Titles)\n" +
            "LIMIT 15";
        }
        
        //Building query for search filters
        String query = this.prefix + "SELECT  DISTINCT (GROUP_CONCAT(DISTINCT ?Titles) as ?Title) (GROUP_CONCAT(DISTINCT ?Quantities) as ?Quantity) (GROUP_CONCAT(DISTINCT ?Names; SEPARATOR=\", \")as ?Name)" +
               "WHERE {" +
               "?symptoms a health:Symptoms;" +
               "health:Label ?Label;" +
               "health:treated_with ?Medicines." +
               "?Medicines a health:Medicines." +
               "?Medicines health:Title ?Titles." +
               "?Medicines health:Quantity ?Quantities." +
               "?symptoms health:checked_by_doctor ?Doctors." +
               "?Doctors health:Name ?Names." +
               (this.symptoms.equals("") ? "" : "FILTER (regex(str(?Label), \"" + this.symptoms + "\")) " ) +
               "}" +
               "GROUP BY ?Medicines " +
               "ORDER BY ASC(?Title)" +
               "LIMIT 15";
        
     
        return query;
    }
}
