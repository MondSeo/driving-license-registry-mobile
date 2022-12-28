package com.example.drivinglicenseupload.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.example.drivinglicenseupload.ui.custom.CustomDialog;

public class Util {

    public static CustomDialog customViewDialog(Context context,String titleText,String contentText, int contentResourceId,
                                                CustomDialog.OnCustomInitialize onCustomInitialize,
                                                String okText,View.OnClickListener okListener, boolean isColorButton)
    {
        CustomDialog dialog = new CustomDialog(context,titleText,contentText,contentResourceId,onCustomInitialize,okText,okListener,isColorButton);
        dialog.setOnCustomInitialize(onCustomInitialize);
        try {
            dialog.setCancelable(true);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static CustomDialog selectDialog(Context context, String contentText, String leftText, String rightText, View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener) {
        CustomDialog dialog = new CustomDialog(context, contentText, leftText, rightText, leftOnClickListener, rightOnClickListener);
        try {
            dialog.setCancelable(true);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }

}
