package com.rarcher.Acticitys;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rarcher.Figure.FingerprintUtil;
import com.rarcher.R;
import com.rarcher.Utils.ScreenUtils;

public class FigureSafe extends AppCompatActivity {
    private Context mContext;
    ConstraintLayout layout;
    View view;
    AlertDialog dialog;

    private final static String TAG = "finger_log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure_safe);
        mContext = this;
        FingerprintUtil.context = mContext;
        view = LayoutInflater.from(this).inflate(R.layout.figure_dialog, null, false);
        dialog = new AlertDialog.Builder(this).setView(view).create();
        layout = findViewById(R.id.btn_activity_main_finger);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFingerprintClick(v);

            }
        });
    }

    public void onFingerprintClick(View v){

        FingerprintUtil.callFingerPrint(new FingerprintUtil.OnCallBackListenr() {

            @Override
            public void onSupportFailed() {
                Toast.makeText(mContext,"当前设备不支持指纹",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInsecurity() {
                Toast.makeText(mContext,"当前设备未处于安全保护中",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEnrollFailed() {

                Toast.makeText(mContext,"请到设置中设置指纹",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationStart() {
                showDialog();
            }

            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {

                Toast.makeText(mContext,errString.toString(),Toast.LENGTH_SHORT).show();
                if (dialog != null  &&dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onAuthenticationFailed() {

                Toast.makeText(mContext,"解锁失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {

                Toast.makeText(mContext,helpString.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {

                Toast.makeText(mContext,"解锁成功",Toast.LENGTH_SHORT).show();
                if (dialog != null  &&dialog.isShowing()){

                    dialog.dismiss();

                }

            }
        });
    }

    //初始化并弹出对话框方法
    private void showDialog() {

        final Button cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerprintUtil.cancel();
                dialog.dismiss();
            }
        });

        dialog.show();
        //此处设置位置窗体大小，我这里设置为了手机屏幕宽度的3/4  注意一定要在show方法调用后再写设置窗口大小的代码，否则不起效果会
        dialog.getWindow().setLayout((ScreenUtils.getScreenWidth(this) / 4 * 3), LinearLayout.LayoutParams.WRAP_CONTENT);
    }


}
