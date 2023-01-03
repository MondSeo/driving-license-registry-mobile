package com.example.drivinglicenseupload.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;

import com.example.drivinglicenseupload.R;
import com.example.drivinglicenseupload.ui.custom.CustomDialog;

import java.io.ByteArrayOutputStream;

public class Util {
    public static CustomDialog mConfirmDialog = null;
    private static String mPrevContent = "";

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

    public static CustomDialog confirmDialog(Context context, String contentText) {
        return confirmDialog(context,null, contentText, context.getResources().getString(R.string.Common_Confirm), null);
    }


    public static CustomDialog confirmDialog(Context context,String titleText, String contentText, String okText, View.OnClickListener okOnClickListener) {
        if (mConfirmDialog != null && mConfirmDialog.isShowing()){
            if (mPrevContent.equals(contentText)){
                return null;
            } else {
                if (context instanceof Activity && !((Activity)context).isFinishing()) {
                    try {
                        mConfirmDialog.dismiss();
                    } catch (Exception e) {
                        // 아마 java.lang.IllegalArgumentException 일거임,, 일단 Crash 방지용
                    }
                }
            }
        }
        mConfirmDialog = new CustomDialog(context,titleText, contentText, okText, okOnClickListener);
        mPrevContent = contentText;
        try {
            mConfirmDialog.setCancelable(true);
            mConfirmDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mConfirmDialog;
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
