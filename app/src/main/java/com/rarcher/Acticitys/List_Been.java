package com.rarcher.Acticitys;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rarcher.R;

public class List_Been extends AppCompatActivity {

    public final static String HOSPITAL = "hospital";

    Intent intent;
    String item;
    ListView details_lv;

    public final static String[] hospitals = {"保定市第一中心医院","河北大学附属医院","保定市第一中医院","保定252医院","河北省第六人民医院"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__been);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("我的预约");
        intent = getIntent();
        item = intent.getStringExtra(HOSPITAL);
        details_lv = findViewById(R.id.details_lv);
        ArrayAdapter<String> hospital_adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,hospitals);
        switch (item){
            case HOSPITAL:
                details_lv.setAdapter(hospital_adapter);
                break;

        }

        details_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (item){
                    case HOSPITAL:
                        Intent intent = new Intent(List_Been.this,Details.class);
                        intent.putExtra(Details.POINT,hospitals[position]);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
