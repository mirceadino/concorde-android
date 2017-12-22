package com.ubb.mirko.concorde.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.model.Flight;

public class DetailsFlightActivity extends AppCompatActivity {
    private EditText detailsText_source;
    private EditText detailsText_destination;
    private EditText detailsText_price;
    private GraphView graphView_graph;
    private Button button_cancel;
    private int flightId;

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
        Flight flight = (Flight) getIntent().getExtras().getSerializable("currentFlight");
        System.out.println(flight.toString());

        // Fill the layout with the information from intent.
        flightId = flight.getId();
        detailsText_source.setText(flight.getSource());
        detailsText_destination.setText(flight.getDestination());
        detailsText_price.setText("" + flight.getPrice());
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cancelIntent = new Intent(DetailsFlightActivity.this, FlightsActivity.class);
                startActivity(cancelIntent);
            }
        });

        buildGraph();
    }

    protected Flight getAddedFlight() {
        String source = String.valueOf(detailsText_source.getText());
        String destination = String.valueOf(detailsText_destination.getText());
        int price = Integer.parseInt(String.valueOf(detailsText_price.getText()));
        return new Flight(flightId, source, destination, price);
    }

    protected void buildGraph() {
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 2),
                new DataPoint(1, 4),
                new DataPoint(2, 4),
                new DataPoint(3, 3),
                new DataPoint(4, 1),
                new DataPoint(5, 6),
        });
        graphView_graph.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX() * 255 / 4, (int) Math.abs(data.getY() * 255 / 6), 100);
            }
        });
    }
}
