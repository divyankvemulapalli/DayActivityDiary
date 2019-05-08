package com.example.divyank.dad;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Divyank on 04-08-2016.
 */
public class Addreminder extends AppCompatActivity
{
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder);

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


        Button button = (Button)findViewById(R.id.button03);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                db=new DatabaseHandler(Addreminder.this);
                EditText ed1 = (EditText) findViewById(R.id.editText010);
                EditText ed2 = (EditText) findViewById(R.id.editText020);
                String year= ed2.getText().toString().substring(0,4);
                String month = ed2.getText().toString().substring(5,7);
                String day=ed2.getText().toString().substring(8,10);
                String hour=ed2.getText().toString().substring(11,13);
                String min=ed2.getText().toString().substring(14,16);

                Toast.makeText(getApplicationContext(),year+month+day+hour+min,Toast.LENGTH_SHORT).show();
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Calendar calSet = Calendar.getInstance();
                calSet.set(Calendar.DAY_OF_MONTH,Integer.parseInt(day));
                calSet.set(Calendar.MONTH,Integer.parseInt(month));
                calSet.set(Calendar.YEAR,Integer.parseInt(year));
                calSet.set(Calendar.HOUR_OF_DAY,Integer.parseInt(hour));
                calSet.set(Calendar.MINUTE,Integer.parseInt(min));
                calSet.set(Calendar.SECOND, 0);
                calSet.set(Calendar.MILLISECOND, 0);

                Intent intent = new Intent(Addreminder.this, Alarmreminder.class);
                intent.putExtra("msg",ed1.getText().toString());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 320, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);

                db.addReminder(ed1.getText().toString(), ed2.getText().toString());
                finish();
            }
        });
    }



}
