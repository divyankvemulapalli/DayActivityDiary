package com.example.divyank.dad;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by Divyank on 31-07-2016.
 */
public class Loadimages extends AppCompatActivity
{
    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        if (savedInstanceState == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setTitle("Day Activity Diary");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //do something you want
                    finish();

                }
            });
        }

        gridView = (GridView) findViewById(R.id.grid_view);

        ArrayList<String> imgs=getIntent().getStringArrayListExtra("imgs");

        InitilizeGridLayout();
        // Gridview adapter
        adapter = new GridViewImageAdapter(this, imgs,
                columnWidth);

        // setting grid view adapter
        gridView.setAdapter(adapter);

           }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Day Activity Diary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                finish();

            }
        });

        gridView = (GridView) findViewById(R.id.grid_view);

        ArrayList<String> imgs=getIntent().getStringArrayListExtra("imgs");

        InitilizeGridLayout();
        // Gridview adapter
        adapter = new GridViewImageAdapter(this, imgs,
                columnWidth);

        // setting grid view adapter
        gridView.setAdapter(adapter);



    }

    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                8, r.getDisplayMetrics());

        columnWidth = (int) ((getScreenWidth() - ((3 + 1) * padding)) / 3);

        gridView.setNumColumns(3);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) getBaseContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}
