package com.rarcher.Login_Register;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rarcher.DataBase.LocalDB;
import com.rarcher.R;


public class ForgetPwd_two extends AppCompatActivity implements View.OnClickListener ,TextWatcher {

    EditText et_reset_pwd;
    Button git;
    LocalDB dbhelper;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd_two);
        init();
    }


    private void init(){
        et_reset_pwd = findViewById(R.id.et_reset_pwd);
        git = findViewById(R.id.bt_reset_submit);
        git.setOnClickListener(this);
        dbhelper = new LocalDB(ForgetPwd_two.this, "User.db", null, 2);
        dbhelper.getWritableDatabase();
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        et_reset_pwd.addTextChangedListener(this);

        check();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        check();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_reset_submit:
                Reset_pwd();
                break;
        }

    }

    private void Reset_pwd(){
        String new_pwd = et_reset_pwd.getText().toString().trim();

        if (TextUtils.isEmpty(new_pwd)) {
            Toast.makeText(getApplicationContext(), "名字不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!TextUtils.isEmpty(new_pwd)) {
            if (new_pwd.length() < 6) {
                Toast.makeText(getApplicationContext(), "请输入至少六位新密码", Toast.LENGTH_SHORT).show();
                return;
            } else {
                //TODO::去网络服务器上更新数据
                updata(phone,new_pwd);
                Toast.makeText(getApplicationContext(),"修改密码成功,新密码为\n"+new_pwd,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgetPwd_two.this,LoginActivity.class);
                intent.putExtra("un",phone);
                intent.putExtra("pa",new_pwd);
                startActivity(intent);
                finish();
            }
        }


    }

    private void check(){
        String new_pwd = et_reset_pwd.getText().toString().trim();
        //登录按钮是否可用
        if (!TextUtils.isEmpty(new_pwd)) {
            git.setClickable(true);
            git.setBackgroundResource(R.drawable.bg_login_submit);
            git.setTextColor(getResources().getColor(R.color.white));
        } else {
            git.setBackgroundResource(R.drawable.bg_login_submit_lock);
            git.setClickable(false);
            git.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    //更新当前用户的密码
    private void updata(String phone, String newpass) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newpass);
        db.update("Book", values, "phone = ?", new String[]{phone});
    }
}
