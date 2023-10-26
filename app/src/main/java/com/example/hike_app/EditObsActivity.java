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

        updateObs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(EditObsActivity.this);
                name = obsName.getText().toString().trim();
                added = obsAddedDate.getText().toString().trim();
                cmt = obsCmt.getText().toString().trim();
                db.updateObsData(obsId, name, added, cmt);

                Intent intent = new Intent(EditObsActivity.this, DetailObs.class);
                intent.putExtra("obs_id", obsId);
                startActivity(intent);
            }
        });

    }
    void getIntentData(){
        if(getIntent().hasExtra("obs_id") &&
                getIntent().hasExtra("obs_name") &&
                getIntent().hasExtra("obs_addedDate") &&
                getIntent().hasExtra("obs_cmt")){
            obsId = getIntent().getStringExtra("obs_id");
            name = getIntent().getStringExtra("obs_name");
            added = getIntent().getStringExtra("obs_addedDate");
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
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}