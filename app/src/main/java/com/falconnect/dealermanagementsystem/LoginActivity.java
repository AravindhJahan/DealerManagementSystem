package com.falconnect.dealermanagementsystem;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private boolean mVisible;
    TextView signup, forgot_password;
    TextView donthaveaccount;
    Button submit;
    EditText username, pass_word;
    //JSON DATAS
    String user, pass;

    public ArrayList<HashMap<String, String>> LoginList;
    HashMap<String, String> loginlistmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;

        if (mVisible) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            mVisible = false;

        } else {
        }

        intialize();

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = pass_word.getText().toString();

                if (user.equals("") && pass.equals("")) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Invalid User");
                    builder.setMessage("Enter the valid username and password")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                    builder.show();
                } else {
                    new GetLoginData().execute();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(j);

                LoginActivity.this.finish();
            }
        });

    }

    public void intialize() {
        signup = (TextView) findViewById(R.id.sigin_up);
        donthaveaccount = (TextView) findViewById(R.id.dont_have_account);
        forgot_password = (TextView) findViewById(R.id.forgot_password);
        submit = (Button) findViewById(R.id.submit_btn);

        //Edittext
        username = (EditText) findViewById(R.id.username);
        pass_word = (EditText) findViewById(R.id.password);

        username.setTypeface(Typeface.SANS_SERIF);
        pass_word.setTypeface(Typeface.SANS_SERIF);


    }


    /////ONBackPressed Button Dialog Box
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    class GetLoginData extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {

            ServiceHandler sh = new ServiceHandler();

            String main_url = Constant.LOGIN_API + "email=" + user.toString() + "&password=" + pass.toString();

            String json = sh.makeServiceCall(main_url, ServiceHandler.POST);


            if (json != null) {
                LoginList = new ArrayList<>();
                loginlistmap = new HashMap<String, String>();

                try {
                    JSONObject obj = new JSONObject(json);

                    for (int i = 0; i <= obj.length(); i++) {

                        String result = obj.getString("Result");

                        String message = obj.getString("message");

                        loginlistmap.put("REsult", result);

                        loginlistmap.put("Message", message);

                        LoginList.add(loginlistmap);

                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Json parsing error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        protected void onPostExecute(String file_url) {


            if (loginlistmap.get("REsult").equals("1")) {
                Intent i = new Intent(LoginActivity.this, DashBoard.class);
                startActivity(i);
                LoginActivity.this.finish();

            } else if (loginlistmap.get("REsult").equals("0")){
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Login Incorrect");
                builder.setMessage(loginlistmap.get("Message"))
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                username.setText("");
                                pass_word.setText("");
                            }
                        });

                builder.show();
            }
        }
    }
}

