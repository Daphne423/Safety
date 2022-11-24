package com.daphne.safety;

import static android.app.ProgressDialog.show;

import static androidx.core.os.LocaleListCompat.create;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
//import android.view.KeyEvent;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    CardView contact, location1, tips, about;
    ImageButton moreBtn;
    //Button button;
    FirebaseAuth firebaseAuth;
    ImageButton reports;
    TextView nameTv;
    Button btnSendSms, btnSafeLocation;
    Context context;
//    FusedLocationProviderClient fusedLocationProviderClient;
//    double currentLat = 0, currentLong = 0;

    // nav
    public DrawerLayout drawerLayout;
    private double latitude,longitude;


    //public ActionBarDrawerToggle actionBarDrawerToggle;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contact = findViewById(R.id.Contact);
        location1 = findViewById(R.id.location);
        tips = findViewById(R.id.tips);
        about = findViewById(R.id.about);
        btnSafeLocation = findViewById(R.id.safeLocation);
        btnSendSms = findViewById(R.id.btnSendSms);


        // declaring this on click listener
        moreBtn = findViewById(R.id.moreBtn);
        nameTv = findViewById(R.id.name);
        reports = findViewById(R.id.reports);


        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AnalysisActivity.class);
                startActivity(intent);
            }
        });
///////////////////////////////////////////Was working no error but Crashed
        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // generate an MaterialAlertDialog Box
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Send Message Alert");
                builder.setMessage("Are you sure want to send an emergency message?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO: Consider calling
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            return;
//                        }
//                        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);



//                            double longitude = location.getLongitude();
//                            double latitude = location.getLatitude();







                        // get the list of all the contacts in Database
                DbHelper db = new DbHelper(MainActivity.this);
                List<ContactModel> list = db.getAllContacts();

                // send SMS to each contact
                for (ContactModel c : list) {
                    String message = "Hey,In an emergency situation."+""+ "Sending my location now,,..."+""+""+ "http://maps.google.com/?q="+latitude+","+longitude;


                    sms.sendTextMessage(c.getPhoneNo(), null, message, null, null);
                    Toast.makeText(getApplicationContext(), "Sending Message.....Message Sent successfully!",
                            Toast.LENGTH_LONG).show();
                }
//                        //addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                        String phoneNumber = "0792935763";
//
//
//                    String message = "Hey,I am in danger.Please help me out"+"Sending my location now"+"http://maps.google.com/?q="+latitude+","+longitude;
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phoneNumber,null,message,null,null);
////                        Toast.makeText(context, "Sending Message...", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

//        btnSendSms.setOnClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                // generate an MaterialAlertDialog Box
//                // Use the Builder class for convenient dialog construction
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Send Message Alert");
//                builder.setMessage("Are you sure want to send an emergency message?")
//                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent intent = new Intent(MainActivity.this, SendActivity.class);
//                                startActivity(intent);
//                                finish();
//                                Toast.makeText(context, "Sending Message...", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                builder.dismiss();
//                  }
//                        });
//                // Create the AlertDialog object and return it
//                return builder.create();
//
//                return true;
//            }
//        });


//        btnSendSms.setOnClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                // generate an MaterialAlertDialog Box
//                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(context, R.style.AlertDialogCustom);
//                alertDialogBuilder.setTitle("Send Message Alert");
//                alertDialogBuilder.setMessage("Are you sure want to send an emergency message?");
//                alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(MainActivity.this, SendActivity.class);
//                        startActivity(intent);
//                        finish();
//                        Toast.makeText(context, "Sending Message...", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                AlertDialog alertDialog = alertDialogBuilder
//                        .show();
//                return alertDialog;
//            }
//        });
//        public Dialog showDialog(String title, String msg, final Activity activity) {
//
//            final AlertDialog alertDialog = new AlertDialog.Builder(activity)
//                    .create();
//            alertDialog.setTitle(title);
//            alertDialog.setMessage(msg);
//            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    alertDialog.dismiss();
//
//                    activity.finish();
//
//                }
//            });
//            alertDialog.show();
//
//            return alertDialog;
//
//        }
//



        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);
        btnSafeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager sms = SmsManager.getDefault();

                // get the list of all the contacts in Database
                DbHelper db = new DbHelper(MainActivity.this);
                List<ContactModel> list = db.getAllContacts();

                // send SMS to each contact
                for (ContactModel c : list) {
                    String message = "Hey, I am now at a SAFE Location"+","+"Thanks for the lookout.";
                    sms.sendTextMessage(c.getPhoneNo(), null, message, null, null);
                }
                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        // pop up menu
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, moreBtn);
        // add menu items to our menu
