package com.example.hike_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailObs extends AppCompatActivity {
    private Toolbar toolbar;
    DatabaseHelper db = new DatabaseHelper(this);
    TextView obsName, obsAddedDate, obsCmt;
    Button edit, delete;
    String obsId, hikeID, name, added, cmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_obs);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);

        obsCmt = findViewById(R.id.obs_cmt_detail_txt);
        obsName = findViewById(R.id.obs_name_detail_txt);
        obsAddedDate = findViewById(R.id.obs_detail_addedDate_txt);
        edit = findViewById(R.id.edit_btn);
        delete = findViewById(R.id.delete_btn);

        obsId = getIntent().getStringExtra("obs_id");
        hikeID = getIntent().getStringExtra("hike_id");
        System.out.println(hikeID);
        System.out.println(obsId);

        if (getIntent().hasExtra("obs_id")) {
            dataObsById(Integer.parseInt(obsId));
            obsName.setText(name);
            obsAddedDate.setText(added);
            obsCmt.setText(cmt);
        } else {
            Toast.makeText(this, "Invalid Hike", Toast.LENGTH_SHORT).show();
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailObs.this, EditObsActivity.class);
                intent.putExtra("hike_id", String.valueOf(hikeID));
                intent.putExtra("obs_id", String.valueOf(obsId));
                intent.putExtra("obs_name", String.valueOf(obsName.getText()));
//                intent.putExtra("obs_addedDate", String.valueOf(obsAddedDate.getText()));
                intent.putExtra("obs_cmt", String.valueOf(obsCmt.getText()));
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteObs();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DetailObs.this, Observations.class);
                intent.putExtra("hike_id", hikeID);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void dataObsById(int id){
        Cursor cursor = db.dataObsByIdObs(id);
        if (cursor.getCount() != 0 && cursor.moveToFirst()){
            obsId = cursor.getString(0);
            name = cursor.getString(1);
            added = cursor.getString(2);
            cmt = cursor.getString(3);
        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDeleteObs(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Do you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteObsData(obsId);
                Intent intent = new Intent(DetailObs.this, Observations.class);
                intent.putExtra("hike_id", String.valueOf(hikeID));
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