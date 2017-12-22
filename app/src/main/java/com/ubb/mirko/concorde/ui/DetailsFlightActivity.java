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
    private int flightId;
    private Flight currentFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get details texts and buttons from layout.
        detailsText_source = (EditText) findViewById(R.id.detailsFlight_source);
        detailsText_destination = (EditText) findViewById(R.id.detailsFlight_destination);
        detailsText_price = (EditText) findViewById(R.id.detailsFlight_price);
        graphView_graph = (GraphView) findViewById(R.id.detailsFlight_graph);
        button_cancel = (Button) findViewById(R.id.detailsFlight_button_cancel);

        // Get flight from intent.
        currentFlight = (Flight) getIntent().getExtras().getSerializable("currentFlight");
        System.out.println(currentFlight.toString());

        // Fill the layout with the information from intent.
        flightId = currentFlight.getId();
        detailsText_source.setText(currentFlight.getSource());
        detailsText_destination.setText(currentFlight.getDestination());
        detailsText_price.setText("" + currentFlight.getPrice());
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
        for (int i = 0; i < currentFlight.getAllPrices().size(); ++i) {
            dataPointList.add(new DataPoint(2 * i + 0, currentFlight.getAllPrices().get(i)));
            dataPointList.add(new DataPoint(2 * i + 1, currentFlight.getAllPrices().get(i)));
        }
        System.out.println(dataPointList);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPointList.toArray(new DataPoint[0]));
        graphView_graph.addSeries(series);
    }
}
