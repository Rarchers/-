package com.rarcher.Utils;

import android.content.Context;
import android.graphics.Color;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

/**
 * Created by 25532 on 2019/3/12.
 */

public class Toasts {
    public static void Toasts(Context context,String info){
        new StyleableToast
                .Builder(context)
                .text(info)
                .length(1)
                .textColor(Color.WHITE)
                .backgroundColor(Color.GRAY)
                .show();
    }
}
