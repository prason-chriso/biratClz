 package com.example.prason.biratclz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static java.lang.Thread.sleep;

 public class MainActivity extends AppCompatActivity {

     Boolean firstTime=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(firstTime) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                        firstTime=false;
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
        Intent i = new Intent(MainActivity.this, MainPageRegulator.class);
        startActivity(i);
    }

     @Override
     public void onBackPressed() {
         super.onBackPressed();
         this.finish();
     }
 }
