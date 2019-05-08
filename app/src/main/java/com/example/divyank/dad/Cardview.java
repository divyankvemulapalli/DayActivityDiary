package com.example.divyank.dad;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

/**
 * Created by Divyank on 31-07-2016.
 */
public class Cardview extends AppCompatActivity
{
    DatabaseHandler db;
    public ArrayList<ArrayList<String>> array_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardview);

        if (savedInstanceState == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setTitle("Day Activity Diary");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            String day = getIntent().getStringExtra("d");
            String month = getIntent().getStringExtra("m");
            String month_1=getIntent().getStringExtra("m1");
            String year = getIntent().getStringExtra("y");
            String table_name = "_"+year+month+day;
            db=new DatabaseHandler(getBaseContext());
            array_list = db.getEvents(table_name);

            // get our list view
            ListView theListView = (ListView) findViewById(R.id.mainListView);


            // prepare elements to display
            final ArrayList<Item> items = Item.getTestingList(array_list, day, month_1, year);

            // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
            final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items,table_name);


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

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //do something you want
                    finish();

                }
            });


        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Day Activity Diary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String day = getIntent().getStringExtra("d");
        String month = getIntent().getStringExtra("m");
        String month_1=getIntent().getStringExtra("m1");
        String year = getIntent().getStringExtra("y");
        String table_name = "_"+year+month+day;
        db=new DatabaseHandler(getBaseContext());
        array_list = db.getEvents(table_name);

        // get our list view
        ListView theListView = (ListView) findViewById(R.id.mainListView);


        // prepare elements to display
        final ArrayList<Item> items = Item.getTestingList(array_list, day, month_1, year);


        // create custom adapter that holds elements and their state (we need hold a id's of unfolded elements for reusable elements)
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items,table_name);

        // set elements to adapter
        theListView.setAdapter(adapter);

        theListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3)
            {
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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                finish();

            }
        });


    }




}

