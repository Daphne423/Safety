package com.daphne.safety;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.HttpsCallableResult;

import java.util.HashMap;
import java.util.Map;

public class TipsActivity extends AppCompatActivity {
    TextView linktext, linktext2,linktext3, linktext4,linktext5,linktext6;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        linktext = findViewById(R.id.mylink1);
        linktext2 = findViewById(R.id.mylink2);
        linktext3 = findViewById(R.id.mylink3);
        linktext4 = findViewById(R.id.mylink4);
        linktext5 = findViewById(R.id.mylink5);
        linktext6 = findViewById(R.id.mylink6);


        firebaseAuth = FirebaseAuth.getInstance();
        linktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linktext.getText().toString();
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else {

                    //Log.d("Implicint", "cant handle");
                }
            }
        });
        linktext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linktext2.getText().toString();
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else {

                    //Log.d("Implicint", "cant handle");
                }
            }
        });
        linktext3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linktext3.getText().toString();
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else {

                    //Log.d("Implicint", "cant handle");
                }
            }
        });
        linktext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linktext4.getText().toString();
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else {

                    //Log.d("Implicint", "cant handle");
                }
            }
        });
        linktext5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linktext5.getText().toString();
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else {

                    //Log.d("Implicint", "cant handle");
                }
            }
        });
        linktext6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = linktext6.getText().toString();
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }else {

                    //Log.d("Implicint", "cant handle");
                }
            }
        });


        loadLinks();
    }

    private void loadLinks() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Links");
        reference.orderByChild("Mylinks").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String mylinks = "" + ds.child("link").getValue();
                    String mylink2 = "" + ds.child("linka").getValue();
                    String mylink3 = "" + ds.child("linkb").getValue();
                    String mylink4 = "" + ds.child("linkc").getValue();
                    String mylink5 = "" + ds.child("linkd").getValue();
                    String mylink6 = "" + ds.child("linke").getValue();

                    linktext.setText(mylinks);
                    linktext2.setText(mylink2);
                    linktext3.setText(mylink3);
                    linktext4.setText(mylink4);
                    linktext5.setText(mylink5);
                    linktext6.setText(mylink6);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