//        popupMenu.getMenu().add("Settings");
//        popupMenu.getMenu().add("Share");
        popupMenu.getMenu().add("Logout");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuitem) {
                if (menuitem.getTitle() == "Settings") {
                    startActivity(new Intent(MainActivity.this, MySettingsActivity.class));

                } else if (menuitem.getTitle() == "Share") {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here ");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, "Text");
                    startActivity(Intent.createChooser(sharingIntent, "share via"));

                } else if (menuitem.getTitle() == "Logout") {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("online", "false");
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
                        //String myname = ""+dataSnapshot.child("name").getValue();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String name = "" + ds.child("name").getValue();

                            if(name.equals("Daphne Wambugu ")){
                                reports.setVisibility(View.VISIBLE);
                            }else{
                                reports.setVisibility(View.GONE);
                            }


                            nameTv.setText(name);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        contact.setOnClickListener(this);
        location1.setOnClickListener(this);
        tips.setOnClickListener(this);
        about.setOnClickListener(this);
        //button.setOnClickListener(this);
//        button.setOnClickListener(this);


//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                SmsManager sms = SmsManager.getDefault();
//
//                // get the list of all the contacts in Database
//                DbHelper db = new DbHelper(MainActivity.this);
//                List<ContactModel> list = db.getAllContacts();
//
//                // send SMS to each contact
//                for (ContactModel c : list) {
//                    String message = "Hey, " + c.getName() + "I am now at a SAFE PLACE,Thanks.";
//
//
//                    sms.sendTextMessage(c.getPhoneNo(), null, message, null, null);
//                }
//                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
//                        Toast.LENGTH_LONG).show();
//
//            }
//        })
//        ;
    }

    //       btnSafeLocation.
    private void stopMessage() {
        SmsManager sms = SmsManager.getDefault();

        // get the list of all the contacts in Database
        DbHelper db = new DbHelper(MainActivity.this);
        List<ContactModel> list = db.getAllContacts();

        // send SMS to each contact
        for (ContactModel c : list) {
            String message = "Hey, " + c.getName() + "I am now at a SAFE Location,Thanks for the lookout.";
            sms.sendTextMessage(c.getPhoneNo(), null, message, null, null);
        }
        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                Toast.LENGTH_LONG).show();

    }

//    private void onSuccess(Location location) {
//
//
//    }

//    private void getCurrentLocation() {
//    }

    SmsManager sms = SmsManager.getDefault();
    private void sendMessage() {



        //   get the list of all the contacts in Database
        DbHelper db = new DbHelper(MainActivity.this);
        List<ContactModel> list = db.getAllContacts();
//            displaylocation();

        for (ContactModel c : list) {
            String phone = "+254792935763";
            String message = "Hey, " + c.getName() + "I am in DANGER, i need help. Please urgently reach me out. Here are my coordinates.\n " + "http://maps.google.com/?q=";
//                    + location.getLatitude() + "," + location.getLongitude();
            sms.sendTextMessage(phone, null, message, null, null);

        }

    }





//        String messageToSend = "this is a message";
//        String number = "+254780754884";
//        sms.sendTextMessage(c.getPhoneNo(), null, message, null, null);

//        sms.sendTextMessage(number, null, messageToSend, null,null);





