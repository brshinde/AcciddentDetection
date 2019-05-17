package com.example.sukrut.detectionphase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


/**
 * Created by root on 3/12/18.
 */

public class DrivingActivity extends AppCompatActivity {

    Button driving;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startdriving);
        driving=findViewById(R.id.btn_startdriving);

        driving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(DrivingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
