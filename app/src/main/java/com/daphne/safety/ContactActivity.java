//package com.daphne.safety;
//
//import static android.content.ContentValues.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.ContactsContract;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//public class ContactActivity extends Activity implements View.OnClickListener, MyAdapter.DeleteListener{
//
//
// private static final int RQS_PICK_CONTACT = 1131;
//
//    ListView listNumbers;
//    Button btnAdd1;
//    ImageView img;
//    String deletenumber;
//    String currentUser;
//    //Initialize firebase
//    private FirebaseAuth auth;
//    private FirebaseFirestore db;
////    TextView name;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_contact);
//
////        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//
////        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
////        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
//
//
//        listNumbers = findViewById(R.id.listNumbers);
//        btnAdd1 = findViewById(R.id.btnAdd1);
//        img = findViewById(R.id.img);
//        btnAdd1.setOnClickListener(this);
//        db = FirebaseFirestore.getInstance();
//        auth = FirebaseAuth.getInstance();
//
//        //names = findViewById(R.id.t1);
//
//        //addContact();
//        loadData();
//    }
//
//    String name;
//    private void addContact() {
//
//
//    }
//
//    private void loadData() {
//        MyDatabase obj = new MyDatabase(this);
//        ArrayList<Contacts> list = obj.loadContacts();
//        MyAdapter adapter = new MyAdapter(this, R.layout.item_contact, list);
//        adapter.setDeleteListener(this);
//        listNumbers.setAdapter(adapter);
//
//
//
//
//
//        if (list.size() > 0) {
//            img.setVisibility(View.GONE);
//        } else {
//
//            img.setVisibility(View.VISIBLE);
//        }
//    }
//    @Override
//    public void onClick(View view) {
//        if (view == btnAdd1) {
//           // pickContact();
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
//            startActivityForResult(intent, RQS_PICK_CONTACT);
//
//
////       intent.putExtra(ContactActivity.EXTRA_SHOW_CHECK_ALL, true)
////                    .putExtra(ContactActivity.EXTRA_SELECT_CONTACTS_LIMIT, 0)
////    .putExtra(ContactActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, false)
////    .putExtra(ContactActivity.EXTRA_WITH_GROUP_TAB, false)
//
//
//        }
//
//    }
//
////    private void pickContact(){
////        Intent intent = new Intent(Intent.ACTION_PICK,
////                ContactsContract.Contacts.CONTENT_URI);
////        startActivityForResult(intent, RQS_PICK_CONTACT);
////
////    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RQS_PICK_CONTACT) {
//          if (resultCode == RESULT_OK) {
//                Uri contactData = data.getData();
//                Cursor cursor = managedQuery(contactData, null, null, null, null);
//                cursor.moveToFirst();
//
//                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                Contacts obj = new Contacts();
//                obj.setName(name);
//                obj.setNumber(number);
//
//         //Send Contact to Firebase
//              Map<String, Object> docData = new HashMap<>();
//              docData.put("name", name);
//              docData.put("number", number);
//
//              db.collection("EmergencyContacts").
//                      document(Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getUid()))
//                      .collection("MyEmergencyContacts")
//                      .document(number)
//                      .set(docData)
//                      .addOnSuccessListener(new OnSuccessListener<Void>() {
//                          @Override
//                          public void onSuccess(Void aVoid) {
//                              Log.d(TAG, "DocumentSnapshot successfully written!");
//                          }
//                      })
//                      .addOnFailureListener(new OnFailureListener() {
//                          @Override
//                          public void onFailure(@NonNull Exception e) {
//                              Log.w(TAG, "Error writing document", e);
//                          }
//                      });
//
//
//
//              MyDatabase db = new MyDatabase(this);
//                if (db.saveContact(obj)) {
//
//                    Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "Not added", Toast.LENGTH_SHORT).show();
//                }
//                loadData();
//                //contactName.setText(name);
////                contactNumber.setText(number);
//                //contactEmail.setText(email);
//            }
//        }
//    }
//
//    @Override
//    public void deleteItem(Contacts c) {
//      //Delete contact from Firebase
//        deletenumber = c.getNumber();
//        db.collection("EmergencyContacts").
//                document(Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getUid()))
//                .collection("MyEmergencyContact")
//                .document(deletenumber)
//                .delete()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error deleting document", e);
//                    }
//                });
//
//
//        MyDatabase db = new MyDatabase(this);
//        if (db.deleteContact(c.getNumber())) {
//
//
//
//
//
//
//
//            Toast.makeText(this, "DELETED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
//
//            loadData();
//        } else {
//            Toast.makeText(this, "SOME ERROR OCCURRED", Toast.LENGTH_SHORT).show();
//
//        }
//    }
//
//
//}
//
////    ImageButton button = (ImageButton) findViewById(R.id.pick_contact);
////        if (button != null) {
////                button.setOnClickListener(new View.OnClickListener() {
////@Override
////public void onClick(View v) {
////        Intent intent = new Intent(DemoActivity.this, ContactPickerActivity.class)
////        .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ?
////        R.style.Theme_Dark : R.style.Theme_Light)
////
////        .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE,
////        ContactPictureType.ROUND.name())
////
////        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION,
////        ContactDescription.ADDRESS.name())
////        .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
////        .putExtra(ContactPickerActivity.EXTRA_SELECT_CONTACTS_LIMIT, 0)
////        .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, false)
////        .putExtra(ContactPickerActivity.EXTRA_WITH_GROUP_TAB, false)
////
////        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE,
////        ContactsContract.CommonDataKinds.Email.TYPE_WORK)
////
////        .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER,
////        ContactSortOrder.AUTOMATIC.name());
////
////        startActivityForResult(intent, REQUEST_CONTACT);
////        }
////        });
////        }
////        else {
////        finish();
////        }
////////////////////////////////////////////////////////////////////////////////////////////////////

