package com.ubb.mirko.concorde.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.controller.FlightController;
import com.ubb.mirko.concorde.model.Flight;

public class ManageFlightsActivity extends AppCompatActivity {

    private RecyclerView recyclerView_flights_;
    private RecyclerView.Adapter recyclerView_flights_adapter;
    private RecyclerView.LayoutManager recyclerView_flights_layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_flights);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This button adds flights.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ManageFlightsActivity.this, AddFlightActivity.class);
                startActivity(myIntent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup displaying flights.
        recyclerView_flights_ = (RecyclerView) findViewById(R.id.recyclerView_manage_flights);
        // recyclerView_flights_.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView_flights_layoutManager = new LinearLayoutManager(this);
        recyclerView_flights_.setLayoutManager(recyclerView_flights_layoutManager);

        // Initialize dataset.
        FlightController flightController = FlightController.getInstance();

        // Get modified/added/removed flight if that's the case and modify/add/remove the flight.
        if (getIntent().hasExtra("addedFlight")) {
            Flight addedFlight = (Flight) getIntent().getExtras().getSerializable("addedFlight");
            System.out.println("Added flight: " + addedFlight);
            flightController.addFlight(addedFlight);

        } else if (getIntent().hasExtra("deletedFlight")) {
            Flight deletedFlight = (Flight) getIntent().getExtras().getSerializable("deletedFlight");
            System.out.println(deletedFlight);
            flightController.removeFlight(deletedFlight);
        }

        // specify an adapter
        recyclerView_flights_adapter = new ManageFlightsAdapter(flightController.getAllFlights(), this);
        recyclerView_flights_.setAdapter(recyclerView_flights_adapter);
    }
}
