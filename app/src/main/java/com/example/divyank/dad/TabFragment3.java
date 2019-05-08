package com.example.divyank.dad;

/**
 * Created by Divyank on 17-06-2016.
 */
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TabFragment3 extends Fragment {

    ListView listView;
    DatabaseHandler db;
    ArrayAdapter<String> listViewAdapter;
    public ArrayList<ArrayList<String>> array_list;
    public ArrayList<String> array_list_add;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view =  inflater.inflate(R.layout.tab_fragment_3, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list1);

        display();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh1);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                display();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    public void display() {
        if (listViewAdapter != null)
            listViewAdapter.clear();

        array_list = new ArrayList<>();

        db = new DatabaseHandler(getActivity());

        if (db.getReminders() != null) {
            TextView textView = (TextView) getView().findViewById(R.id.textView50);
            textView.setVisibility(View.INVISIBLE);


            array_list = db.getReminders();

            int j = 0;
            int size = array_list.size();
            array_list_add = new ArrayList<>();
            Log.d("SIZE", Integer.toString(size));
            while (size != 0) {
                String data_1 = array_list.get(j).get(0);
                String data_2 = array_list.get(j).get(1);

                array_list_add.add("'"+data_1+ "' " + "on" + " " + data_2);
                j++;
                size--;
            }


            listViewAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, array_list_add);
            listView.setAdapter(listViewAdapter);

            Log.d("size of arraylistadd", String.valueOf(array_list_add.size()));

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long arg3) {

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    final int i = position;
                    alertDialogBuilder.setTitle("Day Activity Diary");
                    alertDialogBuilder.setMessage("Do yo want to delete this reminder?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.removereminder(i + 1);
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
        }
    }
}