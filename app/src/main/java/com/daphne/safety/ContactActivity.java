package com.daphne.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactActivity extends Activity implements View.OnClickListener, MyAdapter.DeleteListener{
    private static int PICK_CONTACT = 0;

//    private static final int RQS_PICK_CONTACT = 0;
//    private static final int CONTACT_PICKER_RESULT = 0;
    //    private static final boolean EXTRA_SHOW_CHECK_ALL >0 ;
//    private static final String EXTRA_SELECT_CONTACTS_LIMIT = ;
//    private static final String EXTRA_ONLY_CONTACTS_WITH_PHONE = ;
//    private static final String EXTRA_WITH_GROUP_TAB = ;
    ListView listNumbers;
    Button btnAdd1;
    ImageView img;
    TextView names;

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

        //names = findViewById(R.id.t1);

        addContact();
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
            pickContact();
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
//            startActivityForResult(intent, RQS_PICK_CONTACT);


//       intent.putExtra(ContactActivity.EXTRA_SHOW_CHECK_ALL, true)
//                    .putExtra(ContactActivity.EXTRA_SELECT_CONTACTS_LIMIT, 0)
//    .putExtra(ContactActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, false)
//    .putExtra(ContactActivity.EXTRA_WITH_GROUP_TAB, false)


        }

    }

    private void pickContact(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT) {
          if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();

                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contacts obj = new Contacts();
                obj.setName(name);
                obj.setNumber(number);

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