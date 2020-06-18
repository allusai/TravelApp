package com.example.travelapp;

import android.net.Uri;

public class TouristLocation
{
    private String name,category,websiteLink,description,country;
    private boolean selected;
    private double rating;
    private String photoLinkStr;

    private int startHr, startMin;

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
}
