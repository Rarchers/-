package com.rarcher.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rarcher.R;

import java.util.List;

/**
 * Created by 25532 on 2019/3/4.
 */

public class Has_oder_Adapter extends ArrayAdapter<Has_oder_been> {
    private int resourceid;

    public Has_oder_Adapter(Context context, int resource, List<Has_oder_been> objects) {
        super(context, resource, objects);
        this.resourceid = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Has_oder_been has_oder_been = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceid,parent,false);

        TextView titles = view.findViewById(R.id.has_oder_title);
        TextView start = view.findViewById(R.id.has_oder_start_time);
        TextView uid = view.findViewById(R.id.uid);
        uid.setText("UID:"+has_oder_been.getUID());
        titles.setText(has_oder_been.getTitle());
        start.setText(has_oder_been.getStart_time());
        return view;

    }
}
