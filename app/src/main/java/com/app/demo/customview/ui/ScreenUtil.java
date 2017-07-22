package com.app.demo.customview.ui;


import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtil {
    private static final String TAG = ScreenUtil.class.getSimpleName();
    private static int mScreenHeight;
    private static int mScreenWidth;

    private static String sDensityLabel;

    public static int getScreenWidth() {
        //make sure screen width is not 0
        if (mScreenWidth == 0) {
            mScreenWidth = 1;
        }
        return mScreenWidth;
    }

    public static int getScreenHeight() {
        //make sure screen height is not 0
        if (mScreenHeight == 0) {
            mScreenHeight = 1;
        }
        return mScreenHeight;
    }

    public static void init(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
        Log.d(TAG, "width is " + mScreenWidth + ", height is " + mScreenHeight);
    }

    public static float getDensity(Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return scale;
    }

    public static void setScreenDensityDPI(String densityLabel) {
        sDensityLabel = densityLabel;
    }

    public static String getScreenDensityDPI() {
        return sDensityLabel;
    }

    public static int convertDipToPix(Context context, int dip) {
        // return (int) (dip *
        // context.getResources().getDisplayMetrics().density + 0.5f);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics);
    }

    public static int convertPixToDip(Context context, int pixel) {
        float scale = getDensity(context);
        return (int) ((pixel - 0.5f) / scale);
    }

    public static float convertPixToSp(Context context, Float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static int convertDipToPix(Context context, float dip) {
        // return (int) (dip *
        // context.getResources().getDisplayMetrics().density + 0.5f);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics);
    }
}
