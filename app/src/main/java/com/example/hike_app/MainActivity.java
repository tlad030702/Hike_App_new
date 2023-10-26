package com.example.hike_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hike_app.models.HikeModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    CustomAdapter customAdapter;
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ArrayList<String> hike_id, hike_name, hike_location;
    DatabaseHelper db = new DatabaseHelper(MainActivity.this);
    private Context contextMain = this;
    ImageView emptyData_image, search_ic;
    TextView emptyData;
    private SearchView searchView;
    ArrayList<HikeModel> hikeModels = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        //find id
        recyclerView = findViewById(R.id.listHike);
        add_button = findViewById(R.id.add_btn);
        emptyData_image = findViewById(R.id.imageView);
        emptyData = findViewById(R.id.emptyData);

        //orther
//        hike_id = new ArrayList<>();
//        hike_name = new ArrayList<>();
//        hike_location = new ArrayList<>();


//        customAdapter = new CustomAdapter(MainActivity.this, hike_id, hike_name, hike_location);
        customAdapter = new CustomAdapter(MainActivity.this, hikeModels);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        displayData();
        customAdapter.notifyDataSetChanged();

        //btn
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
    void displayData() {
//        hike_id.clear();
//        hike_name.clear();
//        hike_location.clear();

        Cursor cursor = db.dataHike();
//        if (cursor.getCount() == 0){
//            emptyData_image.setVisibility(View.VISIBLE);
//            emptyData.setVisibility(View.VISIBLE);
//            Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
//        } else {
//            while (cursor.moveToNext()){
//                hike_id.add(cursor.getString(0));
//                hike_name.add(cursor.getString(1));
//                hike_location.add(cursor.getString(2));
//                emptyData_image.setVisibility(View.GONE);
//                emptyData.setVisibility(View.GONE);
//            }
//        }

        if (cursor.getCount() == 0) {
            emptyData_image.setVisibility(View.VISIBLE);
            emptyData.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                hikeModels.add(new HikeModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        search_ic = searchView.findViewById(androidx.appcompat.R.id.search_button);
        search_ic.setColorFilter(Color.WHITE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDeleteAllHike();
        }
//        else if (item.getItemId() == R.id.search) {
//
//        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDeleteAllHike(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All Hike?");
        builder.setMessage("Do you want to delete all data of hike ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteAllData();
                Intent intent = new Intent(contextMain, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(contextMain, "All data deleted", Toast.LENGTH_SHORT).show();
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