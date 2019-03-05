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
 * Created by 25532 on 2019/3/5.
 */

public class Safe_Note_Adapter extends ArrayAdapter<Safe_Note_been> {
    private int resourceid;

    public Safe_Note_Adapter(Context context, int resource, List<Safe_Note_been> objects) {
        super(context, resource, objects);
        this.resourceid = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Safe_Note_been safe_note_been = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceid,parent,false);
        TextView titles = view.findViewById(R.id.safetitle);
        TextView context = view.findViewById(R.id.safecontext);
        titles.setText(safe_note_been.getTitle());
        context.setText(safe_note_been.getContext());
        return view;

    }

}
