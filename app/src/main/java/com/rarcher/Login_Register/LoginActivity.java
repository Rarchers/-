package com.rarcher.Login_Register;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rarcher.Acticitys.MainActivity;
import com.rarcher.DataBase.LocalDB;
import com.rarcher.DataBase.Nowusers;
import com.rarcher.DataBase.Users;
import com.rarcher.R;

import java.io.ByteArrayInputStream;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, ViewTreeObserver.OnGlobalLayoutListener, TextWatcher {

    /*
    * 控件
    * */

    CheckBox markme;

    private ImageButton mIbNavigationBack;
    private EditText mEtLoginUsername;
    private EditText mEtLoginPwd;
    private LinearLayout mLlLoginUsername;
    private ImageView mIvLoginUsernameDel;
    private Button mBtLoginSubmit;
    private LinearLayout mLlLoginPwd;
    private ImageView mIvLoginPwdDel;
    private ImageView mIvLoginLogo;
    private LinearLayout mLayBackBar;
    private TextView mTvLoginForgetPwd;
    private Button mBtLoginRegister;

    //全局Toast
    private Toast mToast;

    private int mLogoHeight;
    private int mLogoWidth;

    String RU,RP;

    String TAG = "Login";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    //登陆对话框
    ProgressDialog progressDialog;
    /*
    * DataBase
    * */
    LocalDB dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title();
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        RU = intent.getStringExtra("un");
        RP = intent.getStringExtra("pa");
        Log.d(TAG, "onCreate:   RU"+RU);
        Log.d(TAG, "onCreate:   RP"+RP);

        //注册dialog
        progressDialog = new ProgressDialog(LoginActivity.this);

        markme = findViewById(R.id.markme);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        initView();

        String username = mEtLoginUsername.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();
        //登录按钮是否可用
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
            mBtLoginSubmit.setClickable(true);
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.black));
        } else {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            mBtLoginSubmit.setClickable(false);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
        //数据库初始化

        dbhelper = new LocalDB(LoginActivity.this, "User.db", null, 2);
        dbhelper.getWritableDatabase();


    }


    //初始化视图
    private void initView() {


        //导航栏+返回按钮
        mLayBackBar = findViewById(R.id.ly_retrieve_bar);
        mIbNavigationBack = findViewById(R.id.ib_navigation_back);

        //logo
        mIvLoginLogo = findViewById(R.id.iv_login_logo);

        //username
        mLlLoginUsername = findViewById(R.id.ll_login_username);
        mEtLoginUsername = findViewById(R.id.et_login_username);
        mIvLoginUsernameDel = findViewById(R.id.iv_login_username_del);

        //passwd
        mLlLoginPwd = findViewById(R.id.ll_login_pwd);
        mEtLoginPwd = findViewById(R.id.et_login_pwd);
        mIvLoginPwdDel = findViewById(R.id.iv_login_pwd_del);

        //提交、注册
        mBtLoginSubmit = findViewById(R.id.bt_login_submit);
        mBtLoginRegister = findViewById(R.id.bt_login_register);

        //忘记密码
        mTvLoginForgetPwd = findViewById(R.id.tv_login_forget_pwd);
        mTvLoginForgetPwd.setOnClickListener(this);

        boolean isRemember = preferences.getBoolean("remember", false);

        //检查是否记住密码
        if (isRemember&&RU==null&&RP==null) {
            String account = preferences.getString("account", "");
            String pass = preferences.getString("password", "");
            mEtLoginUsername.setText(account);
            mEtLoginPwd.setText(pass);
            markme.setChecked(true);
        }
        else if (RU!=null&&RP!=null)
        {
            mEtLoginUsername.setText(RU);
            mEtLoginPwd.setText(RP);
        }

        mIbNavigationBack.setVisibility(View.INVISIBLE);
        mIbNavigationBack.setClickable(false);

        //注册点击事件
        mIbNavigationBack.setOnClickListener(this);
        mEtLoginUsername.setOnClickListener(this);
        mIvLoginUsernameDel.setOnClickListener(this);
        mBtLoginSubmit.setOnClickListener(this);
        mBtLoginRegister.setOnClickListener(this);
        mEtLoginPwd.setOnClickListener(this);
        mIvLoginPwdDel.setOnClickListener(this);


        //注册其它事件
        mLayBackBar.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mEtLoginUsername.setOnFocusChangeListener(this);
        mEtLoginUsername.addTextChangedListener(this);
        mEtLoginPwd.setOnFocusChangeListener(this);
        mEtLoginPwd.addTextChangedListener(this);


    }

    //融合状态栏
    private void title() {
        if (Build.VERSION.SDK_INT >= 21)

        {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }



    //显示loading对话框
    private void showloading() {
        progressDialog.setTitle("登陆中");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    //关闭loading
    private void closeloading() {
        progressDialog.cancel();
    }

    //云端数据库查到资料后放进本地数据库
    private void addtolocal(String phone, String password, String name, String sex) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("password", password);
        values.put("name", name);
        values.put("sex", sex);
        db.insert("Book", null, values);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                //返回
                //finish();
                break;
            case R.id.et_login_username:
                mEtLoginPwd.clearFocus();
                mEtLoginUsername.setFocusableInTouchMode(true);
                mEtLoginUsername.requestFocus();
                break;
            case R.id.et_login_pwd:
                mEtLoginUsername.clearFocus();
                mEtLoginPwd.setFocusableInTouchMode(true);
                mEtLoginPwd.requestFocus();
                break;
            case R.id.iv_login_username_del:
                //清空用户名
                mEtLoginUsername.setText(null);
                break;
            case R.id.iv_login_pwd_del:
                //清空密码
                mEtLoginPwd.setText(null);
                break;
            case R.id.bt_login_submit:
                //登录
                loginRequest();
                break;
            case R.id.bt_login_register:
                //注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
            case R.id.tv_login_forget_pwd:
                //忘记密码
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                break;
            default:
                break;
        }
    }

    //用户名密码焦点改变
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();

        if (id == R.id.et_login_username) {
            if (hasFocus) {
                mLlLoginUsername.setActivated(true);
                mLlLoginPwd.setActivated(false);
            }
        } else {
            if (hasFocus) {
                mLlLoginPwd.setActivated(true);
                mLlLoginUsername.setActivated(false);
            }
        }
    }


    //显示或隐藏logo
    @Override
    public void onGlobalLayout() {
        final ImageView ivLogo = this.mIvLoginLogo;
        Rect KeypadRect = new Rect();

        mLayBackBar.getWindowVisibleDisplayFrame(KeypadRect);

        int screenHeight = mLayBackBar.getRootView().getHeight();
        int keypadHeight = screenHeight - KeypadRect.bottom;

        //隐藏logo
        if (keypadHeight > 300 && ivLogo.getTag() == null) {
            final int height = ivLogo.getHeight();
            final int width = ivLogo.getWidth();
            this.mLogoHeight = height;
            this.mLogoWidth = width;

            ivLogo.setTag(true);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
        //显示logo
        else if (keypadHeight < 300 && ivLogo.getTag() != null) {
            final int height = mLogoHeight;
            final int width = mLogoWidth;

            ivLogo.setTag(null);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(400).setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    ViewGroup.LayoutParams layoutParams = ivLogo.getLayoutParams();
                    layoutParams.height = (int) (height * animatedValue);
                    layoutParams.width = (int) (width * animatedValue);
                    ivLogo.requestLayout();
                    ivLogo.setAlpha(animatedValue);
                }
            });

            if (valueAnimator.isRunning()) {
                valueAnimator.cancel();
            }
            valueAnimator.start();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    //用户名密码输入事件
    @Override
    public void afterTextChanged(Editable s) {
        String username = mEtLoginUsername.getText().toString().trim();
        String pwd = mEtLoginPwd.getText().toString().trim();

        Log.d(TAG, "afterTextChanged: change");
        //是否显示清除按钮
        if (username.length() > 0) {
            mIvLoginUsernameDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginUsernameDel.setVisibility(View.INVISIBLE);
        }
        if (pwd.length() > 0) {
            mIvLoginPwdDel.setVisibility(View.VISIBLE);
        } else {
            mIvLoginPwdDel.setVisibility(View.INVISIBLE);
        }

        //登录按钮是否可用
        if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(username)) {
            mBtLoginSubmit.setClickable(true);
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.black));
        } else {
            mBtLoginSubmit.setBackgroundResource(R.drawable.bg_login_submit_lock);
            mBtLoginSubmit.setClickable(false);
            mBtLoginSubmit.setTextColor(getResources().getColor(R.color.account_lock_font_color));
        }
    }

    //登录
    private void loginRequest() {
        showloading();
        Toast.makeText(LoginActivity.this, "showloading", Toast.LENGTH_SHORT).show();
        String account = mEtLoginUsername.getText().toString();
        String pass = mEtLoginPwd.getText().toString();

        if (account == "" || pass == "") {
            closeloading();
            Toast.makeText(LoginActivity.this, "请输入用户名或者密码", Toast.LENGTH_SHORT).show();
        }
        Users logins = LocalDB.query_user(account,dbhelper);
        //如果本地数据库有记录,就直接放行,不去云端数据库搞事情了
        if (logins != null && pass.equals(logins.getPassword())) {
            editor = preferences.edit();
            if (markme.isChecked()) {//记住密码是否生效
                editor.putBoolean("remember", true);
                editor.putString("account", account);
                editor.putString("password", pass);
                LocalDB.updata_user(account, pass,dbhelper);
            } else editor.clear();

            editor.apply();

            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            closeloading();
            Nowusers.setPhone(account);
            Nowusers.setName(logins.getName());
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("user", account);
            startActivity(intent);
            finish();


        } else {//本地数据库为空或者密码匹配不上
            //请求云端服务器拉取信息
            Toast.makeText(LoginActivity.this, "从云端数据库获取信息或者登陆失败", Toast.LENGTH_SHORT).show();


        }
    }


    //退出时的时间
    private long mExitTime;
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    //退出方法
    private void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(LoginActivity.this, "再按1一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            //用户退出处理
            finish();
            System.exit(0);
        }
    }

}
