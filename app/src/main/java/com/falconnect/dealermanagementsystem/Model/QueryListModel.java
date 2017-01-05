package com.falconnect.dealermanagementsystem.Model;



public class QueryListModel {

    private String image1;
    private String owner_name;
    private String car_name;
    private String car_message;
    private String date_time;


    public QueryListModel(String image1, String car_name, String owner_name, String car_message, String date_time) {
        super();

        this.image1 = image1;
        this.owner_name = owner_name;
        this.car_name = car_name;
        this.date_time = date_time;
        this.car_message = car_message;

    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
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
        return car_message;
    }

    public void setCar_details(String car_message) {
        this.car_message = car_message;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}






