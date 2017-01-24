package com.falconnect.dealermanagementsystem.Model;


public class LoanModel {
    private String custname;
    private String token;
    private String customermobileno;
    private String amount;
    private String email;
    private String status;
    private String date;
    private String bankimage;




    public LoanModel(String custname,String token, String customermobileno, String amount, String email, String status, String date, String bankimage) {
        super();
        this.custname = custname;
        this.token = token;
        this.customermobileno = customermobileno;
        this.amount = amount;
        this.email = email;
        this.status = status;
        this.date = date;
        this.bankimage = bankimage;

    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
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

    public String getCustomermobileno() {
        return customermobileno   ;
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

    public void setCustomermobileno(String customermailid) {
        this.customermobileno = customermobileno;
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

    public String getBankimage() {
        return bankimage;
    }

    public void setBankimage(String bankimage) {
        this.bankimage = bankimage;
    }
}






