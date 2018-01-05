package com.ubb.mirko.concorde.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.controller.FlightController;
import com.ubb.mirko.concorde.controller.UserController;

public class FlightsActivity extends AppCompatActivity {

    private RecyclerView recyclerView_flights;
    private RecyclerView.Adapter recyclerView_flights_adapter;
    private RecyclerView.LayoutManager recyclerView_flights_layoutManager;
    private UserController userController = UserController.getInstance();
    private FlightController flightController = FlightController.getInstance();

    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup displaying flights.
        recyclerView_flights = findViewById(R.id.recyclerView_flights);
        // recyclerView_flights.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView_flights_layoutManager = new LinearLayoutManager(this);
        recyclerView_flights.setLayoutManager(recyclerView_flights_layoutManager);

        // specify an adapter
        recyclerView_flights_adapter = new FlightsAdapter(flightController.getAllFlights(), this);
        recyclerView_flights.setAdapter(recyclerView_flights_adapter);
    }
}
