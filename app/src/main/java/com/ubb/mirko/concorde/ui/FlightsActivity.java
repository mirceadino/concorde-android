package com.ubb.mirko.concorde.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.controller.FlightController;
import com.ubb.mirko.concorde.model.Flight;

public class FlightsActivity extends AppCompatActivity {

    private RecyclerView recyclerView_flights_;
    private RecyclerView.Adapter recyclerView_flights_adapter;
    private RecyclerView.LayoutManager recyclerView_flights_layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup displaying flights.
        recyclerView_flights_ = (RecyclerView) findViewById(R.id.recyclerView_flights);
        // recyclerView_flights_.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView_flights_layoutManager = new LinearLayoutManager(this);
        recyclerView_flights_.setLayoutManager(recyclerView_flights_layoutManager);

        // Initialize dataset.
        FlightController flightController = FlightController.getInstance();

        // specify an adapter
        recyclerView_flights_adapter = new FlightsAdapter(flightController.getAllFlights(), this);
        recyclerView_flights_.setAdapter(recyclerView_flights_adapter);
    }
}
