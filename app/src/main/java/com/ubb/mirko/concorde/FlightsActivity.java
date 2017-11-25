package com.ubb.mirko.concorde;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ubb.mirko.concorde.model.Flight;
import com.ubb.mirko.concorde.model.FlightInMemoryDataset;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You can't add a new flight yet", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup displaying flights.
        recyclerView_flights_ = (RecyclerView) findViewById(R.id.recyclerView_flights);
        // recyclerView_flights_.setHasFixedSize(true);
        // use a linear layout manager
        recyclerView_flights_layoutManager = new LinearLayoutManager(this);
        recyclerView_flights_.setLayoutManager(recyclerView_flights_layoutManager);

        // Initialize dataset.
        FlightInMemoryDataset dataset = FlightInMemoryDataset.getInstance();

        // Get modified flight if that's the case and modify the flight.
        if (getIntent().hasExtra("modifiedFlight")) {
            Flight modifiedFlight = (Flight) getIntent().getExtras().getSerializable("modifiedFlight");
            System.out.println(modifiedFlight);
            dataset.addFlight(modifiedFlight);
        }

        // specify an adapter
        recyclerView_flights_adapter = new FlightsAdapter(dataset.getDataset(), this);
        recyclerView_flights_.setAdapter(recyclerView_flights_adapter);
    }
}
