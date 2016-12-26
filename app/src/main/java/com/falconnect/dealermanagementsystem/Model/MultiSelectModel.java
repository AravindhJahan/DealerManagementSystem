package com.falconnect.dealermanagementsystem.Model;

public class MultiSelectModel {

    String city_name;
    String city_id;

    public MultiSelectModel(String id, String name) {
        this.city_name = name;
        this.city_id = id;
    }

    public String getName() {
        return city_name;
    }

    public String  getId() {
        return city_id;
    }


}
