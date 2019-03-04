package com.rarcher.Acticitys;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.rarcher.Adapter.Service_info;
import com.rarcher.DataBase.LocalDB;
import com.rarcher.DataBase.Nowusers;
import com.rarcher.R;

import java.io.File;

import static android.content.ContentValues.TAG;

public class Oders extends AppCompatActivity {
    public static final String CONTEXT = "context";
    public static final String UID = "uid";
    public static final String STATU = "statue";
    FloatingActionButton delet;
    int start = 0;
    private LocalDB localDB;
    String info;
    String uid;
    NotificationManager manager;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //重写ToolBar返回按钮的行为，防止重新打开父Activity重走生命周期方法
            case android.R.id.home:
                Intent intent = new Intent(Oders.this,AllOders.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oders);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);//通知管理器
        final Intent intent = getIntent();
        initDB();
        String context = intent.getStringExtra(CONTEXT);
        uid = intent.getStringExtra(UID);
        start = intent.getIntExtra(STATU,0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        TextView fruitContentText = (TextView) findViewById(R.id.odersdetails);
        delet = findViewById(R.id.joinevents);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(context);

        final Service_info infos = queryall(uid);

        if (infos==null){
            new StyleableToast
                    .Builder(getApplicationContext())
                    .text("当前预约不存在,请刷新预约列表")
                    .length(1)
                    .textColor(Color.WHITE)
                    .backgroundColor(Color.GRAY)
                    .show();
            finish();
        }
        else {info ="预约单号: "+infos.getCodeID()+ "\n\n\n服务预约者: "+infos.getName()+"\n\n服务开始日期: "+infos.getData()+" "+infos.getStart_time()+"\n" +
                "\n服务结束时间: "+infos.getEnd_time()+"\n\n服务类型: "+infos.getContext()+"\n\n服务地址: "+infos.getLocation()+"\n\n特殊需求: "+infos.getSpecial_request()
                +"\n\n\n\n"+"义工名字: "+infos.getService_name()+"\n\n义工性别: "+infos.getService_sex()+"\n\n义工年龄: "+infos.getService_age()+"\n\n义工手机号: "+infos.getService_phones()
                +"\n\n义工编号: "+infos.getService_code();
            Log.d(TAG, "onCreate: "+infos.getData());
            fruitContentText.setText(info);}

        final String id = infos.getCodeID();

        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (start==0){
                    delet.setBackgroundResource(R.drawable.ic_backup);
                    delet.setBackground(getDrawable(R.drawable.ic_backup));
                    delet.setBackgroundDrawable(getDrawable(R.drawable.ic_backup));
                    start = 1;
                    updata(infos.getCodeID(),1);
                    new StyleableToast
                            .Builder(getApplicationContext())
                            .text("开始服务")
                            .length(1)
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GRAY)
                            .show();
                    //通知的适配,因为在Android 8.0以上加入了通道概念,不对通道进行修改是无法显示通知的     https://www.jb51.net/article/138411.htm
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {



                        String channelId = "service";
                        String channelName = "服务提醒";
                        int importance = NotificationManager.IMPORTANCE_MAX;
                        createNotificationChannel(channelId, channelName, importance);

                        //确保通知是打开的
                        NotificationChannel channel = manager.getNotificationChannel("service");
                        Log.d(TAG, "onClick: "+channel.getImportance());
                        if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                            intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                            startActivity(intent);
                            new StyleableToast
                                    .Builder(getApplicationContext())
                                    .text("请手动将通知打开")
                                    .length(1)
                                    .textColor(Color.WHITE)
                                    .backgroundColor(Color.GRAY)
                                    .show();
                        }
                    }

                    Intent intent1 = new Intent(getApplication(),Oders.class);
                    intent1.putExtra(CONTEXT,intent.getStringExtra(CONTEXT));
                    intent1.putExtra(UID,uid);
                    PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,intent1,0);
                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), "service")
                            .setContentTitle("您预约的服务正在进行中...")
                            .setContentText("服务单号:"+id)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.drawable.nav_icon)//TODO:应用图标
                            .setSound(Uri.fromFile (new File("/system/media/audio/ringtones/Luna.ogg")))
                            .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.nav_icon))
                            .setVibrate(new long[]{0,1000,1000,1000})
                            .setAutoCancel(false)
                            .setContentIntent(pi)
                            .build();
                    manager.notify(1, notification);

                }
                else if (start == 1){
                    new StyleableToast
                            .Builder(getApplicationContext())
                            .text("结束服务")
                            .length(1)
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GRAY)
                            .show();
                    manager.cancel(1);
                    start = 2;
                    updata(infos.getCodeID(),2);
                    delet.setBackgroundResource(R.drawable.ic_comment);
                    delet.setBackground(getDrawable(R.drawable.ic_comment));
                    delet.setBackgroundDrawable(getDrawable(R.drawable.ic_comment));
                }
                else {
                    delet.setBackgroundResource(R.drawable.ic_comment);
                    delet.setBackground(getDrawable(R.drawable.ic_comment));
                    delet.setBackgroundDrawable(getDrawable(R.drawable.ic_comment));
                    new StyleableToast
                            .Builder(getApplicationContext())
                            .text("评价一下?")
                            .length(1)
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GRAY)
                            .show();
                }


            }
        });

    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{0,1000,1000,1000});

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    private void initDB(){
        localDB = new LocalDB(getApplicationContext(),"service.db",null,2);
        localDB.getWritableDatabase();
    }
    private Service_info queryall(String uid){
        SQLiteDatabase db = localDB.getWritableDatabase();
        Log.d(TAG, "queryall: 开始查询");
        // 查询Book表中所有的数据
        Cursor cursor = db.query("Service", null, "codeID = ?", new String[]{uid}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex("context"));
                String start_time = cursor.getString(cursor.getColumnIndex("start_time"));
                String data = cursor.getString(cursor.getColumnIndex("data"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String end_time = cursor.getString(cursor.getColumnIndex("end_time"));
                String location = cursor.getString(cursor.getColumnIndex("location"));
                String special_request = cursor.getString(cursor.getColumnIndex("special_request"));
                String service_name = cursor.getString(cursor.getColumnIndex("service_name"));
                String service_sex = cursor.getString(cursor.getColumnIndex("service_sex"));
                int service_age = cursor.getInt(cursor.getColumnIndex("service_age"));
                String service_phones = cursor.getString(cursor.getColumnIndex("service_phones"));
                String service_code = cursor.getString(cursor.getColumnIndex("service_code"));
                Service_info info = new Service_info(Nowusers.getName(),data,start_time,end_time,location,special_request,title
                        ,uid,service_name,service_sex,service_code,service_phones,service_age);

                return info;

            } while (cursor.moveToNext());
        }

        cursor.close();
        return null;
    }

    //更新当前状态
    private void updata(String uid, int statu) {
        SQLiteDatabase db = localDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", statu);
        db.update("Service", values, "codeID = ?", new String[]{uid});
    }
    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent(Oders.this,AllOders.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
