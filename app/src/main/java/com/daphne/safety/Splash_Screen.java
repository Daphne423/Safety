package com.daphne.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Splash_Screen extends AppCompatActivity {
    ImageView logo;
    final static int SPLASH_SCREEN=4500;
    FirebaseAuth firebaseAuth;

    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth = FirebaseAuth.getInstance();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent welcomeIntent = new Intent(Splash_Screen.this, LoginActivity.class);
                    startActivity(welcomeIntent);
                }

            }

        };
        thread.start();
//        Objects.requireNonNull(getSupportActionBar()).hide();
//
//        logo=findViewById(R.id.log);

        //animation
//        rotateAnimation();
//
//        logo.animate().translationY(-3000).setDuration(1000).setStartDelay(3500);
//        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(Splash_Screen.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }, SPLASH_SCREEN);
//    }
//    private void rotateAnimation() {
//
//        animation= AnimationUtils.loadAnimation(this,R.anim.rotate_anim);
//        logo.startAnimation(animation);
//
  }


}