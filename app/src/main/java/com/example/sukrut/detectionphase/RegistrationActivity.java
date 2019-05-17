package com.example.sukrut.detectionphase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 3/12/18.
 */

public class RegistrationActivity extends AppCompatActivity {

    EditText uname,lname,address,gender,age,phno,guardno,bloodgrp,desease,password;
    Spinner spgender,spbloodgrp;
    Button register;
    String url="http://192.168.43.145/DemoSite/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        uname = findViewById(R.id.edtreg_username);
        lname = findViewById(R.id.edt_lastname);
        address = findViewById(R.id.edt_address);
        gender = findViewById(R.id.edt_gender);
        spgender=findViewById(R.id.spinner1);
        spbloodgrp=findViewById(R.id.spinner2);
        age = findViewById(R.id.edt_age);
        phno = findViewById(R.id.edt_phoneno);
        guardno = findViewById(R.id.edt_guradianno);
        bloodgrp = findViewById(R.id.edt_bloodgroup);
        desease = findViewById(R.id.edt_disease);
        password = findViewById(R.id.edtreg_password);
        register = findViewById(R.id.btn_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.optString("success").trim();
                            String message = jsonObject.optString("message").trim();

                            if (success.equalsIgnoreCase("1") && message.equalsIgnoreCase("Register Successfull")) {
                                Toast.makeText(RegistrationActivity.this, "You Have Succesfully Registered", Toast.LENGTH_SHORT).show();
                                clearFields();
                                showDialog();

                            } else {
                                Toast.makeText(RegistrationActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("firstname", uname.getText().toString().trim());
                        params.put("lastname", lname.getText().toString().trim());
                        params.put("address", address.getText().toString().trim());
                        params.put("gender", spgender.getSelectedItem().toString().trim());
                        params.put("age", age.getText().toString().trim());
                        params.put("phoneno", phno.getText().toString().trim());
                        params.put("guardianno", guardno.getText().toString().trim());
                        params.put("bloodgroup", spbloodgrp.getSelectedItem().toString().trim());
                        params.put("disease", desease.getText().toString().trim());
                        params.put("password", password.getText().toString().trim());
                        return params;


                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(RegistrationActivity.this);
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
                .setMessage("You Have Succesfully Registered")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);

                     //   Toast.makeText(RegistrationActivity.this,"Sending Location To Hospital",Toast.LENGTH_LONG).show();

                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    void clearFields()
    {
        uname.setText("");
        lname.setText("");
        address.setText("");
        gender.setText("");
        phno.setText("");
        guardno.setText("");
        desease.setText("");
        password.setText("");
        register.setText("");

    }


}
