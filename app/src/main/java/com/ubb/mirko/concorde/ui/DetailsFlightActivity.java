package com.ubb.mirko.concorde.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.model.Flight;

import java.util.ArrayList;
import java.util.List;

public class DetailsFlightActivity extends AppCompatActivity {
    private EditText detailsText_source;
    private EditText detailsText_destination;
    private EditText detailsText_price;
    private GraphView graphView_graph;
    private Button button_cancel;
    private Flight flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_flight);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get details texts and buttons from layout.
        detailsText_source = findViewById(R.id.detailsFlight_source);
        detailsText_destination = findViewById(R.id.detailsFlight_destination);
        detailsText_price = findViewById(R.id.detailsFlight_price);
        graphView_graph = findViewById(R.id.detailsFlight_graph);
        button_cancel = findViewById(R.id.detailsFlight_button_cancel);

        // Get flight from intent.
        flight = (Flight) getIntent().getExtras().getSerializable("flight");

        // Fill the layout with the information from intent.
        detailsText_source.setText(flight.getSource());
        detailsText_destination.setText(flight.getDestination());
        detailsText_price.setText("" + flight.getLastPrice());
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(DetailsFlightActivity.this, FlightsActivity.class);
                startActivity(cancelIntent);
            }
        });

        buildGraph();
    }

    protected void buildGraph() {
        List<DataPoint> dataPointList = new ArrayList<>();
        dataPointList.add(new DataPoint(0, 0));
        for (int i = 0; i < flight.getPrice().size(); ++i) {
            dataPointList.add(new DataPoint(i + 1, flight.getPrice().get(i)));
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointList.toArray(new DataPoint[0]));
        graphView_graph.addSeries(series);
    }
}
