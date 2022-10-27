package com.daphne.safety;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContactActivity extends Activity implements View.OnClickListener, MyAdapter.DeleteListener{


 private static final int RQS_PICK_CONTACT = 1131;

    ListView listNumbers;
    Button btnAdd1;
    ImageView img;
    String deletenumber;
    String currentUser;
    //Initialize firebase
    private FirebaseAuth auth;
    private FirebaseFirestore db;
//    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

//        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

//        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
//        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);


        listNumbers = findViewById(R.id.listNumbers);
        btnAdd1 = findViewById(R.id.btnAdd1);
        img = findViewById(R.id.img);
        btnAdd1.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        //names = findViewById(R.id.t1);

        //addContact();
        loadData();
    }

    String name;
    private void addContact() {


    }

    private void loadData() {
        MyDatabase obj = new MyDatabase(this);
        ArrayList<Contacts> list = obj.loadContacts();
        MyAdapter adapter = new MyAdapter(this, R.layout.item_contact, list);
        adapter.setDeleteListener(this);
        listNumbers.setAdapter(adapter);





        if (list.size() > 0) {
            img.setVisibility(View.GONE);
        } else {

            img.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View view) {
        if (view == btnAdd1) {
           // pickContact();
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            startActivityForResult(intent, RQS_PICK_CONTACT);


//       intent.putExtra(ContactActivity.EXTRA_SHOW_CHECK_ALL, true)
//                    .putExtra(ContactActivity.EXTRA_SELECT_CONTACTS_LIMIT, 0)
//    .putExtra(ContactActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, false)
//    .putExtra(ContactActivity.EXTRA_WITH_GROUP_TAB, false)


        }

    }

//    private void pickContact(){
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                ContactsContract.Contacts.CONTENT_URI);
//        startActivityForResult(intent, RQS_PICK_CONTACT);
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQS_PICK_CONTACT) {
          if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contacts obj = new Contacts();
                obj.setName(name);
                obj.setNumber(number);

         //Send Contact to Firebase
              Map<String, Object> docData = new HashMap<>();
              docData.put("name", name);
              docData.put("number", number);

              db.collection("EmergencyContacts").
                      document(Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getUid()))
                      .collection("MyEmergencyContacts")
                      .document(number)
                      .set(docData)
                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void aVoid) {
                              Log.d(TAG, "DocumentSnapshot successfully written!");
                          }
                      })
                      .addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Log.w(TAG, "Error writing document", e);
                          }
                      });



              MyDatabase db = new MyDatabase(this);
                if (db.saveContact(obj)) {

                    Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not added", Toast.LENGTH_SHORT).show();
                }
                loadData();
                //contactName.setText(name);
//                contactNumber.setText(number);
                //contactEmail.setText(email);
            }
        }
    }

    @Override
    public void deleteItem(Contacts c) {
      //Delete contact from Firebase
        deletenumber = c.getNumber();
        db.collection("EmergencyContacts").
                document(Objects.requireNonNull(Objects.requireNonNull(auth.getCurrentUser()).getUid()))
                .collection("MyEmergencyContact")
                .document(deletenumber)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });


        MyDatabase db = new MyDatabase(this);
        if (db.deleteContact(c.getNumber())) {







            Toast.makeText(this, "DELETED SUCCESSFULLY", Toast.LENGTH_SHORT).show();

            loadData();
        } else {
            Toast.makeText(this, "SOME ERROR OCCURRED", Toast.LENGTH_SHORT).show();

        }
    }


}

//    ImageButton button = (ImageButton) findViewById(R.id.pick_contact);
//        if (button != null) {
//                button.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        Intent intent = new Intent(DemoActivity.this, ContactPickerActivity.class)
//        .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ?
//        R.style.Theme_Dark : R.style.Theme_Light)
//
//        .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE,
//        ContactPictureType.ROUND.name())
//
//        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION,
//        ContactDescription.ADDRESS.name())
//        .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
//        .putExtra(ContactPickerActivity.EXTRA_SELECT_CONTACTS_LIMIT, 0)
//        .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, false)
//        .putExtra(ContactPickerActivity.EXTRA_WITH_GROUP_TAB, false)
//
//        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE,
//        ContactsContract.CommonDataKinds.Email.TYPE_WORK)
//
//        .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER,
//        ContactSortOrder.AUTOMATIC.name());
//
//        startActivityForResult(intent, REQUEST_CONTACT);
//        }
//        });
//        }
//        else {
//        finish();
//        }