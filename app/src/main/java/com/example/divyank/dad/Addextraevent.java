package com.example.divyank.dad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;

import java.util.ArrayList;

/**
 * Created by Divyank on 04-08-2016.
 */
public class Addextraevent extends AppCompatActivity
{

    DatabaseHandler db;
    private ArrayList<String> imagePaths = null;

    ArrayList<Image> images=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extraevent);
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
        db = new DatabaseHandler(this);
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

    public void showimg(View view) {
        Intent intent = new Intent(this, AlbumSelectActivity.class);
//set limit on number of images that can be selected, default is 10
        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 10);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    public void loadimg(View view)
    {
        if(imagePaths!=null) {
            Intent i = new Intent(this, Loadimages.class);
            i.putStringArrayListExtra("imgs", imagePaths);
            startActivity(i);
        }
        else
            Toast.makeText(getApplicationContext(), "No photos selected", Toast.LENGTH_SHORT).show();

    }

    public void savetodbase(View view)
    {
        EditText ed1 = (EditText) findViewById(R.id.editText01);
        EditText ed2 = (EditText) findViewById(R.id.editText02);
        EditText ed3 = (EditText) findViewById(R.id.editText03);
        EditText ed4 = (EditText) findViewById(R.id.editText04);
        String date = ed4.getText().toString();
        date = date.substring(0,4)+date.substring(5,7)+date.substring(8,10);

        if ("".equals(ed1.getText().toString()) || "".equals(ed2.getText().toString()) || "".equals(ed3.getText().toString()) || "".equals(ed4.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Please fill the above information", Toast.LENGTH_SHORT).show();
        } else
        {
            db.addEvent1(new EventAdder(ed1.getText().toString(), ed2.getText().toString(), ed3.getText().toString()),date);
            if(imagePaths!=null)
                db.addEventImages1(imagePaths,date);
            else
                db.addEventImages1(null,date);


            finish();

        }

    }


}
