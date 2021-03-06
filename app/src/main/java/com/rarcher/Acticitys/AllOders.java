package com.rarcher.Acticitys;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rarcher.Adapter.Has_oder_Adapter;
import com.rarcher.Adapter.Has_oder_been;
import com.rarcher.Adapter.In_oder_Adapter;
import com.rarcher.Adapter.In_oder_been;
import com.rarcher.Adapter.To_oder_Adapter;
import com.rarcher.Adapter.To_oder_been;
import com.rarcher.DataBase.LocalDB;
import com.rarcher.DataBase.Nowusers;
import com.rarcher.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AllOders extends AppCompatActivity {

    private LocalDB localDB;
    private ListView lv_oder;
    private SwipeRefreshLayout fresh;
    TextView todo,hasdone,indoing;
    Has_oder_Adapter has_oder_adapter;
    To_oder_Adapter to_oder_adapter;
    In_oder_Adapter in_oder_adapter;
    int status = 0;
    ProgressDialog waitingDialog;

    /*
    * 管理三张表
    * */
    private List<Has_oder_been> has = new ArrayList<>();
    private List<In_oder_been> in = new ArrayList<>();
    private List<To_oder_been> to = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_oders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("我的预约");
        initDB();
        init();

        listener();
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
    private void listener(){
        hasdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 2;
                hasdone.setBackgroundResource(R.color.white);
                todo.setBackgroundResource(R.color.gray_light);
                indoing.setBackgroundResource(R.color.gray_light);
                lv_oder.setAdapter(has_oder_adapter);
            }
        });

        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 0;
                hasdone.setBackgroundResource(R.color.gray_light);
                todo.setBackgroundResource(R.color.white);
                indoing.setBackgroundResource(R.color.gray_light);
                lv_oder.setAdapter(to_oder_adapter);
            }
        });

        indoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = 1;
                hasdone.setBackgroundResource(R.color.gray_light);
                todo.setBackgroundResource(R.color.gray_light);
                indoing.setBackgroundResource(R.color.white);

                lv_oder.setAdapter(in_oder_adapter);
            }
        });

        lv_oder.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (status){
                    case 0:

                        final To_oder_been t = to.get(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(AllOders.this);
                        builder.setTitle("确定?");
                        builder.setMessage("您确定要删除这个预约?");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delete(t.getUID());
                                refresh();
                            }
                        });
                        builder.show();
                        break;
                    case 1:

                        final In_oder_been ins = in.get(position);
                        AlertDialog.Builder builders = new AlertDialog.Builder(AllOders.this);
                        builders.setTitle("确定?");
                        builders.setMessage("您确定要删除这个预约?");
                        builders.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builders.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delete(ins.getUID());
                                refresh();
                            }
                        });
                        builders.show();
                        //delete(i.getUID());
                        break;
                    case 2:

                        final Has_oder_been h = has.get(position);
                        AlertDialog.Builder buildere = new AlertDialog.Builder(AllOders.this);
                        buildere.setTitle("确定?");
                        buildere.setMessage("您确定要删除这个预约?");
                        buildere.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        buildere.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delete(h.getUID());
                                refresh();
                            }
                        });
                        buildere.show();
                        break;
                }


                return true;
            }
        });

        lv_oder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (status){
                    case 0:
                        To_oder_been t = to.get(position);
                        Intent intent = new Intent(getApplicationContext(), Oders.class);
                        intent.putExtra(Oders.CONTEXT,t.getTitle());
                        intent.putExtra(Oders.UID,t.getUID());
                        intent.putExtra(Oders.STATU,0);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        In_oder_been i = in.get(position);
                        Intent intent1 = new Intent(getApplicationContext(), Oders.class);
                        intent1.putExtra(Oders.CONTEXT,i.getTitle());
                        intent1.putExtra(Oders.UID,i.getUID());
                        intent1.putExtra(Oders.STATU,1);
                        startActivity(intent1);
                        finish();
                        break;
                    case 2:
                        Has_oder_been h = has.get(position);
                        Intent intent2 = new Intent(getApplicationContext(), Oders.class);
                        intent2.putExtra(Oders.CONTEXT,h.getTitle());
                        intent2.putExtra(Oders.UID,h.getUID());
                        intent2.putExtra(Oders.STATU,2);
                        startActivity(intent2);
                        finish();
                        break;
                }
            }
        });



    }
    private void init(){
        lv_oder = findViewById(R.id.oder_lv);
        fresh = findViewById(R.id.fresh_oder);
        todo = findViewById(R.id.o_to);
        hasdone = findViewById(R.id.o_has);
        indoing = findViewById(R.id.o_in);
        queryall();
        to_oder_adapter = new To_oder_Adapter(getApplicationContext(),R.layout.has_oder_items,to);
        in_oder_adapter = new In_oder_Adapter(getApplicationContext(),R.layout.has_oder_items,in);
        has_oder_adapter = new Has_oder_Adapter(getApplicationContext(),R.layout.has_oder_items,has);
        lv_oder.setAdapter(to_oder_adapter);
        Log.d(TAG, "init: setting adapter");
        fresh.setColorSchemeResources(R.color.colorPrimary);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                 refresh();
            }
        });
    }
    private void initDB(){
        localDB = new LocalDB(getApplicationContext(),"service.db",null,2);
        localDB.getWritableDatabase();
    }
    private void refresh(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                has.clear();
                to.clear();
                in.clear();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        queryall();
                        to_oder_adapter.notifyDataSetChanged();
                        has_oder_adapter.notifyDataSetChanged();
                        in_oder_adapter.notifyDataSetChanged();
                        fresh.setRefreshing(false);
                    }
                });

            }
        }).start();
    }
    private void queryall(){
        waitingDialog = new ProgressDialog(AllOders.this);
        waitingDialog.setTitle("正在加载数据");
        waitingDialog.setMessage("Loading...");
        waitingDialog.setIndeterminate(true);
        waitingDialog.setCancelable(false);
        waitingDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = localDB.getWritableDatabase();
                Log.d(TAG, "queryall: 开始查询");
                // 查询Book表中所有的数据
                Cursor cursor = db.query("Service", null, "name = ?", new String[]{Nowusers.getName()}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(cursor.getColumnIndex("context"));
                        String start_time = cursor.getString(cursor.getColumnIndex("start_time"));
                        String data = cursor.getString(cursor.getColumnIndex("data"));
                        String uid = cursor.getString(cursor.getColumnIndex("codeID"));
                        int status = cursor.getInt(cursor.getColumnIndex("status"));
                        Log.d(TAG, "queryall: thetitles"+title);

                        if (status==0){
                            To_oder_been to_oder_been = new To_oder_been(title,data+" "+start_time,uid);
                            to.add(to_oder_been);
                        }
                        else if (status==1){
                            In_oder_been in_oder_been = new In_oder_been(title,data+" "+start_time,uid);
                            in.add(in_oder_been);
                        }
                        else if (status==2){
                            Has_oder_been has_oder_been = new Has_oder_been(title,data+" "+start_time,uid);
                            has.add(has_oder_been);
                        }

                    } while (cursor.moveToNext());
                }
                else  ;
                cursor.close();

            }

        }).start();
        waitingDialog.cancel();

    }

    private void delete(String uid){
        SQLiteDatabase db = localDB.getWritableDatabase();
        db.delete("Service", "codeID = ?", new String[] { uid });
    }

}
