package com.daphne.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class LoginActivity extends AppCompatActivity {
    //EditText name,password;
    Button login_btn;
    private EditText email,password;
    //TextView reg_log;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference reference;
    private String userID;
    //private String str_email,str_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        email =  (EditText) findViewById(R.id.user_email2);
        password = (EditText) findViewById(R.id.user_pass_log);
        //login_btn = findViewById(R.id.login_btn);
        //reg_log = findViewById(R.id.reg_log);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    public void LoginUser(View view) {
        //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        //startActivity(intent);
        String str_email=email.getText().toString();
        String str_pass=password.getText().toString();

        if(str_email.isEmpty()){
            email.setError("Please fill field");
            email.requestFocus();
            return;
        }
        if(str_pass.isEmpty()){
            password.setError("Please fill field");
            password.requestFocus();
            return;
        }
        progressDialog.setMessage("Logging In...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(str_email, str_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    userID = user.getUid();
                    reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else {
                    Toast.makeText(LoginActivity.this,"login failrd",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //private void Validation() {



    //}

//    private void checkFromDb() {
//        progressDialog.setMessage("Checking user...");
//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put("online", "true");
//
//        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
////        reference.child(str_e);
//        reference.child(firebaseAuth.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                progressDialog.dismiss();
//                startActivity(new Intent(LoginActivity.this,MainActivity.class));
//                finish();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(LoginActivity.this,"Text",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        // end
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    String db_pass = snapshot.child("password").getValue(String.class);
//                    if (str_pass.equals(db_pass)) {
//                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
////                        intent.putExtra("name", str_name);
//                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//
//                        startActivity(intent);
//                        finish();
//                    }
//                    else{
//                        Toast.makeText(getApplicationContext(),"password Incorrect", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                else{
//                    Toast.makeText(LoginActivity.this, "Record not found!!", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
   // }



    public void FaceLogin(View view) {
        Uri uri = Uri.parse("http://www.facebook.com"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void GoLogin(View view) {
        Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void Forgot(View view) {
        Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
        startActivity(intent);


    }

    public void RegisterUser(View view) {
        Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
        startActivity(intent);
    }
}


