package com.rarcher.Acticitys;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rarcher.DataBase.LocalDB;
import com.rarcher.DataBase.Nowusers;
import com.rarcher.R;

import java.util.UUID;

public class Adding extends AppCompatActivity implements TextWatcher {


    private CardView save;
    private EditText addingtitle,addingcontext;
    private LocalDB localDB;
    String title;
    String context;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle("添加备忘录");
        initDB();
        init();

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
                Intent intent = new Intent(Adding.this,SafeNote.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void init(){
        save = findViewById(R.id.addingsave);
        addingcontext = findViewById(R.id.addingcontext);
        addingtitle = findViewById(R.id.addingtitle);
        addingcontext.addTextChangedListener(this);
        addingtitle.addTextChangedListener(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()){
                    uid = UUID.randomUUID().toString();//当前小票的唯一编号
                    LocalDB.addData_note(Nowusers.getName(),context,title,uid,localDB);
                    Intent intent = new Intent(Adding.this,SafeNote.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        title = addingtitle.getText().toString().trim();
        context = addingcontext.getText().toString().trim();
    }


    private boolean check(){
        if ((title!=null&&context!=null)&&(!title.equals("")&&!context.equals(""))){
            return true;
        }
        else
            Toast.makeText(getApplicationContext(),"还有必填项没填",Toast.LENGTH_SHORT).show();
        return false;
    }


}
