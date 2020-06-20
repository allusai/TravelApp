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
    }

    public TouristLocation[] getOnlySelectedLocations()
    {
        int matchCount = 0;
        for(int i = 0; i < numPlaces; i++)
        {
            if(getSelectedStatus(i))
            {
                matchCount++;
            }
        }

        TouristLocation[] results = new TouristLocation[matchCount];
        int ptr = 0;
        for(int i = 0; i < numPlaces; i++)
        {
            if(getSelectedStatus(i))
            {
                results[ptr] = placesList[i];
                ptr++;
            }
        }

        return results;
    }


    public boolean getSelectedStatus(int pos) {
        return placesList[pos].isSelected();
    }

    public void toggleSelected(int pos) {
        if(placesList[pos].isSelected())
        {
            placesList[pos].setSelected(false);
        }
        else
        {
            placesList[pos].setSelected(true);
        }
    }

    /* Useful for transferring placesList to a new activity*/
    public String turnDataIntoSerializedString()
    {
        String result = "";

        for(int i = 0; i < numPlaces; i++)
        {
            result += placesList[i].encodeToString();
            if(i == 0)
            {
                placesList[i].checkEncoding();
            }
            if(i != numPlaces-1)
            {
                result += "~";
            }
        }

        return result;
    }

    /* Useful when making a query maker from a second activity*/
    public boolean loadPlacesListFromAString(String encodedString, Context c)
    {
        String[] objs = encodedString.split("~");

        mContext = c;
        numPlaces = 13;
        placesList = new TouristLocation[numPlaces];

        for(int i = 0; i < numPlaces; i++)
        {
            TouristLocation temp = new TouristLocation();
            boolean decoded = temp.decodeFromString(objs[i]);
            placesList[i] = temp;
            if(decoded)
            {
                System.out.println("Successfully decoded TouristLocation!");
            }
        }

        return true;
    }

    public boolean updateSelectedTime(int pos, int startHr, int startMin)
    {
        placesList[pos].setStartHr(startHr);
        placesList[pos].setStartMin(startMin);
        return true;
    }
}