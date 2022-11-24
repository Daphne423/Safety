package com.daphne.safety;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AnalysisActivity extends AppCompatActivity {
    Button btnBar,btnChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);


        btnBar = findViewById(R.id.btnBar);
//        btnLine = findViewById(R.id.btnLine);
        btnChart = findViewById(R.id.btnPie);


        btnBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnalysisActivity.this, BarChartActivity.class);
                startActivity(intent);

            }
        });

//        btnLine.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AnalysisActivity.this, LineGraphActivity.class);
//                startActivity(intent);
//
//            }
//
//        });

        btnChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnalysisActivity.this, PieChartActivity.class);
                startActivity(intent);

            }

        });


    }
}