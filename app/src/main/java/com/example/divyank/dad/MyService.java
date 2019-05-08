package com.example.divyank.dad;

/**
 * Created by Divyank on 31-07-2016.
 */
import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Divyank on 16-06-2016.
 */
public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener
{
    static int i=0;
    public static Location mLastLocation;
    double latitude;
    double longitude;
    boolean isGPSEnabled = false;
    protected LocationManager locationManager;
    Timer timer;
    private PendingIntent pendingIntent;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    DatabaseHandler db;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 1000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 1; // 10 meters

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onCreate() {
        super.onCreate();

        db = new DatabaseHandler(this);
        //startAt12();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        // Building the GoogleApi client
        buildGoogleApiClient();
        createLocationRequest();
        if (mGoogleApiClient != null) {

            mGoogleApiClient.connect();
        }
        return START_STICKY;
    }



    public void callAsynchronousTask()
    {

            final Handler handler = new Handler();
            timer = new Timer();
            TimerTask doAsynchronousTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                locationManager = (LocationManager) getBaseContext().getSystemService(Context.LOCATION_SERVICE);
                                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                                if (isGPSEnabled)
                                    startLocationUpdates();
                                else
                                    Toast.makeText(getApplicationContext(),"Enable GPS",Toast.LENGTH_LONG).show();

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                            }
                        }
                    });
                }
            };
            timer.scheduleAtFixedRate(doAsynchronousTask, 0, 1000*60*10); //execute in every 50000 ms


    }



    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

       mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            //Toast.makeText(getApplicationContext(),latitude+" "+longitude,Toast.LENGTH_SHORT).show();
            db.addLocation(new LocationDuration(longitude,latitude));
        }
        else
            Toast.makeText(getApplicationContext(),"No Location",Toast.LENGTH_SHORT).show();

    }





    @Override
    public void onDestroy()
    {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onDestroy();
        timer.cancel();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        callAsynchronousTask();

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     * */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }


}
