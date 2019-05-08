package com.example.divyank.dad;

/**
 * Created by Divyank on 17-06-2016.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TabFragment2 extends Fragment  {


    ListView listView;
    DatabaseHandler db;
    ArrayAdapter<String> listViewAdapter;
    public ArrayList<ArrayList<String>> array_list;
    public ArrayList<String> array_list_add;
    public String preadd="";
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view =  inflater.inflate(R.layout.tab_fragment_2, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list);
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;

        if (connected)
            display();
        else {

            TextView textView = (TextView) getView().findViewById(R.id.textView);
            textView.setText("Internet Connectivity Issues");
            Toast.makeText(getContext(), "Please check the connectivity and swipe down to refresh the page", Toast.LENGTH_SHORT).show();
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                } else
                    connected = false;

                if (connected)
                    display();
                else {

                    TextView textView = (TextView) getView().findViewById(R.id.textView);
                    textView.setText("Internet Connectivity Issues");
                    Toast.makeText(getContext(), "Please check the connectivity and swipe down to refresh the page", Toast.LENGTH_SHORT).show();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    public void display()
    {
        preadd="";

        if(listViewAdapter!=null)
            listViewAdapter.clear();

        array_list = new ArrayList<>();
        array_list_add = new ArrayList<>();

        db=new DatabaseHandler(getActivity());
        Double lng=0.0,lat=0.0;

        if (db.getAlllocations() != null)
        {
            TextView textView = (TextView) getView().findViewById(R.id.textView);
            textView.setVisibility(View.INVISIBLE);

            LocationAddress locationAddress = new LocationAddress();
            array_list=db.getAlllocations();

            int j=0;
            int size = array_list.size();

            Log.d("SIZE", Integer.toString(size));
            while(size!=0 )
            {
                lng=Double.parseDouble(array_list.get(j).get(0));
                lat=Double.parseDouble(array_list.get(j).get(1));
                if("".equals(preadd))
                {
                    preadd = locationAddress.getAddressFromLocation(lat, lng,
                            getActivity().getApplicationContext());
                    array_list_add.add(preadd);

                }
                else
                {
                    String add=locationAddress.getAddressFromLocation(lat,lng,getActivity().getApplicationContext());
                    if(!add.equals(preadd))
                    {
                        array_list_add.add(add);
                        preadd=add;
                    }
                    else
                    {
                        db.removelocation(j);
                    }
                }
                j++;
                size--;
            }



            listViewAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, array_list_add);
            listView.setAdapter(listViewAdapter);

            Log.d("size of arraylistadd",String.valueOf(array_list_add.size()));

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long arg3) {

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    final int i = position;
                    alertDialogBuilder.setTitle("Day Activity Diary");
                    alertDialogBuilder.setMessage("Do yo want to delete this location - " + array_list_add.get(i));
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.removelocation(i+1);
                            array_list_add.remove(i);
                            listView.setAdapter(listViewAdapter);
                            listViewAdapter.notifyDataSetChanged();
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                    return true;
                }

            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {


                    // Launching new Activity on selecting single List Item
                    Intent i = new Intent(getContext(), Today_listItem.class);
                    // sending data to new activity
                    i.putExtra("list_item", array_list_add.get(position));
                    startActivity(i);

                }
            });

        }
        else
        {
            Log.d("in else","63131");
        }

    }





}