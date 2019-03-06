package com.rarcher.Fragments;


import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.rarcher.Acticitys.FigureSafe;
import com.rarcher.Acticitys.List_Been;
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
                        Intent intent = new Intent(getActivity(), FigureSafe.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Toast.makeText(getContext(),"抱歉，此功能还处于完善状态，稍晚时间会上线，请耐心等待",Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Intent intent1= new Intent(getActivity(), List_Been.class);
                        intent1.putExtra(List_Been.HOSPITAL,List_Been.HOSPITAL);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent0= new Intent(getActivity(), List_Been.class);
                        intent0.putExtra(List_Been.HOUSEKEPPING,List_Been.HOUSEKEPPING);
                        startActivity(intent0);
                        break;
                    case 4:
                        Intent intent4= new Intent(getActivity(), List_Been.class);
                        intent4.putExtra(List_Been.PROPERTY,List_Been.PROPERTY);
                        startActivity(intent4);
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
