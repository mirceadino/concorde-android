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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addText_source = findViewById(R.id.addFlight_source);
        addText_destination = findViewById(R.id.addFlight_destination);
        addText_price = findViewById(R.id.addFlight_price);
        button_add = findViewById(R.id.addFlight_button_add);
        button_cancel = findViewById(R.id.addFlight_button_cancel);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flight flight = getAddedFlight();
                addNewFlight(flight);
                sendNewFlightEmail(flight);
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected Flight getAddedFlight() {
        String source = String.valueOf(addText_source.getText());
        String destination = String.valueOf(addText_destination.getText());
        int price = Integer.parseInt(String.valueOf(addText_price.getText()));
        return new Flight(source, destination, price);
    }

    protected void addNewFlight(Flight flight) {
        Intent addNewIntent = new Intent(this, ManageFlightsActivity.class);
        addNewIntent.putExtra("addedFlight", flight);
        setResult(123, addNewIntent);
        finish();
    }

    protected void sendNewFlightEmail(Flight flight) {
        String address = "mirceadino97@gmail.com";
        String subject = "Adding new flight on Concorde";
        String body = "You will fly from" +
                flight.getSource() + " to " + flight.getDestination() + " for $" +
                flight.getLastPrice() + ".";
        Intent sendMailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Uri.encode(address)));
        sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendMailIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(sendMailIntent);
    }
}
