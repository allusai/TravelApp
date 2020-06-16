package com.example.travelapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private TouristLocation[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {  // 1 (my custom holder)
        // each data item is just a string in this case

        public TextView category; //jumble of views in this ViewHolder
        public TextView restaurantName;
        public ImageButton alarmButton;

        public MyViewHolder(View itemXML) {
            super(itemXML);
            category = (TextView) itemXML.findViewById(R.id.category);
            restaurantName = (TextView) itemXML.findViewById(R.id.restaurantName);
            alarmButton = (ImageButton) itemXML.findViewById(R.id.alarmButton);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LocationAdapter(TouristLocation[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_location, parent, false); //inflate XML file
        //...
        MyViewHolder vh = new MyViewHolder(v); //Use it to build a ViewHolder container with the constructor above
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { // bind data to a holder
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.category.setText(mDataset[position].getCategory());
        holder.restaurantName.setText(mDataset[position].getName());  // 3 (let 'textView' = some string)
        //set alarm's picker default value for view here
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}