package com.rarcher.Acticitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rarcher.Adapter.Infomations;
import com.rarcher.R;

public class Details extends AppCompatActivity {
    final String[] hospitals = {"保定市第一中心医院","河北大学附属医院","保定市第一中医院","保定252医院","河北省第六人民医院"};
    public static final String POINT = "POINT";
    TextView details;
    String data = "";
    Intent intent;
    String points;
    int tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        details = findViewById(R.id.details);
        intent = getIntent();
        points = intent.getStringExtra(POINT);
        collapsingToolbar.setTitle("医疗健康");
        switch (points){
            case "保定市第一中心医院":
                data = Infomations.保定市第一中心医院;
                details.setText(data);
                tel = 0312-5976500;
                break;
            case "河北大学附属医院":
                data = Infomations.河北大学附属医院;
                details.setText(data);
                tel = 0312-5981818;
                break;
            case "保定市第一中医院":
                data = Infomations.保定市第一中医院;
                details.setText(data);
                tel = 0312-2023310;
                break;
            case "保定252医院":
                data = Infomations.保定252医院;
                details.setText(data);
                tel = 0312-2058114;
                break;
            case "河北省第六人民医院":
                data = Infomations.河北省第六人民医院;
                details.setText(data);
                tel = 0312-5079256;
                break;
            case List_Been.HOUSEKEPPING:
                data = Infomations.E城E家;
                details.setText(data);
                tel = 0731-85495581;
                break;



        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "是否要拨打电话", Snackbar.LENGTH_LONG)
                        .setAction("是", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));//跳转到拨号界面，同时传递电话号码
                                startActivity(intent);
                            }
                        }).show();
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
