package com.github.xiaohu409.demo.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    public static void showShort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
