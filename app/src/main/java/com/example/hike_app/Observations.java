package com.example.hike_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hike_app.models.HikeModel;
import com.example.hike_app.models.ObservationsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class Observations extends AppCompatActivity {
    private Toolbar toolbar;
    FloatingActionButton addObsBtn;
    RecyclerView recyclerView;
    ImageView emptyData_image;
    TextView emptyData;
    EditText addObs, addTimeObs, addCmtObs;
    Button addObsButton, cancelBtn;
    CustomAdapterForObs customAdapterForObs;
    ArrayList<ObservationsModel> observationsModels = new ArrayList<>();
    String hikeId;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observations);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        dialog = new Dialog(Observations.this);
        dialog.setContentView(R.layout.dialog_add_observation);

        addObs = dialog.findViewById(R.id.add_obs_txt);
        addTimeObs = dialog.findViewById(R.id.add_time_txt);
        addCmtObs = dialog.findViewById(R.id.add_comment_txt);
        addObsButton = dialog.findViewById(R.id.add_obs);
        cancelBtn = dialog.findViewById(R.id.cancel_btn);

        addObsBtn = findViewById(R.id.add_obs_btn);
        recyclerView = findViewById(R.id.listObservation);
        emptyData_image = findViewById(R.id.imageView);
        emptyData = findViewById(R.id.emptyData);
        hikeId = getIntent().getStringExtra("hike_id");

        customAdapterForObs = new CustomAdapterForObs(Observations.this, observationsModels);
        recyclerView.setAdapter(customAdapterForObs);
        recyclerView.setLayoutManager(new LinearLayoutManager(Observations.this));

        displayObs(Integer.parseInt(hikeId));
        customAdapterForObs.updateData(observationsModels);

        addObsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddObservation(Gravity.CENTER);
            }
        });
    }
    private void openAddObservation(int gravity){
        Window window = dialog.getWindow();
        if (window == null){ return; }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        getDateTimeNow();

        addObsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateObservation() | !validateAddedDate()){
                    return;
                }
                ObservationsModel obsModels = new ObservationsModel(-1, addObs.getText().toString(), addTimeObs.getText().toString(), addCmtObs.getText().toString(), Integer.parseInt(hikeId));
                DatabaseHelper db = new DatabaseHelper(Observations.this);
                db.addObservation(hikeId, obsModels);
                addObs.setText("");
                addTimeObs.setText("");
                addCmtObs.setText("");
                dialog.dismiss();

                displayObs(Integer.parseInt(hikeId));
                customAdapterForObs.notifyDataSetChanged();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addObs.setText("");
                addTimeObs.setText("");
                addCmtObs.setText("");
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void displayObs(int hikeId){
        observationsModels.clear();

        DatabaseHelper db = new DatabaseHelper(Observations.this);
        Cursor cursor = db.dataObsByIdHike(hikeId);

        if (cursor.getCount() == 0) {
            emptyData_image.setVisibility(View.VISIBLE);
            emptyData.setVisibility(View.VISIBLE);
            Toast.makeText(Observations.this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                observationsModels.add(new ObservationsModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4))
                );
            }
        }
    }
    private void getDateTimeNow(){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        if(currentHour < 10){
            String addedDate = currentDay + "/" + (currentMonth+1) + "/" + currentYear + " " + "0" + currentHour + ":" + currentMinute;
            addTimeObs.setText(addedDate);
        } else if (currentHour < 10 && currentMinute < 10) {
            String addedDate = currentDay + "/" + (currentMonth+1) + "/" + currentYear + " " + "0" + currentHour + ":0" + currentMinute;
            addTimeObs.setText(addedDate);
        } else if (currentMinute < 10) {
            String addedDate = currentDay + "/" + (currentMonth+1) + "/" + currentYear + " " + currentHour + ":0" + currentMinute;
            addTimeObs.setText(addedDate);
        } else {
            String addedDate = currentDay + "/" + (currentMonth+1) + "/" + currentYear + " " + currentHour + ":" + currentMinute;
            addTimeObs.setText(addedDate);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DetailHike.class);
                intent.putExtra("hike_id", hikeId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private boolean validateObservation() {
        String nameInput = addObs.getText().toString().trim();
        if(nameInput.isEmpty()){
            addObs.setError("Observation's content can not be empty");
            return false;
        } else {
            addObs.setError(null);
            return true;
        }
    }
    private boolean validateAddedDate() {
        String locationInput = addTimeObs.getText().toString().trim();
        if (locationInput.isEmpty()) {
            addTimeObs.setError("Location can not be empty");
            return false;
        } else {
            addTimeObs.setError(null);
            return true;
        }
    }
}