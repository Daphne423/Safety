package com.daphne.safety;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.provider.ContactsContract;

import androidx.appcompat.app.AppCompatActivity;

import java.text.BreakIterator;
import java.util.ArrayList;

public class ContactsPickerActivity extends AppCompatActivity {
    private static final int CONTACT_PICK_REQUEST = 1;
    ListView contactsChooser;
    Button btnDone;
    EditText txtFilter;
    TextView txtLoadInfo;
    ContactsListAdapter contactsListAdapter;
    ContactsLoader contactsLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactsChooser = (ListView) findViewById(R.id.lst_contacts_chooser);
        btnDone = (Button) findViewById(R.id.btn_done);
        txtFilter = (EditText) findViewById(R.id.txt_filter);
        txtLoadInfo = (TextView) findViewById(R.id.txt_load_progress);


        contactsListAdapter = new ContactsListAdapter(this, new ContactsList());

        contactsChooser.setAdapter(contactsListAdapter);


        loadContacts("");


        txtFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactsListAdapter.filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (contactsListAdapter.selectedContactsList.contactArrayList.isEmpty()) {
                    setResult(RESULT_CANCELED);
                } else {

                    Intent resultIntent = new Intent();

                    resultIntent.putParcelableArrayListExtra("SelectedContacts", contactsListAdapter.selectedContactsList.contactArrayList);
                    setResult(RESULT_OK, resultIntent);

                }
                finish();

            }
        });
    }


    private void loadContacts(String filter) {

        if (contactsLoader != null && contactsLoader.getStatus() != AsyncTask.Status.FINISHED) {
            try {
                contactsLoader.cancel(true);
            } catch (Exception e) {

            }
        }
        if (filter == null) filter = "";

        try {
            //Running AsyncLoader with adapter and  filter
            contactsLoader = new ContactsLoader(this, contactsListAdapter);
            contactsLoader.txtProgress = txtLoadInfo;
            contactsLoader.execute(filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CONTACT_PICK_REQUEST && resultCode == RESULT_OK) {

            ArrayList<Parcelable> selectedContacts = data.getParcelableArrayListExtra("SelectedContacts");

            String display = "";
            for (int i = 0; i < selectedContacts.size(); i++) {

                display += (i + 1) + ". " + selectedContacts.get(i).toString() + "\n";

            }



            contacts.setText("Selected Contacts : \n\n" + display);

        }

    }
}
