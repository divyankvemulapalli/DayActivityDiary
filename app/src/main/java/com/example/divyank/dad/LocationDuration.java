package com.example.divyank.dad;

/**
 * Created by Divyank on 23-06-2016.
 */
public class LocationDuration
{
    double longitude;
    double latitude;

    public LocationDuration()
    {

    }
    public LocationDuration(double _longitude, double _latitude)
    {

        this.longitude = _longitude;
        this.latitude = _latitude;
    }

    //getting longitude
    public double get_longitude()
    {
        return this.longitude;
    }

    //setting longitude
    public void set_longitude(long _longitude)
    {
        this.longitude=_longitude;
    }

    //getting latitude
    public double get_latitude()
    {
        return this.latitude;
    }

    //setting longitude
    public void set_latitude(long _latitude)

    {
        this.latitude=_latitude;
    }
}
