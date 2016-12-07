package com.falconnect.dealermanagementsystem.Model;



public class SingleProductModel {

    private String image;
    private String name;
    private String rate;
    private String kms;
    private String fuel;
    private String year;
    private String owner;
    private String address;
    private String posted_date;
    private String num_of_image;
    private String site_image;


    public SingleProductModel(String image, String name, String rate, String kms, String fuel, String year, String owner, String address, String posted_date, String num_of_image, String site_image)
    {
        super();

        this.image = image;
        this.name = name;
        this.rate = rate;
        this.kms = kms;
        this.fuel = fuel;
        this.year = year;
        this.owner = owner;
        this.address = address;
        this.posted_date = posted_date;
        this.num_of_image = num_of_image;
        this.site_image = site_image;

    }
    public String getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public String getRate() {
        return rate;
    }
    public String getKms() {
        return kms;
    }
    public String getFuel() {
        return fuel;
    }
    public String getYear() {
        return year;
    }
    public String getOwner() {
        return owner;
    }
    public String getAddress() {
        return address;
    }
    public String getPosted_date() {
        return posted_date;
    }

    public String getNum_of_image() {
        return num_of_image;
    }

    public String getSite_image() {
        return site_image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setKms(String kms) {
        this.kms = kms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum_of_image(String num_of_image) {
        this.num_of_image = num_of_image;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSite_image(String site_image) {
        this.site_image = site_image;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }

}



