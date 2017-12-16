package com.ubb.mirko.concorde;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ubb.mirko.concorde.model.Flight;

public class EditFlightActivity extends AppCompatActivity {
    private EditText editText_source;
    private EditText editText_destination;
    private EditText editText_price;
    private Button button_edit;
    private int flightId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get edit texts and buttons from layout.
        editText_source = (EditText) findViewById(R.id.editFlight_source);
        editText_destination = (EditText) findViewById(R.id.editFlight_destination);
        editText_price = (EditText) findViewById(R.id.editFlight_price);
        button_edit = (Button) findViewById(R.id.editFlight_button_edit);

        // Get flight from intent.
        Flight flight = (Flight) getIntent().getExtras().getSerializable("flight");
        System.out.println(flight.toString());

        // Fill the layout with the information from intent.
        flightId = flight.getId();
        editText_source.setText(flight.getSource());
        editText_destination.setText(flight.getDestination());
        editText_price.setText("" + flight.getPrice());
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the modified flight.
                Flight flight = getEditedFlight();

                // Intent to modify the flight.
                Intent modifyIntent = new Intent(EditFlightActivity.this, FlightsActivity.class);
                modifyIntent.putExtra("modifiedFlight", flight);
                startActivity(modifyIntent);

                // Intent to send an email.
                String address = "mirceadino97@gmail.com";
                String subject = "Flight was modified";
                String body = "Flight #" + flight.getId() + " is now from " +
                        flight.getSource() + " to " + flight.getDestination() + " for $" +
                        flight.getPrice() + ".";
                Intent sendMailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Uri.encode(address)));
                sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                sendMailIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(sendMailIntent);
            }
        });
    }

    protected Flight getEditedFlight() {
        String source = String.valueOf(editText_source.getText());
        String destination = String.valueOf(editText_destination.getText());
        int price = Integer.parseInt(String.valueOf(editText_price.getText()));
        return new Flight(flightId, source, destination, price);
    }
}
