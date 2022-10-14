package com.daphne.safety;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TipsActivity extends AppCompatActivity {
    TextView linktext, linktext2;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        linktext = findViewById(R.id.mylink1);
        linktext2 = findViewById(R.id.mylink2);
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

        loadLinks();
    }

    private void loadLinks() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("uid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String mylinks = "" + ds.child("Mylink").getValue();
                    String mylink2 = "" + ds.child("Mylinka").getValue();

                    linktext.setText(mylinks);
                    linktext2.setText(mylink2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

