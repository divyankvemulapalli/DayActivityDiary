package com.example.divyank.dad;

/**
 * Created by Divyank on 31-07-2016.
 */
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
public class FoldingCellListAdapter extends ArrayAdapter<Item>
{
    String table_name;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    int i=1;

    public FoldingCellListAdapter(Context context, List<Item> objects,String table_name) {
        super(context, 0, objects);
        this.table_name=table_name+"images";
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        Log.d("position",String.valueOf(position));
        // get item for selected view
        Item item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.day = (TextView) cell.findViewById(R.id.title_day);
            viewHolder.month = (TextView) cell.findViewById(R.id.title_month);
            viewHolder.year = (TextView) cell.findViewById(R.id.title_year);
            viewHolder.title = (TextView) cell.findViewById(R.id.event_title);
            viewHolder.address = (TextView) cell.findViewById(R.id.title_address);
            viewHolder.title_1 = (TextView) cell.findViewById(R.id.event_title_1);
            viewHolder.address_1 = (TextView) cell.findViewById(R.id.title_address_1);
            viewHolder.description=(TextView)cell.findViewById(R.id.event_description_content);
            viewHolder.date=(TextView)cell.findViewById(R.id.title_date);
            viewHolder.photos=(TextView)cell.findViewById(R.id.photo_button);

            viewHolder.photos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(getContext());
                    Log.d("tablename", table_name);
                    Log.d("position", String.valueOf(position));
                    ArrayList<String> imagePaths = db.getEventsImages(table_name, position);
                    if (imagePaths != null) {
                        Intent i = new Intent(getContext(), Loadimages.class);
                        i.putStringArrayListExtra("imgs", imagePaths);
                        getContext().startActivity(i);
                    }
                    else
                        Toast.makeText(getContext(),"No photos recorded",Toast.LENGTH_SHORT).show();
                }
            });

            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        // bind data from selected element to view through view holder
        viewHolder.day.setText(item.getday());
        viewHolder.month.setText(item.getmonth());
        viewHolder.year.setText(item.getyear());
        viewHolder.title.setText(item.gettitle());
        viewHolder.address.setText(item.getaddress());
        viewHolder.title_1.setText(item.gettitle_1());
        viewHolder.address_1.setText(item.getaddress_1());
        viewHolder.description.setText(item.getdescription());
        viewHolder.date.setText(item.getdate());
        viewHolder.photos.setText(item.getphotos());

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    // View lookup cache
    private static class ViewHolder {
        TextView day;
        TextView month;
        TextView year;
        TextView title;
        TextView address;
        TextView title_1;
        TextView address_1;
        TextView description;
        TextView date;
        TextView photos;
    }
}

