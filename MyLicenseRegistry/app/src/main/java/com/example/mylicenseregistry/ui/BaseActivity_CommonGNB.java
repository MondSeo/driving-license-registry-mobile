package com.example.mylicenseregistry.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;

import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.ui.custom.LoadingDialog;
import com.example.mylicenseregistry.util.PreferenceUtil;
import com.example.mylicenseregistry.util.Util;
import com.google.android.material.appbar.AppBarLayout;


public abstract class BaseActivity_CommonGNB extends AppCompatActivity {

    public PreferenceUtil preferenceUtil;
    private LoadingDialog mProgressDialog;
    /*상단 접히는거 변수*/
    public AppBarLayout app_bar_scrolling;
    public RelativeLayout rel_TopBar;
    public AppCompatActivity mContext;

    public LinearLayout btn_Back;
    public RelativeLayout rel_BaseActivityInnerScrollLayout;
    private final float alphaMargin = 0.2f; //20퍼센트 까지는 스크롤 무시하도록..
    private TextView txt_subTitle1,txt_subTitle2;
    public LinearLayout btn_bottom;
    private RelativeLayout rel_AdditionalLayout;
    public NestedScrollView scroll_BaseActivity;
    private LinearLayout btn_CommonGNB_RightButton;
    private ImageView img_CommonGNB_RightButton;
    private TextView txt_CommonGNB_RightButton;

    CoordinatorLayout.LayoutParams titleParam;
    TextView txt_BigTitle, txt_hiddenTitle;
    int hiddenTextTitleWidth, hiddenTextTitleHeight;
    final int minTitleTextSizeDp = 16;
    final int maxTitleTextSizeDp = 24;
    int minTopMargin,maxTopMargin,maxLeftMargin,minLeftMargin;
    TextView txt_Title;

    public boolean isFirstLoading = true;
    String bottomButtonText;
    int buttonButtonImage;
    View.OnClickListener bottomButtonClickListener;

    //개인화 2단계 레이아웃
    private ConstraintLayout cl_cpw2_vehicle_info;
    private TextView tv_vehicle_name, tv_owner_name, tv_owner_email, tv_owner_mobil;
    private LinearLayout btn_change_setting;
    private TextView txt_backup_memo_info;

