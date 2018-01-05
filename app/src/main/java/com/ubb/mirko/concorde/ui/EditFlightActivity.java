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
    private Flight flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flight);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText_source = findViewById(R.id.editFlight_source);
        editText_destination = findViewById(R.id.editFlight_destination);
        numberPicker_pricePicker = findViewById(R.id.editFlight_pricePicker);
        button_edit = findViewById(R.id.editFlight_button_edit);
        button_delete = findViewById(R.id.editFlight_button_delete);

        // Get flight from intent.
        flight = (Flight) getIntent().getExtras().getSerializable("flight");

        // Fill the layout with the information from intent.
        editText_source.setText(flight.getSource());
        editText_destination.setText(flight.getDestination());
        numberPicker_pricePicker.setMinValue(0);
        numberPicker_pricePicker.setMaxValue(300);
        numberPicker_pricePicker.setValue(flight.getLastPrice());
        numberPicker_pricePicker.setWrapSelectorWheel(true);
        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flight flight = getEditedFlight();
                modifyFlight(flight);
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
                                Flight flight = getEditedFlight();
                                deleteFlight(flight);
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
        flight.setSource(String.valueOf(editText_source.getText()));
        flight.setDestination(String.valueOf(editText_destination.getText()));
        flight.setPrice(numberPicker_pricePicker.getValue());
        return flight;
    }

    protected void modifyFlight(Flight flight) {
        Intent modifyIntent = new Intent(this, ManageFlightsActivity.class);
        modifyIntent.putExtra("addedFlight", flight);
        startActivity(modifyIntent);
    }

    protected void deleteFlight(Flight flight) {
        Intent deleteIntent = new Intent(this, ManageFlightsActivity.class);
        deleteIntent.putExtra("deletedFlight", flight);
        startActivity(deleteIntent);
    }
}
