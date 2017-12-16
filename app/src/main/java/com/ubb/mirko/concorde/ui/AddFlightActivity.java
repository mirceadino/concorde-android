package com.ubb.mirko.concorde.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.model.Flight;

public class AddFlightActivity extends AppCompatActivity {
    private EditText addText_source;
    private EditText addText_destination;
    private EditText addText_price;
    private Button button_add;
    private Button button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get add texts and buttons from layout.
        addText_source = (EditText) findViewById(R.id.addFlight_source);
        addText_destination = (EditText) findViewById(R.id.addFlight_destination);
        addText_price = (EditText) findViewById(R.id.addFlight_price);
        button_add = (Button) findViewById(R.id.addFlight_button_add);
        button_cancel = (Button) findViewById(R.id.addFlight_button_cancel);

        // Fill the layout with the information from intent.
        // addText_source.setText(flight.getSource());
        // addText_destination.setText(flight.getDestination());
        // addText_price.setText("" + flight.getPrice());
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the modified flight.
                Flight flight = getAddedFlight();

                // Intent to modify the flight.
                Intent modifyIntent = new Intent(AddFlightActivity.this, FlightsActivity.class);
                modifyIntent.putExtra("addedFlight", flight);
                startActivity(modifyIntent);
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(AddFlightActivity.this, FlightsActivity.class);
                startActivity(cancelIntent);
            }
        });
    }

    protected Flight getAddedFlight() {
        String source = String.valueOf(addText_source.getText());
        String destination = String.valueOf(addText_destination.getText());
        int price = Integer.parseInt(String.valueOf(addText_price.getText()));
        return new Flight(-1, source, destination, price);
    }
}
