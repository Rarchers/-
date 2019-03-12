package com.rarcher.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.rarcher.R;

import java.util.List;

/**
 * Created by R on 2019/2/11.
 */

public class Events_Adapter extends ArrayAdapter<Events> {

    private int resourceId;

    public Events_Adapter(Context context, int resource, List<Events> objects) {
        super(context, resource,objects);
        resourceId = resource;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Events events = getItem(position);
        View view;
        ViewHolder viewHolder;
        //优化ListView
        if (convertView == null){
            view  = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView =view.findViewById(R.id.ac_title);
            viewHolder.textView = view.findViewById(R.id.news_title);
            view.setTag(viewHolder);

        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(events.getImageId());
        viewHolder.textView.setText(events.getTitle());
        return view;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
