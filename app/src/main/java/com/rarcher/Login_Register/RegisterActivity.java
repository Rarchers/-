package com.rarcher.Login_Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rarcher.R;
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    EditText phones;
    EditText code;
    TextView get;
    CheckBox read;
    Button regist;
    LinearLayout ll_register_phone, ll_register_sms_code;
    String TAG = "R";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        String username = phones.getText().toString().trim();
        String pwd = code.getText().toString().trim();
        //登录按钮是否可用
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
            regist.setClickable(true);
            regist.setBackgroundResource(R.drawable.bg_login_submit);
            regist.setTextColor(getResources().getColor(R.color.white));
        } else {
            regist.setBackgroundResource(R.drawable.bg_login_submit_lock);
            regist.setClickable(false);
            regist.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);

        read.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    String phone = phones.getText().toString().trim();
                    String codes = code.getText().toString().trim();
                    if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(codes)) {
                        regist.setClickable(true);
                        regist.setBackgroundResource(R.drawable.bg_login_submit);
                        regist.setTextColor(getResources().getColor(R.color.white));
                    } else {
                        regist.setClickable(false);
                        regist.setBackgroundResource(R.drawable.bg_login_submit_lock);
                        regist.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.tv_register_sms_call:
                String pn = phones.getText().toString();
                if (pn.equals(""))
                    Toast.makeText(getApplicationContext(), "请输入你的手机号来获取验证码", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "您的验证码为1121", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_register_submit:
                String pns = phones.getText().toString();
                if (!pns.equals("") && code.getText().toString().equals("1121")) {
                    Intent intent = new Intent(RegisterActivity.this, Register_two.class);
                    intent.putExtra("phones", pns);
                    startActivity(intent);
                    finish();
                } else Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void init() {
        phones = findViewById(R.id.et_register_username);
        code = findViewById(R.id.et_register_auth_code);
        get = findViewById(R.id.tv_register_sms_call);
        read = findViewById(R.id.cb_protocol);
        regist = findViewById(R.id.bt_register_submit);
        ll_register_phone = findViewById(R.id.ll_register_phone);
        ll_register_sms_code = findViewById(R.id.ll_register_sms_code);

        regist.setOnClickListener(this);
        get.setOnClickListener(this);
        phones.addTextChangedListener(this);
        code.addTextChangedListener(this);

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String phone = phones.getText().toString().trim();
        String codes = code.getText().toString().trim();

        Log.d(TAG, "afterTextChanged: 检查是否开放注册");
        //注册按钮是否可用
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(codes) && read.isChecked()) {
            regist.setClickable(true);
            regist.setBackgroundResource(R.drawable.bg_login_submit);
            regist.setTextColor(getResources().getColor(R.color.white));
        } else {
            regist.setClickable(false);
            regist.setBackgroundResource(R.drawable.bg_login_submit_lock);
            regist.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    //阅读服务条款事件
    @Override
    public void afterTextChanged(Editable s) {


    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
