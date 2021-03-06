package com.ubb.mirko.concorde.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ubb.mirko.concorde.R;
import com.ubb.mirko.concorde.model.Flight;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by mirko on 12/11/2017.
 */

class ManageFlightsAdapter extends RecyclerView.Adapter<ManageFlightsAdapter.ViewHolder> {
    private List<Flight> dataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Context context;
        public CardView cardView;
        public TextView textView_source;
        public TextView textView_destination;
        public TextView textView_price;
        public Button button_edit;

        public ViewHolder(View v, Context context) {
            super(v);
            cardView = v.findViewById(R.id.cardView_flights);
            textView_source = v.findViewById(R.id.cardView_flights_source);
            textView_destination = v.findViewById(R.id.cardView_flights_destination);
            textView_price = v.findViewById(R.id.cardView_flights_price);
            button_edit = v.findViewById(R.id.cardView_flights_button_editFlight);
            this.context = context;
        }

        public void setFlight(final Flight f) {
            textView_source.setText(f.getSource());
            textView_destination.setText("to " + f.getDestination());
            textView_price.setText("$" + Double.toString(f.getLastPrice()));
            button_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent myIntent = new Intent(context, EditFlightActivity.class);
                    myIntent.putExtra("flight", f);
                    ((Activity)context).startActivityForResult(myIntent, 0);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ManageFlightsAdapter(List<Flight> dataset, Context context) {
        this.dataset = dataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ManageFlightsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_manage_flight, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ...
        ViewHolder vh = new ViewHolder(v, context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.setFlight(dataset.get(position));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
