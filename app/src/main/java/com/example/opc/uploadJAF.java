package com.example.opc;

public class uploadJAF {
    //variables of this class
    public String name,stipend,profile,link,jaf;
    //empty constructor
    public uploadJAF() { }

    //constructor with parameters
    public uploadJAF(String name, String stipend, String profile, String link, String jaf) {
        this.name = name;
        this.stipend = stipend;
        this.profile = profile;
        this.link = link;
        this.jaf = jaf;
    }

    //method to return  comapnyname of JAFobject of this class
    public String getName() {
        return name;
    }

    //method to set companyname of JAFobject of this class
    public void setName(String name) {
        this.name = name;
    }

    //method to return  comapnstipend of JAFobject of this class
    public String getStipend() {
        return stipend;
    }

    //method to set  comapnstipend of JAFobject of this class
    public void setStipend(String stipend) {
        this.stipend = stipend;
    }

    //method to return  comapnprofile of JAFobject of this class
    public String getProfile() {
        return profile;
    }

    //method to set  comapnprofile of JAFobject of this class
    public void setProfile(String profile) {
        this.profile = profile;
    }

    //method to return jaf link
    public String getLink() {
        return link;
    }

   //method to set jaf link
    public void setLink(String link) {
        this.link = link;
    }

    //method to get jaf file
    public String getJaf() {
        return jaf;
    }

    //method to set jaf file
    public void setJaf(String jaf) {
        this.jaf = jaf;
    }
}
