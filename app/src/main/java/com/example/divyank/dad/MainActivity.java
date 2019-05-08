package com.example.divyank.dad;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public boolean isFirstStart;
    private Calendar calendar;
    private int year, month, day;
    DatabaseHandler db;
    public ArrayList<ArrayList<String>> array_list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Yesterday"));
        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("Reminders"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();


                Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());
                Log.d("share","tag");
                //  Create a new boolean and preference and set it to true
                isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart)
                {
                    startAt12();
                    startAt9();

                    //  Launch app intro

                    Intent i = new Intent(MainActivity.this, MyIntro.class);
                    startActivity(i);


                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.commit();
                }

            }
        });

// Start the thread
        t.start();
        Log.d("in", "main");



    }

    public void fabs1(View view)
    {
        if(view.getId() == R.id.fab_event) {
            Intent i = new Intent(MainActivity.this, Addextraevent.class);
            startActivity(i);
        }

        if(view.getId() == R.id.fab_rem) {
            Intent i = new Intent(MainActivity.this, Addreminder.class);
            startActivity(i);
        }

    }





    @Override
    protected void onStart()
    {
        super.onStart();
        startService(new Intent(getApplicationContext(), MyService.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        if(id==R.id.action_settings)
        {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);

            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dp = new DatePickerDialog(this,myDateListener,year,month,day);
            dp.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void startAt12() {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Calendar calSet = Calendar.getInstance();
        calSet.set(Calendar.HOUR_OF_DAY,15);
        calSet.set(Calendar.MINUTE,28);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        calSet.set(Calendar.AM_PM,Calendar.PM);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 310, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),1000*60, pendingIntent);
    }

    public void startAt9() {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Calendar calSet = Calendar.getInstance();
        calSet.set(Calendar.HOUR_OF_DAY,21);
        calSet.set(Calendar.MINUTE,0);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        calSet.set(Calendar.AM_PM,Calendar.PM);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),315, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(),alarmManager.INTERVAL_DAY, pendingIntent);
    }


    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener()
    {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            arg2++;
            String year = String.valueOf(arg1);
            String month = String.format("%02d", arg2);
            String day = String.format("%02d", arg3);
            String table_name = "_"+year+month+day;
            String toast_name=day+" "+month+" "+year;

            db=new DatabaseHandler(getBaseContext());

            array_list = db.getEvents(table_name);
            if(array_list==null)
            {
                Toast.makeText(getApplicationContext(),"No Events recorded on "+toast_name,Toast.LENGTH_SHORT).show();
            }
            else
            {
                String month_1 = getMonth(arg2);
                Intent i = new Intent(MainActivity.this,Cardview.class);
                i.putExtra("d",day);
                i.putExtra("m", month);
                i.putExtra("m1",month_1);
                i.putExtra("y",year);
                startActivity(i);
            }

        }
    };

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
    }





}
