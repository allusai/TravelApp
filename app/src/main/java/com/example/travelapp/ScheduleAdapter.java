package com.example.travelapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {
    private TouristLocation[] mDataset;
    private QueryMaker qhelper;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {  // 1 (my custom holder)
        // each data item is just a string in this case

        public ImageView photo; //jumble of views in this ViewHolder
        public TextView category;
        public TextView restaurantName;
        public TextView scheduledTime;

        public MyViewHolder(View itemXML) {
            super(itemXML);
            photo = (ImageView) itemXML.findViewById(R.id.photo);
            category = (TextView) itemXML.findViewById(R.id.category);
            restaurantName = (TextView) itemXML.findViewById(R.id.restaurantName);
            scheduledTime = (TextView) itemXML.findViewById(R.id.scheduledTime);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ScheduleAdapter(TouristLocation[] myDataset, QueryMaker q) {
        mDataset = myDataset;
        qhelper = q;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ScheduleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_item, parent, false); //inflate XML file
        //...
        MyViewHolder vh = new MyViewHolder(v); //Use it to build a ViewHolder container with the constructor above
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) { // bind data to a holder
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String url = mDataset[position].getPhotoLinkStr(); //Restaurant item's photoLink
        if (url.trim().length() == 0)
        {
            System.out.println("Missing photo link on item");
            url = "https://codelabs.developers.google.com/codelabs/advanced-android-training-google-maps/img/3077e66f9f7a1a46.png";
        }
        Picasso.with(holder.photo.getContext()).load(url).placeholder(R.drawable.ic_baseline_place_24).into(holder.photo);
        holder.category.setText(mDataset[position].getCategory());
        holder.restaurantName.setText(mDataset[position].getName());  // 3 (let 'textView' = some string)

        //Set a linearLayout listener
        final String link = mDataset[position].getWebsiteLink();
        final String photoLink = mDataset[position].getPhotoLinkStr();
        final Context ctx = holder.photo.getContext();
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(photoLink); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ctx.startActivity(intent);
            }
        });
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ctx.startActivity(intent);
            }
        });
        holder.restaurantName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ctx.startActivity(intent);
            }
        });

        String example = "5:30";
        holder.scheduledTime.setText(example);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
