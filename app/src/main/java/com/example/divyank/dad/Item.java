package com.example.divyank.dad;

/**
 * Created by Divyank on 31-07-2016.
 */
import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class Item {

    private String day;
    private String month;
    private String year;
    private String title;
    private String address;
    private String title_1;
    private String address_1;
    private String description;
    private String date;
    private String photos;

    public Item() {
    }

    public Item(String day, String month, String year, String title, String address, String title_1,String address_1,String description,String date,String photos) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.title = title;
        this.address = address;
        this.title_1 = title_1;
        this.address_1=address_1;
        this.description=description;
        this.date=date;
        this.photos=photos;
    }

    public String getday() {
        return day;
    }

    public void setday(String day) {
        this.day = day;
    }

    public String getmonth() {
        return month;
    }

    public void setmonth(String month) {
        this.month = month;
    }

    public String getyear() {
        return year;
    }

    public void setyear(String year) {
        this.year = year;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }


    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String gettitle_1() {
        return title_1;
    }

    public void settitle_1(String title_1) {
        this.title_1 = title_1;
    }

    public String getaddress_1() {
        return address_1;
    }

    public void setaddress_1(String address_1) {
        this.address_1=address_1;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description=description;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date=date;
    }

    public String getphotos() {
        return photos;
    }

    public void setphotos(String photos) {
        this.photos=photos;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;


        if (day != null ? !day.equals(item.day) : item.day != null) return false;
        if (month != null ? !month.equals(item.month) : item.month != null)
            return false;
        if (year != null ? !year.equals(item.year) : item.year != null)
            return false;
        if (title != null ? !title.equals(item.title) : item.title != null)
            return false;
        if (address != null ? !address.equals(item.address) : item.address != null) return false;

        if (date != null ? !date.equals(item.date) : item.date != null) return false;

        if(address_1 != null ? !address_1.equals(item.address_1) : item.address_1 != null) return false;

        if (description != null ? !description.equals(item.description) : item.description != null);

        if (photos != null ? !photos.equals(item.photos) : item.photos != null);

        return !(title_1 != null ? !title_1.equals(item.title_1) : item.title_1 != null);

    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (title_1 != null ? title_1.hashCode() : 0);
        result = 31 * result + (address_1 != null ? address_1.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);

        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Item> getTestingList(ArrayList<ArrayList<String>> events,String day,String month,String year)
    {
        ArrayList<Item> items = new ArrayList<>();
        int size=events.size();
        int j=0;
        while(size!=0 )
        {
            items.add(new Item(day,month,year,events.get(j).get(0),events.get(j).get(2),events.get(j).get(0),events.get(j).get(2),events.get(j).get(1),day+" "+month+" "+year,"PHOTOS"));
            j++;
            size--;
        }

        return items;

    }

}
