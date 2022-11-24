package com.daphne.safety;


import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    PieChart pieChart;
    int read;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private ImageButton backBtn;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        pieChart = (PieChart) findViewById(R.id.pieChart);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        //init UI views
        backBtn = findViewById(R.id.backBtn);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.9f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(31f);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        // get progress count

        //
        //DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Users");

        //
        //get orders
        ArrayList<PieEntry> yValues = new ArrayList<>();
        //read = progress;


        yValues.add(new PieEntry(0f, "January"));
        yValues.add(new PieEntry(1f, "February"));
        yValues.add(new PieEntry(0f, "March"));
        yValues.add(new PieEntry(2.5f, "April"));
        yValues.add(new PieEntry(3f, "May"));
        yValues.add(new PieEntry(0f, "June"));
        yValues.add(new PieEntry(6f, "July"));
        yValues.add(new PieEntry(2.5f, "August"));
        yValues.add(new PieEntry(6f, "September"));
        yValues.add(new PieEntry(7.5f, "October"));
        yValues.add(new PieEntry(2.5f, "November"));
        yValues.add(new PieEntry(0f, "December"));

        pieChart.animateY(1400, Easing.EaseInOutQuad);

        PieDataSet dataSet = new PieDataSet(yValues, "How many registered users how many have accessed the resources");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

        // end of progress count


    }
}