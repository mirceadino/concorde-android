package com.ubb.mirko.concorde.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.model.Flight;

import java.util.List;

public class EditFlightActivity extends AppCompatActivity {
    private EditText editText_source;
    private EditText editText_destination;
    private NumberPicker numberPicker_pricePicker;
    private Button button_edit;
    private Button button_delete;
    private int flightId;
    private Flight initialFlight;

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
        numberPicker_pricePicker = (NumberPicker) findViewById(R.id.editFlight_pricePicker);
        button_edit = (Button) findViewById(R.id.editFlight_button_edit);
        button_delete = (Button) findViewById(R.id.editFlight_button_delete);

        // Get flight from intent.
        initialFlight = (Flight) getIntent().getExtras().getSerializable("currentFlight");
        System.out.println(initialFlight.toString());

        // Fill the layout with the information from intent.
        flightId = initialFlight.getId();
        editText_source.setText(initialFlight.getSource());
        editText_destination.setText(initialFlight.getDestination());
        numberPicker_pricePicker.setMinValue(0);
        numberPicker_pricePicker.setMaxValue(300);
        numberPicker_pricePicker.setValue(initialFlight.getLastPrice());
        numberPicker_pricePicker.setWrapSelectorWheel(true);
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the modified flight.
                Flight flight = getEditedFlight();

                // Intent to modify the flight.
                Intent modifyIntent = new Intent(EditFlightActivity.this, ManageFlightsActivity.class);
                modifyIntent.putExtra("addedFlight", flight);
                startActivity(modifyIntent);

                // Intent to send an email.
                String address = "mirceadino97@gmail.com";
                String subject = "Flight was modified";
                String body = "Flight #" + flight.getId() + " is now from " +
                        flight.getSource() + " to " + flight.getDestination() + " for $" +
                        flight.getLastPrice() + ".";
                Intent sendMailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Uri.encode(address)));
                sendMailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                sendMailIntent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(sendMailIntent);
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Get the modified flight.
                                Flight flight = getEditedFlight();

                                // Intent to modify the flight.
                                Intent deleteIntent = new Intent(EditFlightActivity.this, ManageFlightsActivity.class);
                                deleteIntent.putExtra("deletedFlight", flight);
                                startActivity(deleteIntent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    protected Flight getEditedFlight() {
        initialFlight.setSource(String.valueOf(editText_source.getText()));
        initialFlight.setDestination(String.valueOf(editText_destination.getText()));
        initialFlight.setPrice(numberPicker_pricePicker.getValue());
        return initialFlight;
    }
}
