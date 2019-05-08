package com.example.divyank.dad;

/**
 * Created by Divyank on 17-06-2016.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import java.text.DateFormatSymbols;

public class TabFragment1 extends Fragment {

    DatabaseHandler db;
    public ArrayList<ArrayList<String>> array_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        return inflater.inflate(R.layout.tab_fragment_1, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String event_date=getYesterdayDateString();
        event_date=event_date.replace("-","");
       event_date="_"+event_date;
        String year = event_date.substring(1,5);
        String month = event_date.substring(5,7);
        String day = event_date.substring(7, 9);
        db=new DatabaseHandler(getContext());
        array_list = db.getEvents(event_date);

        if(array_list!=null) {
            // get our list view
            ListView theListView = (ListView) getView().findViewById(R.id.mainListView1);


            // prepare elements to display
            final ArrayList<Item> items = Item.getTestingList(array_list,day, new DateFormatSymbols().getMonths()[Integer.parseInt(month)-1], year);

            // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
            final FoldingCellListAdapter adapter = new FoldingCellListAdapter(getContext(), items, event_date);


            // set elements to adapter
            theListView.setAdapter(adapter);

            theListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long arg3) {
                    adapter.remove(items.remove(position));
                    adapter.notifyDataSetChanged();

                    return true;
                }

            });

            // set on click event listener to list view
            theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    // toggle clicked cell state
                    ((FoldingCell) view).toggle(false);
                    // register in adapter that state for selected cell is toggled
                    adapter.registerToggle(pos);
                }
            });

        }
        else
        {
            TextView tv = (TextView)getView().findViewById(R.id.textView100);
            tv.setVisibility(View.VISIBLE);
        }
    }

    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }
}