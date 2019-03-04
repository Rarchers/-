package com.rarcher.Acticitys;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.rarcher.Fragments.Accident;
import com.rarcher.Fragments.Convience;
import com.rarcher.Fragments.Home;
import com.rarcher.Fragments.Me;
import com.rarcher.Fragments.Online;
import com.rarcher.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    // 5个tab布局
    private RelativeLayout homeLayout, onlineLayout, meLayout, convenienceLayout, accidentLayout;
    // 底部标签切换的Fragment
    private Fragment homeFragment,onlineFragment, meFragment, convenienceFragment, accidentFragment, currentFragment;

    // 底部标签图片
    private ImageView homeImg, onlineImg, meImg, convienceImg, accidentImg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initUI();
        initTab();

    }

    /**
     * 初始化UI
     */
    private void initUI() {
        homeLayout = (RelativeLayout) findViewById(R.id.rl_home);
        onlineLayout = (RelativeLayout) findViewById(R.id.rl_online);
        meLayout = (RelativeLayout) findViewById(R.id.rl_me);
        convenienceLayout = (RelativeLayout) findViewById(R.id.rl_convenience);
        accidentLayout = (RelativeLayout) findViewById(R.id.rl_accident);

        homeLayout.setOnClickListener(this);
        onlineLayout.setOnClickListener(this);
        meLayout.setOnClickListener(this);
        convenienceLayout.setOnClickListener(this);
        accidentLayout.setOnClickListener(this);

        homeImg = (ImageView) findViewById(R.id.iv_home);
        onlineImg = (ImageView) findViewById(R.id.iv_i_online);
        meImg = (ImageView) findViewById(R.id.iv_me);
        convienceImg = (ImageView) findViewById(R.id.iv_convenience);
        accidentImg = (ImageView) findViewById(R.id.iv_accident);
        accidentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + 10086));//跳转到拨号界面，同时传递电话号码
                startActivity(intent);
                return true;
            }
        });

    }

    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (homeFragment == null) {
            homeFragment = new Home();
        }
        if (!homeFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layout, homeFragment).commit();
            // 记录当前Fragment
            currentFragment = homeFragment;
            // 设置图片文本的变化
            homeImg.setImageResource(R.mipmap.home_3_glyph_24);
            onlineImg.setImageResource(R.mipmap.calendar_2_outline_24);
            meImg.setImageResource(R.mipmap.single_01_outline_24);
            convienceImg.setImageResource(R.mipmap.handout_outline_32);
            accidentImg.setImageResource(R.mipmap.phone_outline_24);
        }

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_home: // 首页
                clickTab1Layout();
                break;
            case R.id.rl_online: // 线上
                clickTab2Layout();
                break;
            case R.id.rl_accident: // 紧急
                clickTab4Layout();
                break;
            case R.id.rl_convenience: // 便利
                clickTab3Layout();
                break;
            case R.id.rl_me: // 我的
                clickTab5Layout();
                break;
            default:
                break;
        }

    }

    /**
     * 点击第一个tab
     */
    private void clickTab1Layout() {
        if (homeFragment == null) {
            homeFragment = new Home();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), homeFragment);

        // 设置底部tab变化
        homeImg.setImageResource(R.mipmap.home_3_glyph_24);
        onlineImg.setImageResource(R.mipmap.calendar_2_outline_24);
        meImg.setImageResource(R.mipmap.single_01_outline_24);
        convienceImg.setImageResource(R.mipmap.handout_outline_32);
        accidentImg.setImageResource(R.mipmap.phone_outline_24);
    }
    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        if (onlineFragment == null) {
            onlineFragment = new Online();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), onlineFragment);

        homeImg.setImageResource(R.mipmap.home_3_outline_24);
        onlineImg.setImageResource(R.mipmap.calendar_2_glyph_24);
        meImg.setImageResource(R.mipmap.single_01_outline_24);
        convienceImg.setImageResource(R.mipmap.handout_outline_32);
        accidentImg.setImageResource(R.mipmap.phone_outline_24);

    }

    /**
     * 点击第三个tab
     */
    private void clickTab3Layout() {
        if (convenienceFragment == null) {
            convenienceFragment = new Convience();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), convenienceFragment);

        homeImg.setImageResource(R.mipmap.home_3_outline_24);
        onlineImg.setImageResource(R.mipmap.calendar_2_outline_24);
        meImg.setImageResource(R.mipmap.single_01_outline_24);
        convienceImg.setImageResource(R.mipmap.handout_glyph_32);
        accidentImg.setImageResource(R.mipmap.phone_outline_24);

    }

    /**
     * 点击第三个tab
     */
    private void clickTab4Layout() {
        if (accidentFragment == null) {
            accidentFragment = new Accident();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), accidentFragment);

        homeImg.setImageResource(R.mipmap.home_3_outline_24);
        onlineImg.setImageResource(R.mipmap.calendar_2_outline_24);
        meImg.setImageResource(R.mipmap.single_01_outline_24);
        convienceImg.setImageResource(R.mipmap.handout_outline_32);
        accidentImg.setImageResource(R.mipmap.phone_glyph_24);

    }

    /**
     * 点击第三个tab
     */
    private void clickTab5Layout() {
        if (meFragment == null) {
            meFragment = new Me();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), meFragment);

        homeImg.setImageResource(R.mipmap.home_3_outline_24);
        onlineImg.setImageResource(R.mipmap.calendar_2_outline_24);
        meImg.setImageResource(R.mipmap.single_01_glyph_24);
        convienceImg.setImageResource(R.mipmap.handout_outline_32);
        accidentImg.setImageResource(R.mipmap.phone_outline_24);

    }



    /**
     * 加入或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) { // 假设当前fragment未被加入，则加入到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
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
            Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            //用户退出处理
            finish();
            System.exit(0);
        }
    }


}
