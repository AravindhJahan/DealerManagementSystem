package com.falconnect.dealermanagementsystem.Model;


public class ApplyFundingListModel {

    private String token;
    private String contact;
    private String amount;
    private String email;
    private String status;
    private String date;



    public ApplyFundingListModel(String token, String contact, String amount, String email, String status, String date) {
        super();

        this.token = token;
        this.contact = contact;
        this.amount = amount;
        this.email = email;
        this.status = status;
        this.date = date;

    }

    public String getToken() {
        return token;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}






