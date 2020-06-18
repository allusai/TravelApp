package com.example.travelapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class QueryMaker {

    private Context mContext;
    String dbFile; // 13 rows,
    TouristLocation[] placesList;
    int numPlaces;

    public QueryMaker()
    {

    }

    public QueryMaker(Context context)
    {
        this.mContext = context;
        numPlaces = 13;
        placesList = new TouristLocation[numPlaces];
        try {
            InputStream is = mContext.getAssets().open("data.txt");
            System.out.println("That file was FOUND!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            int i = 0;

            while ((line = reader.readLine()) != null) {
                //Separate the line by commas to get individual data values
                String[] tokens = line.split(",");

                //Make a TouristLocation object with the right casting
                TouristLocation newLoc = new TouristLocation(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], Boolean.parseBoolean(tokens[5]), Double.parseDouble(tokens[6]), tokens[7], Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9]));

                //Add this TouristLocation to the array
                placesList[i] = newLoc;
                i++;
            }
            is.close();
        }
        catch(IOException ioe)
        {
            System.out.println("That file was NOT FOUND!");
        }
    }

    public void printMyData()
    {
        System.out.println("**********");
        System.out.println("Printing data stored about Tourist Locations");
        for(int i = 0; i < numPlaces; i++)
        {
            TouristLocation temp = placesList[i];
            System.out.println(i + ": " + temp.toString());
        }
        System.out.println("**********");
    }


    public TouristLocation[] search(String location)
    {
        //Search database for 'location'
        int matchCount = 0;
        for(int i = 0; i < numPlaces; i++)
        {
            if(placesList[i].getCountry().equals(location))
            {
                matchCount++;
            }
        }

        TouristLocation[] results = new TouristLocation[matchCount];
        int ptr = 0;
        for(int i = 0; i < numPlaces; i++)
        {
            if(placesList[i].getCountry().equals(location))
            {
                results[ptr] = placesList[i];
                ptr++;
            }
        }

        return results;
        //Call database with 'location'
        /*TouristLocation test = new TouristLocation("Cafe 81", "Restaurant", "", "", "Paris", false, 5, "", 5, 20);
        TouristLocation sample = new TouristLocation("Cafe Bagel", "Restaurant", "", "", "Paris", false, 4, "", 6, 13);

        if(location.equals("London"))
        {
            test = new TouristLocation("London Bridge Diner", "Coffee House", "", "", "London", false, 5, "https://static.standard.co.uk/s3fs-public/thumbnails/image/2019/01/10/09/amrutha-lounge-1001a.JPG", 5, 20);
            sample = new TouristLocation("Buckingham Palace Restaurant", "Tourist Place", "", "", "London", false, 5, "", 5, 20);
        }
        else if(location.equals("Hyderabad"))
        {
            test = new TouristLocation("Biryani Kabob House", "Tourist Place", "", "", "Hyderabad", false, 5, "", 5, 20);
            sample = new TouristLocation("Noodle House", "Restaurant", "", "", "Hyderabad", false, 5, "", 5, 20);
        }

        return new TouristLocation[]{test, sample}; */
    }
}