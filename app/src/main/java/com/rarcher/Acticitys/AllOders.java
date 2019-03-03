package com.rarcher.Acticitys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.rarcher.Adapter.Has_oder_been;
import com.rarcher.Adapter.In_oder_been;
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
        initDB();
        init();

    }

    private void init(){
        lv_oder = findViewById(R.id.oder_lv);
        fresh = findViewById(R.id.fresh_oder);
        fresh.setColorSchemeResources(R.color.colorPrimary);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // refresh(); TODO::
            }
        });
    }
    private void initDB(){
        localDB = new LocalDB(getApplicationContext(),"service.db",null,2);
        localDB.getWritableDatabase();
    }
  /*  private void refresh(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                list.clear();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        queryall();
                        adapter.notifyDataSetChanged();
                        fresh.setRefreshing(false);
                    }
                });

            }
        }).start();
    }*/


    private void queryall(){
        SQLiteDatabase db = localDB.getWritableDatabase();
        Log.d(TAG, "queryall: 开始查询");
        // 查询Book表中所有的数据
        Cursor cursor = db.query("Service", null, "name = ?", new String[]{LocalDB.query_user(Nowusers.getPhone(),localDB).getName()}, null, null, null);
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



}
