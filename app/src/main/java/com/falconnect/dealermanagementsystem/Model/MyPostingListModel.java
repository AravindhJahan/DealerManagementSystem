package com.falconnect.dealermanagementsystem.Model;


public class MyPostingListModel {

    private String imageurl;
    private String year;
    private String price;
    private String kms;
    private String owner;
    private String fuel_type;
    private String make;
    private String model;
    private String variant;
    private String imagecount;
    private String videoscount;
    private String documentcount;
    private String viewscount;
    private String mongopushdate;

    public MyPostingListModel(String imageurl, String year,
                              String price, String kms,
                              String owner, String fuel_type,
                              String make, String model,
                              String variant, String imagecount,
                              String videoscount, String documentcount,
                              String viewscount, String mongopushdate) {
        super();

        this.imageurl = imageurl;
        this.year = year;
        this.price = price;
        this.kms = kms;
        this.owner = owner;
        this.fuel_type = fuel_type;
        this.make = make;
        this.model = model;
        this.variant = variant;
        this.imagecount = imagecount;
        this.videoscount = videoscount;
        this.documentcount = documentcount;
        this.viewscount = viewscount;
        this.mongopushdate = mongopushdate;

    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKms() {
        return kms;
    }

    public void setKms(String kms) {
        this.kms = kms;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getImagecount() {
        return imagecount;
    }

    public void setImagecount(String imagecount) {
        this.imagecount = imagecount;
    }

    public String getVideoscount() {
        return videoscount;
    }

    public void setVideoscount(String videoscount) {
        this.videoscount = videoscount;
    }

    public String getViewscount() {
        return viewscount;
    }

    public void setViewscount(String viewscount) {
        this.viewscount = viewscount;
    }

    public String getMongopushdate() {
        return mongopushdate;
    }

    public void setMongopushdate(String mongopushdate) {
        this.mongopushdate = mongopushdate;
    }

}






