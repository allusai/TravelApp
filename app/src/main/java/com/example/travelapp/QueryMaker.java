package com.example.travelapp;

public class QueryMaker {

    public static TouristLocation[] search(String location)
    {
        //Call database with 'location'
        TouristLocation test = new TouristLocation("Cafe 81", "Restaurant", "", "", "Paris", false, 5, "", 5, 20);
        TouristLocation sample = new TouristLocation("Cafe Bagel", "Restaurant", "", "", "Paris", false, 4, "", 6, 13);

        if(location.equals("London"))
        {
            test = new TouristLocation("London Bridge Diner", "Coffee House", "", "", "London", false, 5, "", 5, 20);
            sample = new TouristLocation("Buckingham Palace Restaurant", "Tourist Place", "", "", "London", false, 5, "", 5, 20);
        }
        else if(location.equals("Hyderabad"))
        {
            test = new TouristLocation("Biryani Kabob House", "Tourist Place", "", "", "Hyderabad", false, 5, "", 5, 20);
            sample = new TouristLocation("Noodle House", "Restaurant", "", "", "Hyderabad", false, 5, "", 5, 20);
        }

        return new TouristLocation[]{test, sample};
    }
}