package com.daphne.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



import java.util.List;

public class ContactActivity extends AppCompatActivity {
    private static final int IGNORE_BATTERY_OPTIMIZATION_REQUEST = 1002;
    private static final int PICK_CONTACT = 1;

    // create instances of various classes to be used
    Button button1;
    ListView listView;
    DbHelper db;
    List<ContactModel> list;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_contact);

        // check for runtime permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS}, 100);
            }
        }

        // this is a special permission required only by devices using
        // Android Q and above. The Access Background Permission is responsible
        // for populating the dialog with "ALLOW ALL THE TIME" option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 100);
        }

        // check for BatteryOptimization,
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (pm != null && !pm.isIgnoringBatteryOptimizations(getPackageName())) {
                askIgnoreOptimization();
            }
        }

        // start the service
        SensorService sensorService = new SensorService();
        Intent intent = new Intent(this, sensorService.getClass());
        if (!isMyServiceRunning(sensorService.getClass())) {
            startService(intent);
        }


        button1 = findViewById(R.id.Button1);
        listView = (ListView) findViewById(R.id.ListView);
        db = new DbHelper(this);
        list = db.getAllContacts();
        customAdapter = new CustomAdapter(this, list);
        listView.setAdapter(customAdapter);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling of getContacts()
                if (db.count() != 5) {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, PICK_CONTACT);
                } else {
                    Toast.makeText(ContactActivity.this, "Can't Add more than 5 Contacts", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // method to check if the service is running
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service status", "Running");
                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }

    @Override
    protected void onDestroy() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, ReactivateService.class);
        this.sendBroadcast(broadcastIntent);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Permissions Denied!\n Can't use the App!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // get the contact from the PhoneBook of device
        switch (requestCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        // start sending to db

                        // stop

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        @SuppressLint("Range") String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String phone = null;
                        try {
                            if (hasPhone.equalsIgnoreCase("1")) {
                                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                                phones.moveToFirst();
                                phone = phones.getString(phones.getColumnIndex("data1"));
                            }
                            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            db.addcontact(new ContactModel(0, name, phone));
                            list = db.getAllContacts();
                            customAdapter.refresh(list);
                        } catch (Exception ex) {
                        }
                    }
                }
                break;
        }
    }

    // this method prompts the user to remove any
    // battery optimisation constraints from the App
    private void askIgnoreOptimization() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            @SuppressLint("BatteryLife") Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, IGNORE_BATTERY_OPTIMIZATION_REQUEST);
        }

    }

}
