package fi.jamk.golfcourseapp;

import java.util.ArrayList;
import java.util.List;


public class GolfData{
    public String name;
    public String address;
    public String phonenr;
    public String email;
    public String image;


    public GolfData(String name, String address, String phonenr, String email, String image){
        this.image = image;
        this.name = name;
        this.address = address;
        this.phonenr = phonenr;
        this.email = email;
    }


    private List<GolfData> golfcourses;

    public GolfData(){
        golfcourses = new ArrayList<>();
    }

    public void addGolfplace(GolfData golfdata){
        golfcourses.add(golfdata);
    }

    public List<GolfData> getGolfplaces(){
        return golfcourses;
    }
}