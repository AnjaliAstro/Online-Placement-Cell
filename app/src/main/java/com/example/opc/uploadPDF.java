package com.example.opc;

public class uploadPDF {
    //declaring variables
    public String company,year,url,name;
    //empty constructor
    public uploadPDF(){}
    //constructor with parameters
    public uploadPDF(String company, String year,String url) {
        this.company = company;
        this.year = year;
        this.url = url;
    }

    //method to return comany name of PDFobject of this class
    public String getCompany() {
        return company;
    }

    //method to set comany name of PDFobject of this class
    public void setCompany(String company) {
        this.company = company;
    }

    //method to return year of PDFobject of this class
    public String getYear() {
        return year;
    }

    //method to set year of PDFobject of this class
    public void setYear(String year) {
        this.year = year;
    }

    //method to return url of pdf of PDFobject of this class
    public String getUrl() {
        return url;
    }

    //method to set url of pdf of PDFobject of this class
    public void setUrl(String url) {
        this.url = url;
    }
}
