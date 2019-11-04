package com.contact.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.contact.demo.R;


public class ActivitySplashScreen extends AppCompatActivity {
    private TextView tvAppname;

    // Animation
    private Animation animAppName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        init();

        //After loading animatio set animation to particuler view(Button, Image, etc.)

        tvAppname.setAnimation(animAppName);

        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                    // session.CheckLogin();
                    Intent intent = new Intent(ActivitySplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    private void init() {
        animAppName = AnimationUtils.loadAnimation(this, R.anim.appname_anim);
        tvAppname = findViewById(R.id.tvAppname);
    }
}
