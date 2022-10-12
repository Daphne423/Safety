package com.daphne.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class NavActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    CardView contact, location, tips,about;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        contact = findViewById(R.id.Contact);
        location = findViewById(R.id.location);
        tips = findViewById(R.id.tips);
        about = findViewById(R.id.about);
        button = findViewById(R.id.buttonsend);
        // declaring this on click listener




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

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();



         if (id == R.id.nav_share) {

            getSupportActionBar().setTitle("Share");
            //getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProfileFragment()).commit();

        } else if (id == R.id.nav_settings) {

            getSupportActionBar().setTitle("Settings");
            //getSupportFragmentManager().beginTransaction().replace(R.id.container,new SettingsFragment()).commit();


        }
        else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(loginActivity);
            finish();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.my_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }else if ( == R.id.nav_logout){
//            logout();
//        }
//        return super.onOptionsItemSelected(item);
    }

//private void logout(){
//        FirebaseAuth.getInstance().signOut();
//
//        Intent intent = new Intent(getApplicationContext(),NavActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);