//    public void onSuccess(Location location) {
//        // check if location is null
//        // for both the cases we will
//        // create different messages
//        if (location != null) {
//
//            // get the SMSManager
//            SmsManager smsManager = SmsManager.getDefault();
//
//            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//            sendIntent.putExtra("sms_body", "default content");
//            sendIntent.setType("vnd.android-dir/mms-sms");
//            startActivity(sendIntent);
//
//            // get the list of all the contacts in Database
//            DbHelper db = new DbHelper(SensorService.this);
//            List<ContactModel> list = db.getAllContacts();
//
//            // send SMS to each contact
//            for (ContactModel c : list) {
//                String message = "Hey, " + c.getName() + "I am in DANGER, i need help. Please urgently reach me out. Here are my coordinates.\n " + "http://maps.google.com/?q=" + location.getLatitude() + "," + location.getLongitude();
//                smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);
//            }
//        } else {
//            String message = "I am in DANGER, i need help. Please urgently reach me out.\n" + "GPS was turned off.Couldn't find location. Call your nearest Police Station.";
//            SmsManager smsManager = SmsManager.getDefault();
//            DbHelper db = new DbHelper(SensorService.this);
//            List<ContactModel> list = db.getAllContacts();
//            for (ContactModel c : list) {
//                smsManager.sendTextMessage(c.getPhoneNo(), null, message, null, null);
//            }
//        }
//    }

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

        switch (v.getId()) {
            case R.id.Contact:
                i = new Intent(this, ContactActivity.class);
                startActivity(i);
                break;
            case R.id.location:
                i = new Intent(this, LocationActivity.class);
                startActivity(i);
                break;
            case R.id.tips:
                i = new Intent(this, TipsActivity.class);
                startActivity(i);
                break;
            case R.id.about:
                i = new Intent(this, AboutActivity.class);
                startActivity(i);
            default:
                break;

//            case R.id.buttonsend:i = new Intent(this, ButtonActivity.class);startActivity(i);
//            default:break;

//            //Getting intent and PendingIntent instance
//            PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, i,0);
//
//             //get the SMSManager

        }

//    public void stopService(View view) {
//        Intent notificationIntent = new Intent(this,SensorService.class);
//        notificationIntent.setAction("stop");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            getApplicationContext().startForegroundService(notificationIntent);
//            Snackbar.make(findViewById(android.R.id.content),"Service Stopped!", Snackbar.LENGTH_LONG).show();
//        }
//    }
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


////////////////////////////////////////////////////////////////////////
//    public void stopService(View view) {
//
//
//
//        //Getting intent and PendingIntent instance
//            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//            PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);
//
        // get the SMSManager
//            SmsManager sms = SmsManager.getDefault();

//        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//        sendIntent.putExtra("sms_body", "default content");
//        sendIntent.setType("vnd.android-dir/mms-sms");
//        startActivity(sendIntent);


        // get the list of all the contacts in Database
//            DbHelper db = new DbHelper(MainActivity.this);
//            List<ContactModel> list = db.getAllContacts();
//
//            // send SMS to each contact
//            for (ContactModel c : list) {
//                String message = "Hey, " + c.getName() + "I am now at a SAFE PLACE,Thanks.";
////                smsText = edittextSmsText.getText().toString();
//                sms.sendTextMessage(c.getPhoneNo(), null, message, null, null);
//            }
//        Toast.makeText(getApplicationContext(), "Message Sent successfully!",
//                Toast.LENGTH_LONG).show();
        //////////////////////////////////////////////////////////////////////////
//                Toast.makeText(getApplicationContext(), "SMS Sent!";
//                Toast.LENGTH_LONG).show();


//    private void show() {
//    }


//try {
//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage(phoneNo, null, sms, null, null);
//        Toast.makeText(getApplicationContext(), "SMS Sent!",
//        Toast.LENGTH_LONG).show();
//        //...
//        }

    }
}