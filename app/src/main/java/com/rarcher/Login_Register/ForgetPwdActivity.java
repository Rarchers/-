package com.rarcher.Login_Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rarcher.R;


public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener ,TextWatcher {

    EditText et_retrieve_tel,et_retrieve_code_input;
    Button bt_retrieve_submit;
    TextView textView;
    String intents="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        init();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.retrieve_sms_call:
                Toast.makeText(getApplicationContext(),"你的验证码为0714",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_retrieve_submit:
                reset();
                break;
        }
    }

    private void init(){
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        et_retrieve_tel = findViewById(R.id.et_retrieve_tel);
        bt_retrieve_submit = findViewById(R.id.bt_retrieve_submit);
        textView = findViewById(R.id.retrieve_sms_call);
        et_retrieve_code_input = findViewById(R.id.et_retrieve_code_input);
        bt_retrieve_submit.setOnClickListener(this);
        textView.setOnClickListener(this);
        Intent intent = getIntent();
        intents = intent.getStringExtra("setting");

        et_retrieve_tel.addTextChangedListener(this);
        et_retrieve_code_input.addTextChangedListener(this);


        String code = et_retrieve_code_input.getText().toString().trim();
        String tel = et_retrieve_tel.getText().toString().trim();
        //登录按钮是否可用
        if (!TextUtils.isEmpty(tel) && !TextUtils.isEmpty(code)) {
            bt_retrieve_submit.setClickable(true);
            bt_retrieve_submit.setBackgroundResource(R.drawable.bg_login_submit);
            bt_retrieve_submit.setTextColor(getResources().getColor(R.color.white));
        } else {
            bt_retrieve_submit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            bt_retrieve_submit.setClickable(false);
            bt_retrieve_submit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    private void reset(){
        String code = et_retrieve_code_input.getText().toString().trim();
        String tel = et_retrieve_tel.getText().toString().trim();

        if (code.equals("0714")){
            Intent intent = new Intent(getApplicationContext(),ForgetPwd_two.class);
            intent.putExtra("phone",tel);
            startActivity(intent);
            finish();
        }
        else Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String code = et_retrieve_code_input.getText().toString().trim();
        String tel = et_retrieve_tel.getText().toString().trim();
        //登录按钮是否可用
        if (!TextUtils.isEmpty(tel) && !TextUtils.isEmpty(code)) {
            bt_retrieve_submit.setClickable(true);
            bt_retrieve_submit.setBackgroundResource(R.drawable.bg_login_submit);
            bt_retrieve_submit.setTextColor(getResources().getColor(R.color.white));
        } else {
            bt_retrieve_submit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            bt_retrieve_submit.setClickable(false);
            bt_retrieve_submit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (intents.equals("setting")){
                //TODO:
             /*   Intent intent = new Intent(getApplicationContext(),Setting_ME.class);
                startActivity(intent);
                finish();*/
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
