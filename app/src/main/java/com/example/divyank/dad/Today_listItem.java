package com.example.divyank.dad;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.ArrayList;

public class Today_listItem extends AppCompatActivity {

    DatabaseHandler db;
    private ArrayList<String> imagePaths = null;

    ArrayList<Image> images=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_list_item);

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
            String li = getIntent().getStringExtra("list_item");
            db = new DatabaseHandler(this);
            EditText ed3 = (EditText) findViewById(R.id.editText2);
            ed3.setText(li);

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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do something you want
                finish();

            }
        });
        String li = getIntent().getStringExtra("list_item");
        db = new DatabaseHandler(this);
        EditText ed3 = (EditText) findViewById(R.id.editText2);
        ed3.setText(li);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);

            imagePaths = new ArrayList<String>();
            int count = images.size();
            int i=0;
            while(count!=0)
            {
                imagePaths.add(images.get(i).path);
                i++;
                count--;
            }

        }
    }


    public void img(View view) {
        Intent intent = new Intent(this, AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    public void loadimgactivity(View view)
    {
        if(imagePaths!=null) {
            Intent i = new Intent(this, Loadimages.class);
            i.putStringArrayListExtra("imgs", imagePaths);
            startActivity(i);
        }
        else
            Toast.makeText(getApplicationContext(),"No photos selected",Toast.LENGTH_SHORT).show();

    }

    public void savetodb(View view)
    {
        EditText ed1 = (EditText) findViewById(R.id.editText);
        EditText ed2 = (EditText) findViewById(R.id.editText1);
        EditText ed3 = (EditText) findViewById(R.id.editText2);

        if ("".equals(ed1.getText().toString()) || "".equals(ed2.getText().toString()) || "".equals(ed3.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please fill the above information", Toast.LENGTH_SHORT).show();
        } else {
            db.addEvent(new EventAdder(ed1.getText().toString(), ed2.getText().toString(), ed3.getText().toString()));
            if(imagePaths!=null)
                db.addEventImages(imagePaths);
            else
                db.addEventImages(null);
            finish();

        }

    }




    @Override
    public void onStart() {
        super.onStart();

        }

    @Override
    public void onStop() {
        super.onStop();

    }




}
