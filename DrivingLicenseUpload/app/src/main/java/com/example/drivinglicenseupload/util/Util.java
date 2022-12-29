package com.example.drivinglicenseupload.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;

import com.example.drivinglicenseupload.ui.custom.CustomDialog;

import java.io.ByteArrayOutputStream;

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

    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static byte[] base64Decode(String source)
    {
        return android.util.Base64.decode(source, Base64.DEFAULT);
    }

    public static String base64Encode(byte[] data)
    {
        return android.util.Base64.encodeToString(data,Base64.DEFAULT);
    }

}
