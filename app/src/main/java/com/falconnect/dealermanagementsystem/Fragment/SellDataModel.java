package com.falconnect.dealermanagementsystem.Fragment;

public class SellDataModel {

    String sellname;
    int sellimage;

    public SellDataModel(String sellname, int sellimage) {
        this.sellname = sellname;
        this.sellimage=sellimage;
    }

    public String getSellname() {
        return sellname;
    }

    public int getSellimage() {
        return sellimage;
    }

    public void setSellimage(int sellimage) {
        this.sellimage = sellimage;
    }

    public void setSellname(String sellname) {
        this.sellname = sellname;
    }
}
