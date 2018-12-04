package com.r3tr0.ambulanceapp;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.r3tr0.ambulanceapp.model.events.OnItemClickListener;
import com.r3tr0.ambulanceapp.model.firebase.FirebaseManager;
import com.r3tr0.ambulanceapp.model.models.Emergency;
import com.r3tr0.ambulanceapp.view.DetailsActivity;
import com.r3tr0.ambulanceapp.view.adapters.EmergenciesRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseManager manager;
    LiveData<DataSnapshot> data;

    RecyclerView recyclerView;
    EmergenciesRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        manager = new FirebaseManager(this);
        data = manager.getSnapshot();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initRecyclerView();
    }

    void initRecyclerView() {
        recyclerView = findViewById(R.id.emergenciesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));

        adapter = new EmergenciesRecyclerAdapter(this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("emergency", adapter.getEmergencies().get(position));

                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);


        data.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                Log.e("test", "RECEIVED");
                DataSnapshot emergenciesSnapshot = dataSnapshot.child("waiting Emergencies");
                List<Emergency> emergencies = new ArrayList<>();

                if (emergenciesSnapshot != null) {

                    for (DataSnapshot emergencySnapshot : emergenciesSnapshot.getChildren()) {
                        emergencies.add(new Emergency(
                                emergencySnapshot.getKey(),
                                emergencySnapshot.child("Emergency Type").getValue(String.class),
                                emergencySnapshot.child("Patients Number").getValue(String.class),
                                emergencySnapshot.child("Accepted by?").getValue(String.class)));
                    }

                    adapter.setEmergencies(emergencies);

                } else {
                    Log.e("test", "SHOUT OUT");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
