package com.rarcher.Fragments;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rarcher.DataBase.LocalDB;
import com.rarcher.DataBase.Nowusers;
import com.rarcher.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.https://blog.csdn.net/sinat_25926481/article/details/70880047
 */
public class Convience extends Fragment {

    private Activity activity;
    private GridView gridView;
    private List<String> dataList;
    private ArrayAdapter<String> adapter;

    String name[]={"医疗健康","家政支持","水电费","安全备忘","物业","法律咨询","生活安排"};

    public Convience() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_convience, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        listener();

    }

    private void init(View view){
        gridView = view.findViewById(R.id.cv);
        initData();
        adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_1,dataList);
        gridView.setAdapter(adapter);

    }

    private void listener(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positon, long l) {
                switch (positon) {
                    case 3 :

                        
                        break;
                }
            }
        });
    }
    void initData() {
        //图标下的文字

        dataList = new ArrayList<String>();
        for (int i = 0; i <name.length; i++) {
            dataList.add(name[i]);
        }
    }

}
