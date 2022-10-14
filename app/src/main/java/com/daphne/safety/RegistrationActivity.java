package com.daphne.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    TextInputEditText name, password, email, phone, con_pass;
    TextView sign_in;
    Button reg;
    ProgressDialog progressDialog;
    private Object Matcher;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.user_name);
        password = findViewById(R.id.user_pass);
        sign_in = findViewById(R.id.sign_in);
        reg = findViewById(R.id.reg_btn);
        email = findViewById(R.id.user_email);
        phone = findViewById(R.id.user_phone);
        con_pass = findViewById(R.id.user_con_pass);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

       // sendlinkstodb();

    }



    //progress
    public void onClick(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait....");
        progressDialog.setCanceledOnTouchOutside(false);
    }


    public void Sign(View view) {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void Register(View view) {
        //Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        //startActivity(intent);
        Validation();

    }
    private String str_name, str_pass, str_mail, str_conf_pass, str_phone;



    private void Validation() {
        str_name = name.getText().toString();
        str_mail = email.getText().toString();
        str_pass = password.getText().toString();
        str_conf_pass = con_pass.getText().toString();
        str_phone = phone.getText().toString();

        if (str_name.isEmpty()) {
            name.setError("Please fill field");
            name.requestFocus();
            return;
        }
        if (str_phone.isEmpty()) {
            phone.setError("Please fill field");
            phone.requestFocus();
            return;
        }
        if (!numberCheck(str_phone)) {
            phone.setError("Invalid Mobile No.");
            phone.requestFocus();
            return;
        }
        if (str_mail.isEmpty()) {
            email.setError("Please fill field");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(str_mail).matches()){
            email.setError("Please enter valid email");
            email.requestFocus();
            return;
        }
        if (str_pass.isEmpty()) {
            password.setError("Please fill field");
            password.requestFocus();
            return;
        }
        else if(!passwordValidation(str_pass)){
            password.setError("Enter maximum 12 digits");

        }
        if (str_conf_pass.isEmpty()) {
            con_pass.setError("Password not matched");
            con_pass.requestFocus();
            return;
        }
         createAccount();
    }

    private boolean passwordValidation(String str_pass) {
        Pattern p = Pattern.compile(".{12}");
             Matcher m = p.matcher(str_pass);
           return m.matches();
    }

    private boolean numberCheck(String str_phone) {
        Pattern p = Pattern.compile("[0-9]{10}");
                Matcher m = p.matcher(str_phone);
                return m.matches();
    }

    private void createAccount() {
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(str_mail,str_pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                sendDataToDb();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });



    }

    private void sendDataToDb() {
        progressDialog.setMessage("Saving to firebase");
       final String regtime=""+System.currentTimeMillis();

        HashMap<String,Object> data=new HashMap<>();
        data.put("uid",""+firebaseAuth.getUid());
        data.put("name",""+str_name);
        data.put("email",""+str_mail);
        data.put("password",""+str_pass);
        data.put("phone",""+str_phone);
        data.put("time",""+regtime);
        data.put("Mylink", "linksss");
        data.put("Mylinka", "linksssa");
//        data.put("Mylinkb", "linksssb");
//        data.put("Mylinkc", "linksssc");
//        data.put("Mylinkd", "linksssd");
//        data.put("Mylinke", "linkssse");
//        data.put("Mylinkf", "linksssf");
//        data.put("Mylinkg", "linksssg");



        //database
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
       //reference.child(str_name).setValue(data);
       reference.child(firebaseAuth.getUid()).setValue (data).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
               //db update
              progressDialog.dismiss();
               //Dashboard Activity Intent
               startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
               finish();
             //Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            //startActivity(intent);

           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               //failed updating db
               progressDialog.dismiss();


           }
       });
//        ValueEventListener registration_successful = reference.addValueEventListener(new OnSuccessListener<>())
//            private Object Intent;
//
//            @Override
//            public void onSuccess(Void unused) {
//                //db update
//                progressDialog.dismiss();
//                Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//
//
//                //Dashboard Activity Intent
//                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//                startActivity((android.content.Intent) Intent);
//            }
//
//                    .addOnFailureListener(new OnFailureListener(){
//                     @Override
//                public void OnFailure(@NonNull Exception e){
//
//                //failed updating db
//                    progressDialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT);
//                }
//
//            }






    }}