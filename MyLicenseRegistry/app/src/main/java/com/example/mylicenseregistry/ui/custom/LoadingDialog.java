package com.example.mylicenseregistry.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mylicenseregistry.R;


public class LoadingDialog extends Dialog {
    private Context mContext;
    private String contentText;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mRunnable = null;
    private TextView txt_cancel;
    private static final long SHOW_CANCEL_BTN_TIME = 10000;

    public LoadingDialog(Context context, String contentText, OnLoadingCancelBtnClickListener listener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.contentText = contentText;
        this.mListener = listener;
    }

    public LoadingDialog(Context context, String contentText) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.contentText = contentText;
    }

    public LoadingDialog(Context context, String contentText, int autoCloseDelayMillis) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.contentText = contentText;
        setDismissAutoCloseTime(autoCloseDelayMillis);
    }


    public LoadingDialog(Context context, String contentText, boolean cancelable) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mContext = context;
        this.contentText = contentText;
        setCancelable(cancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        initLayout();
    }

    private void initLayout() {
        TextView txt_LoadingDialog = findViewById(R.id.txt_LoadingDialog);
        txt_LoadingDialog.setText(contentText);
        ImageView img_Kia_LoadingDialog = findViewById(R.id.img_Kia_LoadingDialog);
        LottieAnimationView img_Bluelink_LoadingDialog = findViewById(R.id.img_Bluelink_LoadingDialog);

        img_Kia_LoadingDialog.setVisibility(View.GONE);
        img_Bluelink_LoadingDialog.setAnimation(R.raw.bluelink_progress_polling);
        img_Bluelink_LoadingDialog.loop(true);
        img_Bluelink_LoadingDialog.playAnimation();


        txt_cancel = findViewById(R.id.txt_cancel);
        SpannableString content = new SpannableString(txt_cancel.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        txt_cancel.setText(content);

        txt_cancel.setOnClickListener(v -> {
            if (mListener != null) mListener.onClick();
        });

        if (mListener != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    txt_cancel.setVisibility(View.VISIBLE);
                }
            }, SHOW_CANCEL_BTN_TIME);
        }
    }

    public void setProgressText(String contentText) {
        setProgressText(contentText, 0);
    }

    public void setProgressText(String contentText, int autoCloseTime) {
        setDismissAutoCloseTime(autoCloseTime);
        this.contentText = contentText;
        TextView txt_LoadingDialog = findViewById(R.id.txt_LoadingDialog);
        txt_LoadingDialog.setText(contentText);
    }

    private AnimationDrawable makeAnimationDrawable() {
//        Util.logcat(getClass().getSimpleName(),"로딩바 생성 시작");
//        long starttime = System.currentTimeMillis();

        int lastNum;
        String formatName;
        int duration;

        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.setOneShot(false);


        lastNum = 30;
        formatName = "%03d";
        duration = 30;


        for (int i = 1; i <= lastNum; i++) {
            String name = "loading_" + String.format(formatName, i);
            int frameID = mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
            Drawable drawable = mContext.getResources().getDrawable(frameID);
            animationDrawable.addFrame(drawable, duration);
        }
//        long makeTime = System.currentTimeMillis() - starttime;
//        Util.logcat(getClass().getSimpleName(),"로딩바 생성끝, 걸린시간 : " + makeTime +"ms");
        return animationDrawable;
    }


    private void setDismissAutoCloseTime(int delayMillis) {
        removeAutoCloseCall();
        if (delayMillis > 0) {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            };
            mHandler.postDelayed(mRunnable, delayMillis);
        }
    }

    private void removeAutoCloseCall() {
        if (mHandler != null && mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void dismiss() {
        super.dismiss();
        removeAutoCloseCall();
    }

    private OnLoadingCancelBtnClickListener mListener;

    public interface OnLoadingCancelBtnClickListener {
        void onClick();
    }
}