    public static final int PERMISSION_CHECK_CAMERA_ID = 0x01;
    public static final int PERMISSION_CHECK_EXTERNAL_STORAGE_ID = 0x02;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_common_gnb);
        mContext = this;
        preferenceUtil = PreferenceUtil.getInstance(this);
        initBaseLayout();

    }

    public static final String[] CHECK_CAMERA_PERMISSIONS = {
            Manifest.permission.CAMERA
    };

    public static final String[] CHECK_READ_MEDIA_IMAGES_PERMISSIONS = {
            Manifest.permission.READ_MEDIA_IMAGES
    };

    public static final String[] CHECK_EXTERNAL_STORAGE_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void setContentView(int layoutResource)
    {
        rel_BaseActivityInnerScrollLayout.removeAllViews();
        LayoutInflater.from(this).inflate(layoutResource, rel_BaseActivityInnerScrollLayout);

    }


    public void addLayerLayout(int layoutResource)
    {
        LayoutInflater.from(mContext).inflate(layoutResource, rel_AdditionalLayout);
    }


    private void initBaseLayout()
    {
        rel_TopBar = findViewById(R.id.rel_TopBar);
        app_bar_scrolling = findViewById(R.id.app_bar_scrolling);
        txt_hiddenTitle = findViewById(R.id.txt_hiddenTitle);
        txt_hiddenTitle.setTextSize(minTitleTextSizeDp);

        txt_BigTitle = findViewById(R.id.txt_BigTitle);
        //txt_BigTitle.setText(R.string.BaseActivity_CommonGNB_Title);
        txt_BigTitle.setTextSize(maxTitleTextSizeDp);

        //txt_hiddenTitle.setText(R.string.BaseActivity_CommonGNB_Title);

        app_bar_scrolling.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();
                float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                handleTextViewAlpha(percentage);
            }
        });

        btn_Back = findViewById(R.id.btn_Back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txt_subTitle1 = findViewById(R.id.txt_subTitle1);
        txt_subTitle2 = findViewById(R.id.txt_subTitle2);

        btn_CommonGNB_RightButton = findViewById(R.id.btn_CommonGNB_RightButton);
        img_CommonGNB_RightButton = findViewById(R.id.img_CommonGNB_RightButton);
        txt_CommonGNB_RightButton = findViewById(R.id.txt_CommonGNB_RightButton);
        /*
        setSubTitleText1("기존 비밀번호와 변경하실\n신규 비밀번호를 입력해 주세요.");
        setSubTitleText2("비밀번호는 알파벳과 숫자의 조합으로\n8자리 이상 입력해 주세요.");
        setCommonGNB_RightButton("Modify", R.drawable.selector_b_gr_01, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BaseActivity_CommonGNB.this,"common gnb click", Toast.LENGTH_SHORT).show();
            }
        });
        */
        btn_bottom = findViewById(R.id.btn_bottom);
        rel_BaseActivityInnerScrollLayout = findViewById(R.id.rel_BaseActivityInnerScrollLayout);
//        showBottomButton("조흥수",null);

        rel_AdditionalLayout = findViewById(R.id.rel_AdditionalLayout);
        scroll_BaseActivity =  findViewById(R.id.scroll_BaseActivity);

        cl_cpw2_vehicle_info = findViewById(R.id.cl_cpw2_vehicle_info);
        tv_vehicle_name = findViewById(R.id.tv_vehicle_name);
        tv_owner_name = findViewById(R.id.tv_owner_name);
        tv_owner_email = findViewById(R.id.tv_owner_email);
        tv_owner_mobil = findViewById(R.id.tv_owner_mobil);
        btn_change_setting = findViewById(R.id.btn_change_setting);
        txt_backup_memo_info = findViewById(R.id.txt_backup_memo_info);
    }

    public void setAppbarScrollEnable(final boolean isEnable)
    {
        CoordinatorLayout.LayoutParams params =(CoordinatorLayout.LayoutParams) app_bar_scrolling.getLayoutParams();
        AppBarLayout.Behavior behavior = new AppBarLayout.Behavior();
        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(AppBarLayout appBarLayout) {
                return isEnable;
            }
        });
        params.setBehavior(behavior);
//        scroll_BaseActivity.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        app_bar_scrolling.setExpanded(true,true);
        scroll_BaseActivity.setNestedScrollingEnabled(isEnable);
    }

    public void setNestedScrollingEnabled(boolean isEnable) {
        scroll_BaseActivity.setNestedScrollingEnabled(isEnable);
    }




    private void textsizeAnimation(float percentage)
    {
        if(hiddenTextTitleWidth==0)
        {
            hiddenTextTitleWidth = txt_hiddenTitle.getWidth();
            hiddenTextTitleHeight = txt_hiddenTitle.getHeight();
            minTopMargin = (rel_TopBar.getHeight()-hiddenTextTitleHeight)/2;

            titleParam = (CoordinatorLayout.LayoutParams) txt_Title.getLayoutParams();
            maxTopMargin = titleParam.topMargin;
            minLeftMargin = titleParam.leftMargin;
            maxLeftMargin = (rel_TopBar.getWidth()-hiddenTextTitleWidth)/2;
        }

        float sizeGap = maxTitleTextSizeDp - minTitleTextSizeDp; //텍스트 사이즈 미니멈과 맥시멈 차이
        float toSize = maxTitleTextSizeDp-(sizeGap*percentage);

        int topMargin = (int)(maxTopMargin - (maxTopMargin*percentage));
        if(topMargin<minTopMargin)
            topMargin = minTopMargin;
        int leftMargin = (int)(minLeftMargin +((maxLeftMargin-minLeftMargin)*percentage));
        titleParam.topMargin = topMargin;
        titleParam.leftMargin = leftMargin;

        txt_Title.setTextSize(toSize);
        txt_Title.setLayoutParams(titleParam);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus&&rel_BaseActivityInnerScrollLayout.getDescendantFocusability()==ViewGroup.FOCUS_BLOCK_DESCENDANTS)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
