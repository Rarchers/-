package com.rarcher.Fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.rarcher.DataBase.LocalDB;
import com.rarcher.DataBase.Nowusers;
import com.rarcher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Online extends Fragment implements View.OnClickListener {


    private GridView gv;
    private ListView listView;
    private TextView title;
    private String[] home = {"衣","食","住","行","娱"};

    public Online() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        listView = view.findViewById(R.id.lv_menu);
        gv = view.findViewById(R.id.grid);
        title = view.findViewById(R.id.tv_titile);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,home);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

    }
}
