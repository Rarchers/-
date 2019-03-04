package com.rarcher.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.rarcher.Acticitys.AllOders;
import com.rarcher.DataBase.LocalDB;
import com.rarcher.DataBase.Nowusers;
import com.rarcher.R;

import java.util.Calendar;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Online extends Fragment  implements TextWatcher {


    private LocalDB localDB;//DB

    private TextView sev;
    private ListView listView;
    private String[] home = {"上门送餐","集中用餐","上门做餐","上门家务","上门修理","陪同出行","上门聊天","陪同就医","陪同复建","陪同检查","外出游玩"};

    /*
    * 订单初始化
    * */

    String final_time="";
    String final_date="";
    String final_end_time="";
    Calendar calendar = Calendar.getInstance();
    String uuid;
    Boolean postable = false;
    int mHour=99,mMinute=99,endhour=99,endminute=99;
    double mYear=9999999,mDay,mMonth;
    String service=home[0],special_request="";
    private AlertDialog alertDialog2; //单选框
    private LinearLayout choose_data,choose_time,choose_end_time;
    private TextView mservice,mdata,mtime,mend_time;
    private EditText special;
    CardView get;


    public Online() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDB();
        init(view);
        listener(view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                checknull(position);

                Log.d(TAG, "onItemClick: "+position+"      "+id);
            }
        });


    }
    private void initDB(){
        localDB = new LocalDB(getContext(),"service.db",null,2);
        localDB.getWritableDatabase();
    }
    private void init(View view){
        listView = view.findViewById(R.id.lv_menu);
        sev = view.findViewById(R.id.service);
        choose_data = view.findViewById(R.id.choose_data);
        choose_time = view.findViewById(R.id.choose_time);
        choose_end_time = view.findViewById(R.id.choose_end_time);
        get = view.findViewById(R.id.get);
        mservice =view.findViewById(R.id.mservice);
        mdata =view.findViewById(R.id.mdata);
        mtime = view.findViewById(R.id.mtime);
        mend_time =view.findViewById(R.id.mend_time);
        special = view.findViewById(R.id.special);
        special.addTextChangedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,home);
        listView.setAdapter(adapter);
    }


    private void checknull(int id){
        final int x = id;
        if (!TextUtils.isEmpty(final_time) || !TextUtils.isEmpty(final_date)|| !TextUtils.isEmpty(final_end_time)|| !TextUtils.isEmpty(special_request)||mHour!=99||mYear!=9999999||endhour!=99) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            Log.d(TAG, "checknull: final_time  "+final_time);
            Log.d(TAG, "checknull: final_date  "+final_date);
            Log.d(TAG, "checknull: final_end_time  "+final_end_time);
            Log.d(TAG, "checknull: special_request  "+special_request);
            Log.d(TAG, "checknull: mHour  "+mHour);
            Log.d(TAG, "checknull: mYear  "+mYear);
            Log.d(TAG, "checknull: endhour  "+endhour);
            builder.setTitle("是否切换?");
            builder.setMessage("是否要放弃当前编辑的订单?");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                     final_time="";
                     final_date="";
                     final_end_time="";
                     postable = false;
                     mHour=99;mMinute=99;endhour=99;endminute=99;
                     mYear=9999999;mDay=0;mMonth=0;
                     special_request="";
                     mservice.setText("");
                     mdata.setText("");
                     mtime.setText("");
                     mend_time.setText("");
                     service=final_date=final_time=final_end_time=special_request=uuid="";
                     special.setText("");
                     sev.setText("服务: "+home[x]);
                     service = home[x];
                }
            });
            builder.show();
        } else {
            sev.setText("服务: "+home[x]);
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
        special_request = special.getText().toString();
    }


    private void listener(View view){
        setDate();
        setendTime();
        setTime();
        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (postable){
                    //TODO::发送数据给云端数据库,告诉社工们
                    uuid = UUID.randomUUID().toString();//当前小票的唯一编号

                    LocalDB.addData_service(Nowusers.getName(),service,final_date,final_time,final_end_time,special_request,uuid,localDB,0);
                    final_time="";
                    final_date="";
                    final_end_time="";
                    postable = false;
                    mHour=99;mMinute=99;endhour=99;endminute=99;
                    mYear=9999999;mDay=0;mMonth=0;
                    special_request="";
                    mservice.setText("");
                    mdata.setText("");
                    mtime.setText("");
                    mend_time.setText("");
                    service=final_date=final_time=final_end_time=special_request=uuid="";
                    special.setText("");
                    new StyleableToast
                            .Builder(getContext())
                            .text("预约成功")
                            .length(1)
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GRAY)
                            .show();
                    Intent intent = new Intent(getContext(), AllOders.class);
                    startActivity(intent);
                }
                else {
                    new StyleableToast
                            .Builder(getContext())
                            .text("有些必填项目没填写完哦")
                            .length(1)
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GRAY)
                            .show();
                }
            }
        });
    }
    public void setTime(){
        //点击"时间"按钮布局 设置时间
        choose_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自定义控件
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = (LinearLayout) getLayoutInflater().inflate(R.layout.time_dialog, null);
                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
                //初始化时间
                calendar.setTimeInMillis(System.currentTimeMillis());
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                //设置time布局
                builder.setView(view);
                builder.setTitle("设置时间信息");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHour = timePicker.getCurrentHour();
                        mMinute = timePicker.getCurrentMinute();
                        //时间小于10的数字 前面补0 如01:12:00
                        mtime.setText(new StringBuilder().append(mHour < 10 ? "0" + mHour : mHour).append(":")
                                .append(mMinute < 10 ? "0" + mMinute : mMinute).append(":00") );
                        final_time = new StringBuilder().append(mHour < 10 ? "0" + mHour : mHour).append(":")
                                .append(mMinute < 10 ? "0" + mMinute : mMinute).append(":00").toString();
                        check();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取 消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }
    public void setendTime(){
        //点击"时间"按钮布局 设置时间
        choose_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //自定义控件
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = (LinearLayout) getLayoutInflater().inflate(R.layout.time_dialog, null);
                final TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
                //初始化时间
                calendar.setTimeInMillis(System.currentTimeMillis());
                timePicker.setIs24HourView(true);
                timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(Calendar.MINUTE);
                //设置time布局
                builder.setView(view);
                builder.setTitle("设置时间信息");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        endhour = timePicker.getCurrentHour();
                        endminute = timePicker.getCurrentMinute();
                        //时间小于10的数字 前面补0 如01:12:00
                        mend_time.setText(new StringBuilder().append(endhour < 10 ? "0" + endhour : endhour).append(":")
                                .append(endminute < 10 ? "0" + endminute : endminute).append(":00") );
                        final_end_time = new StringBuilder().append(mHour < 10 ? "0" + endhour : endhour).append(":")
                                .append(endminute < 10 ? "0" + endminute : endminute).append(":00").toString();
                        check();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取 消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }
    public void setDate(){
        //点击"日期"按钮布局 设置日期
        choose_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过自定义控件AlertDialog实现
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = (LinearLayout) getLayoutInflater().inflate(R.layout.date_dialog, null);
                final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
                //设置日期简略显示 否则详细显示 包括:星期\周
                datePicker.setCalendarViewShown(false);
                //初始化当前日期
                calendar.setTimeInMillis(System.currentTimeMillis());
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH), null);
                //设置date布局
                builder.setView(view);
                builder.setTitle("设置日期信息");
                builder.setPositiveButton("确 定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //日期格式
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        mdata.setText(sb);
                        //赋值后面闹钟使用
                        mYear = datePicker.getYear();
                        mMonth = datePicker.getMonth();
                        mDay = datePicker.getDayOfMonth();
                        final_date = sb.toString();
                        check();
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("取 消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.create().show();
            }
        });
    }
    private void check(){
        if (!TextUtils.isEmpty(final_time) && !TextUtils.isEmpty(final_date)&& !TextUtils.isEmpty(final_end_time)&&mHour!=99&&mYear!=9999999&&endhour!=99) {
            postable =true;
        } else {
            postable =false;
        }
    }



}