//                    rel_BaseActivityInnerScrollLayout.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                }
            },1000);
        }
        if(hasFocus&&isFirstLoading)
        {
            rel_BaseActivityInnerScrollLayout.setPadding(0,0,0,1);
            if(!TextUtils.isEmpty(bottomButtonText))
            {
                btn_bottom.setVisibility(View.VISIBLE);
                TextView txt_BottomButton = findViewById(R.id.txt_BottomButton);
                ImageView img_BottomButton = findViewById(R.id.img_BottomButton);
                img_BottomButton.setBackgroundResource(buttonButtonImage);
                txt_BottomButton.setText(bottomButtonText);
                btn_bottom.setOnClickListener(bottomButtonClickListener);
                rel_BaseActivityInnerScrollLayout.setPadding(0,0,0, Util.dpToPx(50));
            }
            isFirstLoading = false;
        }

    }

    public void setTitleText(String titleText)
    {
        txt_BigTitle.setText(titleText);
        txt_hiddenTitle.setText(titleText);
    }

    public void setCommonGNB_RightButton(String buttonText, int buttonImage,View.OnClickListener buttonClickListener)
    {
        btn_CommonGNB_RightButton.setVisibility(View.VISIBLE);
        btn_CommonGNB_RightButton.setOnClickListener(buttonClickListener);
        if (buttonImage>0)
        img_CommonGNB_RightButton.setBackgroundResource(buttonImage);
        txt_CommonGNB_RightButton.setText(buttonText);
    }

    public void changeCommonGNB_RightButton(String buttonText, int buttonImage,boolean isVisible)
    {
        btn_CommonGNB_RightButton.setVisibility(isVisible?View.VISIBLE:View.GONE);
        if(buttonImage>0)
            img_CommonGNB_RightButton.setBackgroundResource(buttonImage);
        txt_CommonGNB_RightButton.setText(buttonText);
    }

    public void setSubTitleText1(String subTitleText1)
    {
        txt_subTitle1.setVisibility(View.VISIBLE);
        txt_subTitle1.setText(subTitleText1);
        if(txt_subTitle2.getVisibility()==View.VISIBLE)
            findViewById(R.id.view_SubTitleBottom1).setVisibility(View.VISIBLE);
        findViewById(R.id.view_SubTitleBottom2).setVisibility(View.VISIBLE);
    }

    public void setSubTitleText2(String subTitleText2)
    {
        txt_subTitle2.setVisibility(View.VISIBLE);
        txt_subTitle2.setText(subTitleText2);
        if(txt_subTitle1.getVisibility()==View.VISIBLE)
            findViewById(R.id.view_SubTitleBottom1).setVisibility(View.VISIBLE);
        findViewById(R.id.view_SubTitleBottom2).setVisibility(View.VISIBLE);
    }



    private void handleTextViewAlpha(float percentage)
    {
        float titleOpacity = percentage;
        float bodyOpacity = 1-percentage;
        if(titleOpacity<(1-alphaMargin))
            titleOpacity = 0;
        else {
            titleOpacity = (titleOpacity - (1-alphaMargin)) * (1 / (1 - (1-alphaMargin)));
        }
        if(bodyOpacity<alphaMargin)
            bodyOpacity = 0;
        else
            bodyOpacity = (bodyOpacity-alphaMargin)*(1/(1-alphaMargin));

//        Util.logcat("percentage","percentage : " + percentage + ", titleOpacity : "+titleOpacity + " , bodyOpacity : " + bodyOpacity);
        txt_hiddenTitle.setAlpha(titleOpacity);
        findViewById(R.id.rel_CollapseInnerView).setAlpha(bodyOpacity);

    }


    public void changeBottomButton(String buttonText)
    {
        changeBottomButton(buttonText,0 );
    }

    public void changeBottomButton(String buttonText,int buttonImage)
    {
        this.bottomButtonText = buttonText;
        this.buttonButtonImage = buttonImage;
        TextView txt_BottomButton = findViewById(R.id.txt_BottomButton);
        ImageView img_BottomButton = findViewById(R.id.img_BottomButton);
        img_BottomButton.setBackgroundResource(buttonImage);
        txt_BottomButton.setText(buttonText);
    }

    public void showBottomButton(String buttonText, View.OnClickListener buttonListener)
    {
        showBottomButton(buttonText, 0, buttonListener);
    }


    public void showBottomButton(String buttonText, int buttonImage, View.OnClickListener buttonListener)
    {
        this.bottomButtonText = buttonText;
        this.buttonButtonImage = buttonImage;
        this.bottomButtonClickListener = buttonListener;
        btn_bottom.setVisibility(View.VISIBLE);
    }

    public void showBottomButton()
    {
        if(TextUtils.isEmpty(bottomButtonText))
            return;
        btn_bottom.setVisibility(View.VISIBLE);
    }

    public void hideBottomButton()
    {
        btn_bottom.setVisibility(View.GONE);
    }

    public void showVehicleInfo(String vehicleName, View.OnClickListener clickListener) {
        cl_cpw2_vehicle_info.setVisibility(View.VISIBLE);
        tv_vehicle_name.setText(vehicleName);
        btn_change_setting.setOnClickListener(clickListener);
//        initInfoData();
    }


    public void startProgress(final int StringRes)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    if (mProgressDialog == null || !mProgressDialog.isShowing())
                        mProgressDialog = new LoadingDialog(mContext, getString(StringRes));
                    else
                        mProgressDialog.setProgressText(getString(StringRes));
                    mProgressDialog.setCancelable(false);

                    mProgressDialog.show();
            }
        });
    }

    public void endProgress()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //getMemoryLog(); //yws - 프로그레스바 종료 시 메모리 로그 남기기
                if (mProgressDialog != null && mProgressDialog.isShowing())
                {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        });
    }
    public void startActivity(Class<?> activity)
    {
        if(activity!=null)
            startActivity(activity,null);
    }

    public void startActivityForResult(Class<?> activity,int requestCode)
    {
        if(activity!=null)
            startActivityForResult(activity,requestCode,null);
    }

    public void startActivity(Class<?> activity,Bundle bundle)
    {
        if(activity==null)
            return;
        Intent intent = new Intent(mContext,activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(bundle!=null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> activity,int requestCode,Bundle bundle)
    {
        if(activity==null)
            return;
        Intent intent = new Intent(mContext,activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if(bundle!=null)
            intent.putExtras(bundle);
        startActivityForResult(intent,requestCode);
    }

    public abstract void getInitializeParameter();

    public abstract void initLayout();

    public abstract void initProcess();


//    private void initInfoData() {
//        String userId = preferenceUtil.getPreference(PrefKeys.KEY_SELECTED_USER_ID);
//        UserInfoLocalDB userInfoLocalDB = getUserInfoLocalDB(userId);
//        if(userInfoLocalDB != null) {
//            tv_owner_name.setText(userInfoLocalDB.userInfo_CCSP.getName());
//            tv_owner_email.setText(String.format(getString(R.string.personalization_email), userInfoLocalDB.UserID));
//            tv_owner_mobil.setText(String.format(getString(R.string.personalization_mobile_number), userInfoLocalDB.PhoneNumber));
//        }
//    }

    public void showMemoInfo() {
        txt_backup_memo_info.setVisibility(View.VISIBLE);
    }


}
