package com.daphne.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    private EditText emailedittext;
    private Button resetPasswordButton;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emailedittext = (EditText) findViewById(R.id.edit_text_enter_email);
        resetPasswordButton = (Button) findViewById(R.id.ResetPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });
    }

    private void resetpassword() {
        String email = emailedittext.getText().toString().trim();
        if(email.isEmpty()){
            emailedittext.setError("Email is required!");
            emailedittext.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailedittext.setError("Email is required ");
            emailedittext.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this,"Check your email",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPassword.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ForgetPassword.this, "Something went wrong, try again",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}