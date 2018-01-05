package com.ubb.mirko.concorde.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.controller.FlightController;
import com.ubb.mirko.concorde.controller.UserController;
import com.ubb.mirko.concorde.model.Flight;
import com.ubb.mirko.concorde.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ManageFlightsActivity extends AppCompatActivity implements Observer {

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
        setContentView(R.layout.activity_manage_flights);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This button adds flights.
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ManageFlightsActivity.this, AddFlightActivity.class);
                startActivity(myIntent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        flightController.subscribe(this);

        // Get modified/added/removed flight if that's the case and modify/add/remove the flight.
        if (getIntent().hasExtra("addedFlight")) {
            Flight addedFlight = (Flight) getIntent().getExtras().getSerializable("addedFlight");
            System.out.println("Added flight: " + addedFlight);
            flightController.addFlight(addedFlight);

        } else if (getIntent().hasExtra("deletedFlight")) {
            Flight deletedFlight = (Flight) getIntent().getExtras().getSerializable("deletedFlight");
            System.out.println("Deleted flight: " + deletedFlight);
            flightController.removeFlight(deletedFlight);
        }

        update(ObserverStatus.OK, flightController.getAllFlights());
    }

    @Override
    public void update(ObserverStatus status, Object object) {
        if (status == ObserverStatus.OK) {
            List<Flight> flights = (ArrayList<Flight>) object;
            // Setup displaying flights.
            recyclerView_flights = findViewById(R.id.recyclerView_manage_flights);
            // recyclerView_flights.setHasFixedSize(true);
            // use a linear layout manager
            recyclerView_flights_layoutManager = new LinearLayoutManager(this);
            recyclerView_flights.setLayoutManager(recyclerView_flights_layoutManager);

            // specify an adapter
            recyclerView_flights_adapter = new ManageFlightsAdapter(flights, this);
            recyclerView_flights.setAdapter(recyclerView_flights_adapter);

        } else if (status == ObserverStatus.FAIL) {
            String message = (String) object;
            showToast(message);
        }

    }
}
