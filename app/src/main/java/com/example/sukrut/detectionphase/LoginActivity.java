package com.example.sukrut.detectionphase;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 3/12/18.
 */

public class LoginActivity extends AppCompatActivity {

    Button login,signup;
    EditText username,password;
    String url="http://192.168.43.145/DemoSite/login.php";
    boolean status=false;
    LinearLayout mainlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newlogin);
        login=findViewById(R.id.btn_login);
        mainlayout=findViewById(R.id.mainlayout);
        signup=findViewById(R.id.btn_signup);
        username=findViewById(R.id.edt_lusername);
        password=findViewById(R.id.edt_lpassord);
        if(new Session().getUserName(LoginActivity.this).length() == 0)
        {
            mainlayout.setVisibility(View.VISIBLE);

        }
        else
        {
            Intent intent=new Intent(LoginActivity.this,DrivingActivity.class);
            startActivity(intent);
        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent(LoginActivity.this,RegistrationActivity.class);
                startActivity(intent);


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String uname=username.getText().toString().trim();
                final String pass=password.getText().toString().trim();
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.optString("success").trim();
                            String message=jsonObject.optString("message").trim();

                            if(success.equalsIgnoreCase("1")&&message.equalsIgnoreCase("Login Successfull"))
                            {
                                Toast.makeText(LoginActivity.this,"You Have Succesfully loged in",Toast.LENGTH_SHORT).show();
                                Session session=new Session();
                                session.setUserName(getApplicationContext(),uname);
                                Intent intent=new Intent(LoginActivity.this,DrivingActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                               // Toast.makeText(LoginActivity.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
                                   showDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_SHORT).show();


                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params= new HashMap<>();
                        params.put("firstname",uname);
                        params.put("password",pass);
                        return params;


                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);

            }
        });
    }

    public void showDialog()
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Registration")
                .setMessage("Invalid Credentials")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {



                        //   Toast.makeText(RegistrationActivity.this,"Sending Location To Hospital",Toast.LENGTH_LONG).show();

                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
