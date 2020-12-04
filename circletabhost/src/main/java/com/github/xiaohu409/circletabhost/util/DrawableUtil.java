package com.github.xiaohu409.circletabhost.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class DrawableUtil {

    public static Drawable getDrawable(Context context, int rId) {
        Drawable drawable = context.getResources().getDrawable(rId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumWidth());
        return drawable;
    }

}
