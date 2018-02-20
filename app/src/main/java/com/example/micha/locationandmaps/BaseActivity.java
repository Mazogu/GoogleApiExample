package com.example.micha.locationandmaps;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by micha on 2/19/2018.
 */

public class BaseActivity extends AppCompatActivity {

    private ListView list;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    public static final String TAG = "Some Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String activityTitle = getTitle().toString();
        list = findViewById(R.id.activities);
        drawer = findViewById(R.id.drawerlayout);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = ((TextView) view).getText().toString();
                if (title.equals(getString(R.string.Main))) {
                    Log.d(TAG, "onItemClick: "+title);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

                else if (title.equals(getString(R.string.Geocoding))) {
                    Log.d(TAG, "onItemClick: "+title);
                    Intent intent = new Intent(getApplicationContext(), GeoActivity.class);
                    startActivity(intent);
                }

                else if (title.equals(getString(R.string.Places))) {
                    Log.d(TAG, "onItemClick: "+title);
                }
            }
        });

        addToDrawer();
        toggle = new ActionBarDrawerToggle(this,drawer, R.string.open,R.string.closed){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigate");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(activityTitle);
                invalidateOptionsMenu();
            }
        };
        drawer.addDrawerListener(toggle);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    private void addToDrawer(){
        String[] titles = getResources().getStringArray(R.array.activity_list);
        ArrayAdapter adapter = new ArrayAdapter < String > (this,android.R.layout.simple_expandable_list_item_1,titles);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
