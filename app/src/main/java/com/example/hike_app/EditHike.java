package com.example.hike_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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
}