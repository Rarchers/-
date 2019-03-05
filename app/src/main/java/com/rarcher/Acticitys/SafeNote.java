package com.rarcher.Acticitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.rarcher.Adapter.Has_oder_been;
import com.rarcher.Adapter.In_oder_been;
import com.rarcher.Adapter.Safe_Note_Adapter;
import com.rarcher.Adapter.Safe_Note_been;
import com.rarcher.Adapter.To_oder_been;
import com.rarcher.DataBase.LocalDB;
import com.rarcher.DataBase.Nowusers;
import com.rarcher.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SafeNote extends AppCompatActivity {

    TextView adds;
    ListView lv_safenote;
    Safe_Note_Adapter safe_note_adapter;
    private LocalDB localDB;
    ProgressDialog waitingDialog;
    private List<Safe_Note_been> datalist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("安全备忘");
        lv_safenote = findViewById(R.id.lv_safenote);
        initDB();
        queryall();
        safe_note_adapter = new Safe_Note_Adapter(getApplicationContext(),R.layout.safe_listview,datalist);
        lv_safenote.setAdapter(safe_note_adapter);
        adds = findViewById(R.id.add);
        adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SafeNote.this,Adding.class);
                startActivity(intent);
            }
        });


    }

    private void initDB(){
        localDB = new LocalDB(getApplicationContext(),"note.db",null,2);
        localDB.getWritableDatabase();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
            case android.R.id.home:
                Intent intent = new Intent(SafeNote.this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    private void queryall(){
        waitingDialog = new ProgressDialog(SafeNote.this);
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
                Cursor cursor = db.query("Notes", null, "name = ?", new String[]{Nowusers.getName()}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String title = cursor.getString(cursor.getColumnIndex("context"));
                        String context = cursor.getString(cursor.getColumnIndex("start_time"));
                        String uid = cursor.getString(cursor.getColumnIndex("data"));
                        Safe_Note_been safe_note_been = new Safe_Note_been(title,uid,context,Nowusers.getName());
                        datalist.add(safe_note_been);
                    } while (cursor.moveToNext());
                }
                else  ;
                cursor.close();

            }

        }).start();
        waitingDialog.cancel();

    }


}
