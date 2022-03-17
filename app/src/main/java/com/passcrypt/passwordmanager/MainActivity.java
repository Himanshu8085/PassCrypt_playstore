package com.passcrypt.passwordmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String col = "#0336ff";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Force lignt mode
        AppCompatDelegate.setDefaultNightMode(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Actionbar colour
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(col));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Accounts");

        //Statusbar colour
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor(col));

        //Searchbar watermark
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search");
        //add pass hash
        DBHandler dbHandler = new DBHandler(MainActivity.this);
        dbHandler.puthash();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        // Create list of tiles
        DBHandler dbHandler = new DBHandler(MainActivity.this);
        ArrayList<Tileobj> arrayList = dbHandler.readallval();
        //Set the tiles using adapter
        ListView ourlist = findViewById(R.id.itemListView);
        TilesAdapter tilesAdapter = new TilesAdapter(this,arrayList);
        ourlist.setAdapter(tilesAdapter);

        //Search Changing List
        final ArrayList<Tileobj>[] newlist = new ArrayList[]{null};
        //Search Changing List

        //Click item on list then transfer info to editactivity
        ourlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                //This "parcable" is for passing objects between activities
                if (newlist[0] != null){
                    //Search Changing List
                    i.putExtra("Tileobj", (Parcelable) newlist[0].get(position));
                }
                else {
                    i.putExtra("Tileobj", (Parcelable) arrayList.get(position));
                }
                startActivity(i);
            }
        });
        //Add new entry
        FloatingActionButton fabadd = findViewById(R.id.floatingActionButton3);
        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Addnew.class);
                startActivity(i);
            }
        });

        //Searching.....
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                newlist[0] = new ArrayList<>();
                for (int i=0;i<arrayList.size();i++){
                    if (arrayList.get(i).getText1().contains(newText)){
                        newlist[0].add(arrayList.get(i));
                    }
                }
                TilesAdapter newtilesAdapter = new TilesAdapter(MainActivity.this, newlist[0]);
                ourlist.setAdapter(newtilesAdapter);
                return false;
            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedpreferences = getSharedPreferences("Temp_pref", Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();
    }
}