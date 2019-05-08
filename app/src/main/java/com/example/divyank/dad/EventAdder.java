package com.example.divyank.dad;

/**
 * Created by Divyank on 29-07-2016.
 */
public class EventAdder 
{
    String title;
    String description;
    String location;

    public EventAdder()
    {

    }
    public EventAdder(String _title, String _description,String _location)
    {

        this.title = _title;
        this.description = _description;
        this.location=_location;
    }

    //getting title
    public String get_title()
    {
        return this.title;
    }

    //setting title
    public void set_title(String _title)
    {
        this.title=_title;
    }

    //getting description
    public String get_description()
    {
        return this.description;
    }

    //setting description
    public void set_description(String _description)

    {
        this.description=_description;
    }

    //getting location
    public String get_location()
    {
        return this.location;
    }

    //setting location
    public void set_location(String _location)

    {
        this.location=_location;
    }
    
}
