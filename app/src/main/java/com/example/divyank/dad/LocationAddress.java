package com.example.divyank.dad;

/**
 * Created by Divyank on 23-06-2016.
 */
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {
    private static final String TAG = "LocationAddress";
    private static String result = null;
    public static String getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context) {


                Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        sb.append(address.getAddressLine(1)+"\n");
                        sb.append(address.getAddressLine(2));

                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);}
        return result;
    }
}
