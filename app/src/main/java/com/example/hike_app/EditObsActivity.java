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
import android.widget.Toast;

import java.util.Calendar;

public class EditObsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    EditText obsName, obsAddedDate, obsCmt;
    Button updateObs;
    String obsId, hikeID, name, added, cmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_obs);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        obsName = findViewById(R.id.obs_name_edit_txt);
        obsAddedDate = findViewById(R.id.obs_added_edit_txt);
        obsCmt = findViewById(R.id.obs_cmt_edit_txt);
        updateObs = findViewById(R.id.update_obs_btn);

        getIntentData();
        getAddedDate();

        updateObs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateObservation() | !validateAddedDate()){
                    return;
                }
                DatabaseHelper db = new DatabaseHelper(EditObsActivity.this);
                name = obsName.getText().toString().trim();
                added = obsAddedDate.getText().toString().trim();
                cmt = obsCmt.getText().toString().trim();
                db.updateObsData(obsId, name, added, cmt);

                Intent intent = new Intent(EditObsActivity.this, DetailObs.class);
                intent.putExtra("obs_id", obsId);
                intent.putExtra("hike_id", hikeID);
                startActivity(intent);
            }
        });

    }
    void getIntentData(){
        if(getIntent().hasExtra("hike_id") && getIntent().hasExtra("obs_id") &&
                getIntent().hasExtra("obs_name") &&
                getIntent().hasExtra("obs_cmt")){
            hikeID = getIntent().getStringExtra("hike_id");
            obsId = getIntent().getStringExtra("obs_id");
            name = getIntent().getStringExtra("obs_name");
//            added = getIntent().getStringExtra("obs_addedDate");
            cmt = getIntent().getStringExtra("obs_cmt");

            obsName.setText(name);
            obsAddedDate.setText(added);
            obsCmt.setText(cmt);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, DetailObs.class);
                intent.putExtra("obs_id", obsId);
                intent.putExtra("hike_id", hikeID);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getAddedDate(){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        String addedDate;

        if(currentHour < 10){
            addedDate = currentDay + "/" + (currentMonth+1) + "/" + currentYear + " " + "0" + currentHour + ":" + currentMinute;
            obsAddedDate.setText(addedDate);
        } else if (currentHour < 10 && currentMinute < 10) {
            addedDate = currentDay + "/" + (currentMonth+1) + "/" + currentYear + " " + "0" + currentHour + ":0" + currentMinute;
            obsAddedDate.setText(addedDate);
        } else if (currentMinute < 10) {
            addedDate = currentDay + "/" + (currentMonth+1) + "/" + currentYear + " " + currentHour + ":0" + currentMinute;
            obsAddedDate.setText(addedDate);
        } else {
            addedDate = currentDay + "/" + (currentMonth+1) + "/" + currentYear + " " + currentHour + ":" + currentMinute;
            obsAddedDate.setText(addedDate);
        }
    }
    private boolean validateObservation() {
        String nameInput = obsName.getText().toString().trim();
        if(nameInput.isEmpty()){
            obsName.setError("Observation's content can not be empty");
            return false;
        } else {
            obsName.setError(null);
            return true;
        }
    }
    private boolean validateAddedDate() {
        String locationInput = obsAddedDate.getText().toString().trim();
        if (locationInput.isEmpty()) {
            obsAddedDate.setError("Location can not be empty");
            return false;
        } else {
            obsAddedDate.setError(null);
            return true;
        }
    }
}