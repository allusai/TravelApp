package com.example.travelapp;

import android.net.Uri;

public class TouristLocation
{
    private String name,category,websiteLink,description,country;
    private boolean selected;
    private double rating;
    private String photoLinkStr;

    private int startHr, startMin;

    public TouristLocation()
    {

    }

    public TouristLocation(String n, String c, String w, String d, String c2, boolean s, double r, String pls, int s1, int s2)
    {
        name = n;
        category = c;
        websiteLink = w;
        description = d;
        country = c2;
        selected = s;
        rating = r;
        photoLinkStr = pls;
        startHr = s1;
        startMin = s2;
    }


    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public String getDescription() {
        return description;
    }

    public String getCountry() {
        return country;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getRating() {
        return rating;
    }

    public String getPhotoLinkStr() {
        return photoLinkStr;
    }

    public int getStartHr() {
        return startHr;
    }

    public void setStartHr(int startHr) {
        this.startHr = startHr;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    @Override
    public String toString() {
        return "TouristLocation{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", websiteLink='" + websiteLink + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", selected=" + selected +
                ", rating=" + rating +
                ", photoLinkStr='" + photoLinkStr + '\'' +
                ", startHr=" + startHr +
                ", startMin=" + startMin +
                '}';
    }

    public void checkEncoding()
    {
        System.out.println("CHECK ENCODING");
        System.out.println(encodeToString());
    }

    public String encodeToString()
    {
        String result = "";
        result += (name + "," + category + "," + websiteLink + "," + description + "," + country);
        result += ("," + Boolean.toString(selected));
        result += ("," + Double.toString(rating));
        result += ("," + photoLinkStr);
        result += ("," + Integer.toString(startHr));
        result += ("," + Integer.toString(startMin));

        return result;
    }

    public boolean decodeFromString(String encodedString)
    {
        String[] tokens = encodedString.split(",");

        //Make a TouristLocation object with the right casting
        name = tokens[0];
        category = tokens[1];
        websiteLink = tokens[2];
        description = tokens[3];
        country = tokens[4];
        selected = Boolean.parseBoolean(tokens[5]);
        rating = Double.parseDouble(tokens[6]);
        photoLinkStr = tokens[7];
        startHr = Integer.parseInt(tokens[8]);
        startMin = Integer.parseInt(tokens[9]);

        return true;
    }

}
