package com.example.travelapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {
    private TouristLocation[] mDataset;
    private QueryMaker qhelper;
    private static final String TAG = "lyft:TravelApp";
    private static final String LYFT_PACKAGE = "me.lyft.android";
    private static final String UBERTAG = "uber:TravelApp";
    private static final String UBER_PACKAGE = "me.uber.android";
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {  // 1 (my custom holder)
        // each data item is just a string in this case

        public ImageView photo; //jumble of views in this ViewHolder
        public TextView category;
        public TextView restaurantName;
        public TextView scheduledTime;
        public ImageButton rideshareButton;

        public MyViewHolder(View itemXML) {
            super(itemXML);
            photo = (ImageView) itemXML.findViewById(R.id.photo);
            category = (TextView) itemXML.findViewById(R.id.category);
            restaurantName = (TextView) itemXML.findViewById(R.id.restaurantName);
            scheduledTime = (TextView) itemXML.findViewById(R.id.scheduledTime);
            rideshareButton = (ImageButton) itemXML.findViewById(R.id.rideshareButton);
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

        //Use Picasso to load the location's profile picture
        Picasso.with(holder.photo.getContext()).load(url).placeholder(R.drawable.ic_baseline_place_24).into(holder.photo);
        holder.category.setText(mDataset[position].getCategory());
        holder.restaurantName.setText(mDataset[position].getName());  // 3 (let 'textView' = some string)

        //Set a linearLayout listener
        final String link = mDataset[position].getWebsiteLink();
        final String photoLink = mDataset[position].getPhotoLinkStr();
        final Context ctx = holder.photo.getContext();
        final int pos = position;

        //Allow photo to be clickable, open the photo URL to show the full size photo
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(photoLink); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ctx.startActivity(intent);
            }
        });

        //Allow category to be clickable, open the restaurant website
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ctx.startActivity(intent);
            }
        });

        //Allow restaurant name to be clickable, open the restaurant website
        holder.restaurantName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                ctx.startActivity(intent);
            }
        });

        int hr = mDataset[position].getStartHr();
        String toAppend = " am"; //We should append either " am" or " pm" to the scheduled time
        if(hr >= 12) {
            toAppend = " pm";
        }

        //Check if past 12:00 pm
        if(hr > 12) {
            hr -= 12;
        }
        String selectedTime = hr + ":";
        if(mDataset[position].getStartMin() < 10) {
            selectedTime += "0";
        }

        selectedTime += mDataset[position].getStartMin();
        selectedTime += toAppend;

        String example = "5:30 pm";
        holder.scheduledTime.setText(selectedTime);

        holder.rideshareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx, "Lyft", Toast.LENGTH_SHORT).show();
                try {
                    String city = mDataset[pos].getCountry();
                    if(city.equals("Paris") || city.equals("London")) {
                        deepLinkIntoLyft(ctx, mDataset[pos].getAddress());
                    }
                    else { //For Hyderabad, San Francisco, and New York use Uber
                        deepLinkIntoUber(ctx, mDataset[pos].getAddress());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    private void deepLinkIntoLyft(Context ctx, String address) throws IOException {
        if (isPackageInstalled(ctx, LYFT_PACKAGE)) {
            //This intent will help you to launch if the package is already installed
            List<String> latLon = getCoordinates(ctx, address);
            String lat = latLon.get(0);
            String lon = latLon.get(1);
            openLink(ctx, "lyft://ridetype?id=lyft&destination[latitude]="+ lat +"&destination[longitude]="+lon);

            Log.d(TAG, "Lyft is already installed on your phone.");
        } else {
            openLink(ctx, "https://www.lyft.com/signup/SDKSIGNUP?clientId=iYDmuVOvA6Xf&sdkName=android_direct");

            Log.d(TAG, "Lyft is not currently installed on your phone..");
        }
    }

    //startActivity Issue FIX: https://stackoverflow.com/questions/33173043/cannot-resolve-method-startactivity
    static void openLink(Context ctx, String link) {
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW);
        playStoreIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        playStoreIntent.setData(Uri.parse(link));
        ctx.startActivity(playStoreIntent);
    }

    static boolean isPackageInstalled(Context context, String packageId) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageId, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // ignored.
        }
        return false;
    }

    //Geocoding API Key: "AIzaSyDLSCipODCcpB1MzEGeWsEuLJe4bJIiDvs"
    //Reference: https://stackoverflow.com/questions/16271855/geocoder-api-for-java
    public List<String> getCoordinates(Context ctx, String address) throws IOException {
        Geocoder geocoder = new Geocoder(ctx, new Locale("en", "US"));
        List<Address> list = geocoder.getFromLocationName(address, 5);
        Address location = list.get(0);
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        List<String> results = new ArrayList<String>();
        results.add(Double.toString(lat));
        results.add(Double.toString(lon));

        return results;
    }

    //Uber deeplink
    private void deepLinkIntoUber(Context ctx, String address) throws IOException {
        if (isPackageInstalled(ctx, UBER_PACKAGE)) {
            //This intent will help you to launch if the package is already installed
            List<String> latLon = getCoordinates(ctx, address);
            String lat = latLon.get(0);
            String lon = latLon.get(1);


            openLink(ctx, "uber://?client_id=<CLIENT_ID>&dropoff[latitude]="+lat+"&dropoff[longitude]="+lon+"&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d");

            Log.d(UBERTAG, "Lyft is already installed on your phone.");
        } else {
            openLink(ctx, "https://www.lyft.com/signup/SDKSIGNUP?clientId=iYDmuVOvA6Xf&sdkName=android_direct");

            Log.d(UBERTAG, "Lyft is not currently installed on your phone..");
        }
    }

}
