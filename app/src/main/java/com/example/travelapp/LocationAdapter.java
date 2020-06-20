package com.example.travelapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;

//To Fix: When the views are created, their 'selected' value must be fetched out of the
//database, likewise with the alarm values for the 2nd activity.
//selected=true but the star isn't filled in (we want to put isChecked=true)
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private TouristLocation[] mDataset;
    private QueryMaker qhelper;
    private FragmentManager myFM;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {  // 1 (my custom holder)
        // each data item is just a string in this case

        public ImageView photo; //jumble of views in this ViewHolder
        public TextView category;
        public TextView restaurantName;
        public ToggleButton favoriteButton;
        public ImageButton alarmButton;

        public MyViewHolder(View itemXML) {
            super(itemXML);
            photo = (ImageView) itemXML.findViewById(R.id.photo);
            category = (TextView) itemXML.findViewById(R.id.category);
            restaurantName = (TextView) itemXML.findViewById(R.id.restaurantName);
            favoriteButton = (ToggleButton) itemXML.findViewById(R.id.favoriteButton);
            alarmButton = (ImageButton) itemXML.findViewById(R.id.alarmButton);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LocationAdapter(TouristLocation[] myDataset, QueryMaker q, FragmentManager f) {
        mDataset = myDataset;
        qhelper = q;
        myFM = f;
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
        String url = mDataset[position].getPhotoLinkStr(); //Restaurant item's photoLink
        if (url.trim().length() == 0)
        {
            System.out.println("Missing photo link on item");
            url = "https://codelabs.developers.google.com/codelabs/advanced-android-training-google-maps/img/3077e66f9f7a1a46.png";
        }
        Picasso.with(holder.photo.getContext()).load(url).placeholder(R.drawable.ic_baseline_place_24).into(holder.photo);
        holder.category.setText(mDataset[position].getCategory());
        holder.restaurantName.setText(mDataset[position].getName());  // 3 (let 'textView' = some string)

        //Fetch the value of star/selected out of the database!
        holder.favoriteButton.setChecked(qhelper.getSelectedStatus(position));

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

        //Set a 'favorite button listener'
        final Context context = holder.photo.getContext();
        final int pos = position;
        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gotta do this to make favorite button work!
               // boolean old_state = qhelper.getSelectedStatus(pos);
                qhelper.toggleSelected(pos);
               // boolean new_state = qhelper.getSelectedStatus(pos);
               /*
                     if(!old_state) {
                    Toast.makeText(context, "Favorite from " + pos, Toast.LENGTH_SHORT).show();
                    qhelper.printMyData();
                     }
                     else {
                    Toast.makeText(context, "Unfavorite from " + pos, Toast.LENGTH_SHORT).show();
                     }
               */
            }
        });

        //set alarm's listener here
        final FragmentManager fragmentManager = myFM;
        final int p = position;
        holder.alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Alarm", Toast.LENGTH_SHORT).show();
                DialogFragment newFragment = new LocationAdapter.TimePickerFragment();
                ((TimePickerFragment) newFragment).positionInList = p;
                ((TimePickerFragment) newFragment).qHelper = qhelper;
                newFragment.show(fragmentManager, "timePicker");
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        public int positionInList;
        public QueryMaker qHelper;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            // Use the positionInList private variable to update queryMaker data
            qHelper.updateSelectedTime(positionInList, hourOfDay, minute);
            qHelper.printMyData();
        }
    }
}