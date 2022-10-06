package com.daphne.safety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
//import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    CardView contact, location, tips,about;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contact = findViewById(R.id.Contact);
        location = findViewById(R.id.location);
        tips = findViewById(R.id.tips);
        about = findViewById(R.id.about);
        button = findViewById(R.id.buttonsend);
        // declaring this on click listener

        contact.setOnClickListener(this);
        location.setOnClickListener(this);
        tips.setOnClickListener(this);
        about.setOnClickListener(this);
        button.setOnClickListener(this);


    }

//    public boolean dispatchkeyEvent(KeyEvent event){
//        if(event.getKeyCode() == KeyEvent.KEYCODE_POWER){
//            Intent i = new Intent(this,MainActivity.class);
//            startActivity(i);
//            return true;
//        }
//        return super.dispatchKeyEvent(event);
//    }

//    public void Contact(View view) {
//        Intent intent = new Intent(MainActivity.this,ContactActivity.class);
//        startActivity(intent);
//    }
//
//    public void Location(View view) {
//        Intent intent = new Intent(MainActivity.this,LocationActivity.class);
//        startActivity(intent);
//    }
//
//    public void Tips(View view) {
//        Intent intent = new Intent(MainActivity.this,TipsActivity.class);
//        startActivity(intent);
//    }
//
//    public void About(View view) {
//        Intent intent = new Intent(MainActivity.this,AboutActivity.class);
//        startActivity(intent);
//    }
//
//    public void Button(View view) {
//        Intent intent = new Intent(MainActivity.this,ButtonActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()){
            case R.id.Contact:i=new Intent(this,MainScreen.class);startActivity(i);
            break;
            case R.id.location:i = new Intent(this,LocationActivity.class);startActivity(i);
            break;
            case R.id.tips:i= new Intent(this, TipsActivity.class);startActivity(i);
            break;
            case R.id.about:i = new Intent(this, AboutActivity.class);startActivity(i);
            break;
            case R.id.buttonsend:i = new Intent(this, ButtonActivity.class);startActivity(i);
            default:break;
        }
    }
}