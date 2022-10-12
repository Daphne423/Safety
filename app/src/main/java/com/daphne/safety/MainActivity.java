package com.daphne.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
//import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    CardView contact, location, tips,about;
    ImageButton button;
    // nav
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

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

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}