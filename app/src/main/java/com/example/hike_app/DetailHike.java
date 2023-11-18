package com.example.hike_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailHike extends AppCompatActivity {
    private Toolbar toolbar;
    DatabaseHelper db = new DatabaseHelper(DetailHike.this);
    String hikeId, hikeName, hikeLocation, hikeDate, hikeLevel, hikeDescription;
    Double hikeLength;
    int isParking;
    TextView name, location, length, date, level, parking, description;
    Button edit, delete, obs;
    final Context contextDetail = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hike);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        name = findViewById(R.id.hike_name_detail_txt);
        location = findViewById(R.id.hike_location_detail_txt);
        length = findViewById(R.id.hike_length_detail_txt);
        date = findViewById(R.id.hike_date_detail_txt);
        level = findViewById(R.id.hike_level_detail_txt);
        parking = findViewById(R.id.hike_parking_detail_txt);
        description = findViewById(R.id.hike_description_detail_txt);
        edit = findViewById(R.id.edit_btn);
        delete = findViewById(R.id.delete_btn);
        obs = findViewById(R.id.obs_btn);


        hikeId = getIntent().getStringExtra("hike_id");
        if (getIntent().hasExtra("hike_id")) {
            dataHikeById(Integer.parseInt(hikeId));
            name.setText(hikeName);
            location.setText(hikeLocation);
            date.setText(hikeDate);
            parking.setText(isParking == 1 ? "Have Parking" : "No Parking");
            length.setText(hikeLength.toString());
            level.setText(hikeLevel);
            description.setText(hikeDescription);
        } else {
            Toast.makeText(this, "Invalid Hike", Toast.LENGTH_SHORT).show();
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailHike.this, EditHike.class);
                intent.putExtra("hike_id", String.valueOf(hikeId));
                intent.putExtra("hike_name", String.valueOf(name.getText()));
                intent.putExtra("hike_location", String.valueOf(location.getText()));
                intent.putExtra("hike_length", String.valueOf(length.getText()));
                intent.putExtra("hike_date", String.valueOf(date.getText()));
                intent.putExtra("hike_parking", String.valueOf(isParking));
                intent.putExtra("hike_level", String.valueOf(level.getText()));
                intent.putExtra("hike_description", String.valueOf(description.getText()));
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteHike();
            }
        });

        obs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailHike.this, Observations.class);
                intent.putExtra("hike_id", String.valueOf(hikeId));
                startActivity(intent);
            }
        });

    }
    void dataHikeById(int id){
        Cursor cursor = db.dataHikeById(id);
        if (cursor.getCount() != 0 && cursor.moveToFirst()){
            hikeName = cursor.getString(1);
            hikeLocation = cursor.getString(2);
            hikeDate = cursor.getString(3);
            isParking = cursor.getInt(4);
            hikeLength = cursor.getDouble(5);
            hikeLevel = cursor.getString(6);
            hikeDescription = cursor.getString(7);
        } else {
            Toast.makeText(DetailHike.this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDeleteHike(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + hikeName + " ?");
        builder.setMessage("Do you want to delete " + hikeName + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteHikeData(hikeId);
                Intent intent = new Intent(contextDetail, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}