package com.falconnect.dealermanagementsystem.Model;



public class QueryListModel {

    private String image1;
    private String image2;
    private String owner_name;
    private String car_name;
    private String car_details;
    private String date_time;


    public QueryListModel(String image1, String image2, String car_name, String owner_name, String car_details, String date_time) {
        super();

        this.image1 = image1;
        this.image2 = image2;
        this.owner_name = owner_name;
        this.car_name = car_name;
        this.date_time = date_time;
        this.car_details = car_details;

    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getCar_details() {
        return car_details;
    }

    public void setCar_details(String car_details) {
        this.car_details = car_details;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}






