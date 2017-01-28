package com.falconnect.dealermanagementsystem;


public class Constant {

    //Buy Page API
    public static final String LOGIN_API = ConstantIP.IP + "user_login?";
    public static final String FORGOT_PASSWORD_API = ConstantIP.IP + "forgot_password?";
    public static final String DASH_BOARD_SPINNER_API = ConstantIP.IP +"apibuy";
    public static final String DASH_BOARD_SUB_SPINNER_API = ConstantIP.IP + "apibuyid?";
    public static final String CHANGE_PASSWORD_API = ConstantIP.IP + "change_password?";
    public static final String SEARCH_CAR_LISTING_API = ConstantIP.IP + "apisearchcarlisting?";
    public static final String SAVED_CAR_API = ConstantIP.IP + "apiview_savedcars?";
    public static final String SAVE_CAR_API = ConstantIP.IP + "api_save_car?";
    public static final String QUREIS_PAGE_API = ConstantIP.IP + "api_queries_car?";
    public static final String BIDS_POSTED_API = ConstantIP.IP + "api_bidding_list?";
    public static final String BIDS_POSTING_LISTVIEW_API = ConstantIP.IP + "api_bidding_viewmore?";
    public static final String NEW_BID_POSTING_AMOUNT_API = ConstantIP.IP + "api_addbidding_viewmore?";
    public static final String APPLY_FUNDING_APT = ConstantIP.IP + "api_apply_funding?";
    public static final String ADD_APPLY_FUNDING = ConstantIP.IP + "api_add_funding?";
    public static final String REVOKE_FUNDING_BUY = ConstantIP.IP + "api_revoke_funding?";

    public static final String ADD_ALERT_API = ConstantIP.IP + "api_alert_car?";

    //Sell Page API
    public static final String APPLY_LOAN = ConstantIP.IP + "viewapplyloan_list?";
    public static final String MY_POSTING = ConstantIP.IP + "view_mypost_list?";
    public static final String REVOKE_SELL = ConstantIP.IP + "api_revoke_loan?";

    public static final String QUERIES_RECEIVED = ConstantIP.IP + "api_queries_received?";

}

