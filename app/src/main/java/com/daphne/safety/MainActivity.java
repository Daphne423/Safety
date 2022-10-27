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
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    CardView contact, location, tips,about;
    ImageButton button,moreBtn;
    FirebaseAuth firebaseAuth;
    TextView nameTv;
    // nav
    public DrawerLayout drawerLayout;
    //public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contact = findViewById(R.id.Contact);
        location = findViewById(R.id.location);
        tips = findViewById(R.id.tips);
        about = findViewById(R.id.about);
        //button = findViewById(R.id.buttonsend);
        // declaring this on click listener
        moreBtn = findViewById(R.id.moreBtn);
        nameTv = findViewById(R.id.name);

        firebaseAuth = FirebaseAuth.getInstance();

        // pop up menu
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,moreBtn);
        // add menu items to our menu
        popupMenu.getMenu().add("Settings");
        popupMenu.getMenu().add("Share");
        popupMenu.getMenu().add("Logout");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuitem) {
                if(menuitem.getTitle() == "Settings"){
                    startActivity(new Intent(MainActivity.this,SettingsActivity.class));

                }else if(menuitem.getTitle() == "Share"){
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here ");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, "Text");
                    startActivity(Intent.createChooser(sharingIntent, "share via"));

                }else if(menuitem.getTitle() == "Logout"){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("online","false");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.child(firebaseAuth.getUid()).updateChildren(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    firebaseAuth.signOut();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                }
                            });
                }
                return true;
            }
        });
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds: dataSnapshot.getChildren()){
                                    String name = ""+ds.child("name").getValue();

                                    nameTv.setText(name);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        contact.setOnClickListener(this);
        location.setOnClickListener(this);
        tips.setOnClickListener(this);
        about.setOnClickListener(this);
        //button.setOnClickListener(this);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
//        drawerLayout = findViewById(R.id.my_drawer_layout);
        //actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
            case R.id.Contact:i=new Intent(this,ContactActivity.class);startActivity(i);
            break;
            case R.id.location:i = new Intent(this,LocationActivity.class);startActivity(i);
            break;
            case R.id.tips:i= new Intent(this, TipsActivity.class);startActivity(i);
            break;
            case R.id.about:i = new Intent(this, AboutActivity.class);startActivity(i);
            default:break;
//            case R.id.buttonsend:i = new Intent(this, ButtonActivity.class);startActivity(i);
//            default:break;
        }
    }
    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

}