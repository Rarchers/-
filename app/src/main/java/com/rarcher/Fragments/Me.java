package com.rarcher.Fragments;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.rarcher.Acticitys.AllOders;
import com.rarcher.Acticitys.Setting_ME;
import com.rarcher.DataBase.LocalDB;
import com.rarcher.DataBase.Nowusers;
import com.rarcher.DataBase.Users;
import com.rarcher.Login_Register.ForgetPwdActivity;
import com.rarcher.Login_Register.ForgetPwd_two;
import com.rarcher.Login_Register.LoginActivity;
import com.rarcher.R;
import com.rarcher.Utils.ACache;
import com.rarcher.Utils.ActivityCollector;
import com.rarcher.Utils.DataCleanManager;
import com.rarcher.Utils.SDCardUtils;
import com.rarcher.Utils.ToastUtils;
import com.rarcher.view.dialog.ConfirmDialogFragment;
import com.rarcher.view.dialog.LoadingDialogFragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Me extends Fragment {
    private DialogHandler dialogHandler;
    private Activity activity;
    private LinearLayout contact,update,setting,tooders;
    RelativeLayout myself;
    private Button offlineDownloadBtn;//离线下载
    private Button clearCacheBtn,quit;//清除缓存
    private TextView cacheSizeTv;//缓存大小
    private TextView tv_nickname,tv_introduction;
    private CircleImageView image;
    LocalDB dbhelper;
    Users user;
    ProgressDialog progressDialog;
    public Me() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_me, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbhelper = new LocalDB(getContext(), "User.db", null, 2);
        dbhelper.getWritableDatabase();
        user = LocalDB.query_user(Nowusers.getPhone(),dbhelper);
        findViews(view);
        setListener();
        initCacheSize();



        image.setImageDrawable(Drawable.createFromStream(user.getImage(), "img"));
    }


    private void findViews(View view) {
        //offlineDownloadBtn = (Button) view.findViewById(R.id.settings_offline_download_btn);
        clearCacheBtn = (Button) view.findViewById(R.id.settings_clear_cache_btn);
        cacheSizeTv = (TextView) view.findViewById(R.id.settings_cache_size_tv);
        tooders = view.findViewById(R.id.to_oders);
        myself = view.findViewById(R.id.myself);
        image = view.findViewById(R.id.im);
        contact = view.findViewById(R.id.contact_me);
        setting = view.findViewById(R.id.setting);
        update = view.findViewById(R.id.check_update);
        quit = view.findViewById(R.id.quit_btn);
        tv_nickname = view.findViewById(R.id.tv_nickname);
        tv_introduction = view.findViewById(R.id.tv_introduction);
        tv_nickname.setText(user.getName());
        tv_introduction.setText(user.getPhone());
    }

    /**
     * 初始化缓存大小并显示
     */
    private void initCacheSize() {
        String WEBVIEW_CACHE_PATH = "/data/data/com.rarcher.Fragments/app_webview/Cache";//WebView的缓存路径

        //内置存储cache
        double internalCacheSize = SDCardUtils.getDirSize(activity.getCacheDir());
        //外置存储cache
        double externalCacheSize = 0;
        if (SDCardUtils.isSDCardEnable())
            externalCacheSize = SDCardUtils.getDirSize(activity.getExternalCacheDir());
        //WebView的cache
        double webViewCacheSize = SDCardUtils.getDirSize(new File(WEBVIEW_CACHE_PATH));
        String totalCacheSize = new DecimalFormat("#.00").format(
                internalCacheSize +
                        externalCacheSize + webViewCacheSize);
        if (totalCacheSize.startsWith(".")) totalCacheSize = "0" + totalCacheSize;
        //若总大小小于0.1MB，直接显示0.00MB
        if (Float.parseFloat(totalCacheSize) < 0.10f) totalCacheSize = "0.00";
        String str = getString(R.string.settings_cache_size, totalCacheSize);

        cacheSizeTv.setText(str);
        Log.d("yslog", "internalCacheSize--->" + internalCacheSize);
        Log.d("yslog", "externalCacheSize--->" + externalCacheSize);
        Log.d("yslog", "webViewCacheSize--->" + webViewCacheSize);
    }
    private void setListener() {
      /*  offlineDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(activity, "开发中~");
            }
        });*/
        tooders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AllOders.class);
                startActivity(intent);
            }
        });
        clearCacheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = getResources().getString(R.string.confirm_dialog_title, "清除");
                ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
                confirmDialogFragment.setParams(title, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialogFragment loadingDialogFragment = new LoadingDialogFragment();
                        loadingDialogFragment.setParams(getResources().getString(R.string.loading_dialog_title));
                        loadingDialogFragment.show(activity.getFragmentManager(), "settings_loading_dialog");

                        clearAppCache();

                        dialogHandler = new DialogHandler(loadingDialogFragment);
                        dialogHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                }, null);
                confirmDialogFragment.show(activity.getFragmentManager(), "settings_confirm_dialog");
            }
        });

        myself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Setting_ME.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.finishAll();
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(getContext(),"本软件由大挑团队开发",Toast.LENGTH_SHORT).show();
                //StyleableToast.makeText(getContext(), "本软件由大挑团队开发", Toast.LENGTH_LONG, R.style.mytoast).show();
                new StyleableToast
                        .Builder(getContext())
                        .text("本软件由大挑团队开发")
                        .length(1)
                        .textColor(Color.WHITE)
                        .backgroundColor(Color.GRAY)
                        .show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"检查更新",Toast.LENGTH_SHORT).show();
                String apkPath=""; // TODO:这里写apk的下载地址
               /* UpdateAppUtils.from(getActivity())
                        .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式， 默认为VersionCode
                        //CHECK_BY_VERSION_CODE:通过versionCode判断，服务器上versionCode > 本地versionCode则执行更新
                        // CHECK_BY_VERSION_NAME：通过versionName判断，服务器上versionName 与 本地versionName不同则更新
                        .serverVersionCode(2)//服务器versionCode
                        .serverVersionName("2.0")//服务器versionName
                        .apkPath(apkPath)//最新apk下载地址
                        .showNotification(true) //是否显示下载进度到通知栏，默认为true
                        // .updateInfo(info)  //更新日志信息 String
                        .downloadBy(UpdateAppUtils.DOWNLOAD_BY_APP) //下载方式：app下载、手机浏览器下载。默认app下载
                        .isForce(false) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
                        .update();*/

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"设置",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 清除app所有的缓存
     */
    private void clearAppCache() {
        ACache.get(activity).clear();
        DataCleanManager.cleanInternalCache(activity);
        DataCleanManager.cleanExternalCache(activity);
        WebView webView = new WebView(activity);
        webView.clearCache(true);
        webView.clearHistory();
//        DataCleanManager.cleanCustomCache(WEBVIEW_CACHE_PATH);//root时可用
    }

    private class DialogHandler extends Handler {

        LoadingDialogFragment dialogFragment;

        public DialogHandler(LoadingDialogFragment dialogFragment) {
            this.dialogFragment = dialogFragment;
        }

        @Override
        public void handleMessage(Message msg) {
            if (null != dialogFragment && dialogFragment.getDialog().isShowing()) {
                dialogFragment.dismiss();

                ToastUtils.show(activity, getResources().getString(R.string.settings_clear_cache_success));
//                initCacheSize();
                String str = getString(R.string.settings_cache_size, "0.00");
                cacheSizeTv.setText(str);
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        //当fragment显示时
        if (!hidden) {
            initCacheSize();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != dialogHandler) {
            dialogHandler.removeCallbacksAndMessages(null);
        }
    }


}
