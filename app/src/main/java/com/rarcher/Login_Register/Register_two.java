package com.rarcher.Login_Register;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rarcher.DataBase.LocalDB;
import com.rarcher.R;

import java.io.ByteArrayOutputStream;


public class Register_two extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    String phonenumber;
    /*
   * DataBase
   * */
    LocalDB dbhelper;
    Bitmap bmp;
    String TAG = "RT";
    EditText name;
    EditText pwd;
    EditText identity_code;
    TextView man, woman;
    Button register;
    String sex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);
        Drawable drawable = getApplication().getResources().getDrawable(R.drawable.nav_icon);
        bmp = Bitmap.createBitmap(drawableToBitmap(drawable));
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        init();
        //数据库初始化
        dbhelper = new LocalDB(Register_two.this, "User.db", null, 2);
        dbhelper.getWritableDatabase();
        Intent intent = getIntent();
        phonenumber = intent.getStringExtra("phones");
        Log.d(TAG, "onCreate: number" + phonenumber);

    }


    private void init() {
        identity_code = findViewById(R.id.et_register_identity_input);
        name = findViewById(R.id.et_register_username);
        pwd = findViewById(R.id.et_register_pwd_input);
        man = findViewById(R.id.tv_register_man);
        woman = findViewById(R.id.tv_register_female);
        register = findViewById(R.id.bt_register_submit);

        register.setOnClickListener(this);
        man.setOnClickListener(this);
        woman.setOnClickListener(this);

        name.addTextChangedListener(this);
        pwd.addTextChangedListener(this);
        identity_code.addTextChangedListener(this);

        String phone = name.getText().toString().trim();
        String codes = pwd.getText().toString().trim();
        String incode = identity_code.getText().toString().trim();

        Log.d(TAG, "afterTextChanged: 检查是否开放注册");
        //注册按钮是否可用
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(codes)&& !TextUtils.isEmpty(incode) && !TextUtils.isEmpty(sex)) {
            register.setClickable(true);
            register.setBackgroundResource(R.drawable.bg_login_submit);
            register.setTextColor(getResources().getColor(R.color.white));
        } else {
            register.setClickable(false);
            register.setBackgroundResource(R.drawable.bg_login_submit_lock);
            register.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String phone = name.getText().toString().trim();
        String codes = pwd.getText().toString().trim();
        String incodes = identity_code.getText().toString().trim();
        Log.d(TAG, "afterTextChanged: 检查是否开放注册");
        //注册按钮是否可用
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(codes)&& !TextUtils.isEmpty(incodes) && !TextUtils.isEmpty(sex)) {
            register.setClickable(true);
            register.setBackgroundResource(R.drawable.bg_login_submit);
            register.setTextColor(getResources().getColor(R.color.white));
        } else {
            register.setClickable(false);
            register.setBackgroundResource(R.drawable.bg_login_submit_lock);
            register.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_man:
                sex = "male";
                Toast.makeText(getApplicationContext(), "男", Toast.LENGTH_SHORT).show();
                String phone = name.getText().toString().trim();
                String codes = pwd.getText().toString().trim();
                String incodes = identity_code.getText().toString().trim();

                Log.d(TAG, "afterTextChanged: 检查是否开放注册");
                //注册按钮是否可用
                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(codes)&& !TextUtils.isEmpty(incodes) && !TextUtils.isEmpty(sex)) {
                    register.setClickable(true);
                    register.setBackgroundResource(R.drawable.bg_login_submit);
                    register.setTextColor(getResources().getColor(R.color.white));
                } else {
                    register.setClickable(false);
                    register.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    register.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }

                break;
            case R.id.tv_register_female:
                sex = "female";
                Toast.makeText(getApplicationContext(), "女", Toast.LENGTH_SHORT).show();
                String phones = name.getText().toString().trim();
                String codess = pwd.getText().toString().trim();
                String incodess = identity_code.getText().toString().trim();

                Log.d(TAG, "afterTextChanged: 检查是否开放注册");
                //注册按钮是否可用
                if (!TextUtils.isEmpty(phones) && !TextUtils.isEmpty(codess)&& !TextUtils.isEmpty(incodess) && !TextUtils.isEmpty(sex)) {
                    register.setClickable(true);
                    register.setBackgroundResource(R.drawable.bg_login_submit);
                    register.setTextColor(getResources().getColor(R.color.white));
                } else {
                    register.setClickable(false);
                    register.setBackgroundResource(R.drawable.bg_login_submit_lock);
                    register.setTextColor(getResources().getColor(R.color.account_lock_font_color));
                }
                break;
            case R.id.bt_register_submit:
                regist();
                break;


        }
    }

    private void regist() {
        Log.d(TAG, "regist: 开始注册");
        String uname = name.getText().toString().trim();
        String pass = pwd.getText().toString().trim();
        String incodes = identity_code.getText().toString().trim();

        Log.d(TAG, "regist: name  " + uname);
        Log.d(TAG, "regist: pass  " + pass);
        Log.d(TAG, "regist: 名字判断" + !TextUtils.isEmpty(uname));
        Log.d(TAG, "regist: 密码判断" + !TextUtils.isEmpty(pass));
        if (TextUtils.isEmpty(uname) && !TextUtils.isEmpty(pass)&& !TextUtils.isEmpty(incodes)) {
            Toast.makeText(getApplicationContext(), "名字不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!TextUtils.isEmpty(uname)&& !TextUtils.isEmpty(incodes) && TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else if (!TextUtils.isEmpty(uname)&& TextUtils.isEmpty(incodes) && !TextUtils.isEmpty(pass)){
            Toast.makeText(getApplicationContext(), "请输入身份证号", Toast.LENGTH_SHORT).show();
            return;
        } else if (!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(pass)) {
            if (pass.length() < 6) {
                Toast.makeText(getApplicationContext(), "请输入至少六位新密码", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Log.d(TAG, "regist: 添加");
                LocalDB.insert_user(getApplicationContext(),phonenumber, pass, uname, sex,incodes,bmp,dbhelper);
                Intent intent = new Intent(Register_two.this,LoginActivity.class);
                intent.putExtra("un",phonenumber);
                intent.putExtra("pa",pass);
                startActivity(intent);
                finish();
            }
        }

    }


    static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }
}
