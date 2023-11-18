package com.example.hike_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

public class EditHike extends AppCompatActivity {
    private Toolbar toolbar;
    EditText edit_name, edit_location, edit_length, edit_date, edit_level, edit_description;
    Switch edit_switch;
    Button update_button;
    String hike_id, hikeName, hikeLocation, hikeDate, hikeLevel, hikeDescription, hikeLength, isParking;
    double hike_length;
    int is_parking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hike);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        edit_name = findViewById(R.id.hike_name_edit_txt);
        edit_location = findViewById(R.id.hike_location_edit_txt);
        edit_length = findViewById(R.id.hike_length_edit_txt);
        edit_date = findViewById(R.id.hike_date_edit_txt);
        edit_level = findViewById(R.id.hike_level_edit_txt);
        edit_description = findViewById(R.id.hike_description_edit_txt);
        edit_switch = findViewById(R.id.edit_hike_parking);
        update_button = findViewById(R.id.update_btn);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateNameHike() | !validateDate() | !validateLength() | !validateLocation() | !validateLevel()){
                    return;
                }
                DatabaseHelper db = new DatabaseHelper(EditHike.this);
                hikeName = edit_name.getText().toString().trim();
                hikeLocation = edit_location.getText().toString().trim();
                hikeLength = edit_length.getText().toString().trim();
                hikeDate = edit_date.getText().toString().trim();
                is_parking = edit_switch.isChecked() ? 1 : 0;
                hikeLevel = edit_level.getText().toString().trim();
                hikeDescription = edit_description.getText().toString().trim();

                db.updateHikeData(hike_id, hikeName, hikeLocation, Double.parseDouble(hikeLength), hikeDate, is_parking, hikeLevel, hikeDescription);
            }
        });
        edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogPickDate();
            }
        });
        getIntentData();
    }
    void getIntentData(){
        if(getIntent().hasExtra("hike_id") && getIntent().hasExtra("hike_name") && getIntent().hasExtra("hike_location") && getIntent().hasExtra("hike_length") && getIntent().hasExtra("hike_date") && getIntent().hasExtra("hike_parking") && getIntent().hasExtra("hike_date") && getIntent().hasExtra("hike_description")){
            hike_id = getIntent().getStringExtra("hike_id");
            hikeName = getIntent().getStringExtra("hike_name");
            hikeLocation = getIntent().getStringExtra("hike_location");
            hikeLength = getIntent().getStringExtra("hike_length");
            hikeDate = getIntent().getStringExtra("hike_date");
            isParking = getIntent().getStringExtra("hike_parking");
            hikeLevel = getIntent().getStringExtra("hike_level");
            hikeDescription = getIntent().getStringExtra("hike_description");

            is_parking = Integer.parseInt(isParking);
//            hike_length = Double.parseDouble(hikeLength);

            edit_name.setText(hikeName);
            edit_location.setText(hikeLocation);
            edit_length.setText(hikeLength);
            edit_date.setText(hikeDate);
            edit_level.setText(hikeLevel);
            edit_switch.setChecked(is_parking == 0 ? false : true);
            edit_description.setText(hikeDescription);
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DetailHike.class);
                intent.putExtra("hike_id", hike_id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void openDialogPickDate(){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                edit_date.setText(dayOfMonth +"/"+ (month + 1) +"/"+(year));
            }
        }, currentYear, currentMonth, currentDay);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();
    }
    private boolean validateNameHike() {
        String nameInput = edit_name.getText().toString().trim();
        if(nameInput.isEmpty()){
            edit_name.setError("Name can not be empty");
            return false;
        } else {
            edit_name.setError(null);
            return true;
        }
    }
    private boolean validateLocation() {
        String locationInput = edit_location.getText().toString().trim();
        if (locationInput.isEmpty()) {
            edit_location.setError("Location can not be empty");
            return false;
        } else {
            edit_location.setError(null);
            return true;
        }
    }
    private boolean validateLength() {
        String lengthInput = edit_length.getText().toString().trim();
        if (lengthInput.isEmpty()) {
            edit_length.setError("Length can not be empty");
            return false;
        } else {
            edit_length.setError(null);
            return true;
        }
    }
    private boolean validateDate(){
        String dateInput = edit_date.getText().toString().trim();
        if (dateInput.isEmpty()) {
            edit_date.setError("Date can not be empty");
            return false;
        } else {
            edit_date.setError(null);
            return true;
        }
    }
    private boolean validateLevel(){
        String levelInput = edit_level.getText().toString().trim();
        if (levelInput.isEmpty()) {
            edit_level.setError("Level can not be empty");
            return false;
        } else {
            edit_level.setError(null);
            return true;
        }
    }
}