package com.example.drivinglicenseupload.ui.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.drivinglicenseupload.R;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomDialog extends Dialog {

    enum DialogType {
        BTN1, BTN2, CUSTOMVIEW, LINKVIEW
    }

    /**
     * 사용자 Dialog초기화 interface
     */
    public interface OnCustomInitialize {
        void onInitialize(View contentView, CustomDialog dialog);
    }

    private Context context;

    private DialogType dialogType;
    private String titleText;
    private String contentText;
    private String okText, leftText, rightText;
    private TextView contentTextView;
    private LinearLayout btn1LinearLayout, btn2LinearLayout;
    private View.OnClickListener oneOnClickListener, leftOnClickListener, rightOnClickListener;
    private CompoundButton.OnCheckedChangeListener checkboxListener;
    private Button oneButton, leftButton, rightButton;
    private boolean leftBtnColored;

    private OnCustomInitialize onCustomInitialize;
    private int customResourceId;
    private boolean onlyShowContentView = false;
    private boolean isTopBarVisible = true;
    private Pattern pattern1, pattern2;
    private String link1, link2;
    private CheckBox dialogCheckbox;
    private boolean isShowDialogCheckbox;

    public void setOnCustomInitialize(OnCustomInitialize initialize)
    {
        this.onCustomInitialize = initialize;
        this.dialogType = DialogType.CUSTOMVIEW;
    }

    public CustomDialog(Context context, String contentText, String okText, View.OnClickListener okOnClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.contentText = contentText;
        this.okText = okText;
        this.oneOnClickListener = okOnClickListener;
        dialogType = DialogType.BTN1;
    }

    public CustomDialog(Context context, String titleText, String contentText, String okText, View.OnClickListener okOnClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.titleText = titleText;
        this.contentText = contentText;
        this.okText = okText;
        this.oneOnClickListener = okOnClickListener;
        dialogType = DialogType.BTN1;
    }

    public CustomDialog(Context context, String contentText, String leftText, String rightText, View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.contentText = contentText;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        dialogType = DialogType.BTN2;
    }

    public CustomDialog(Context context, boolean leftBtnColored, String contentText, String leftText, String rightText, View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.contentText = contentText;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        this.leftBtnColored = leftBtnColored;
        dialogType = DialogType.BTN2;
    }

    public CustomDialog(Context context, String titleText, String contentText, String leftText, String rightText, View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.titleText = titleText;
        this.contentText = contentText;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        dialogType = DialogType.BTN2;
    }

    public CustomDialog(Context context, String titleText, String contentText, String leftText, String rightText, Boolean isShowDialogCheckbox, View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener, CompoundButton.OnCheckedChangeListener checkboxListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.titleText = titleText;
        this.contentText = contentText;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        this.isShowDialogCheckbox = isShowDialogCheckbox;
        this.checkboxListener = checkboxListener;
        dialogType = DialogType.BTN2;
    }


    // Link가 들어가는 다이얼로그
    public CustomDialog(Context context, String titleText, String contentText, String leftText, String rightText, View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener,
                        Pattern pattern1, Pattern pattern2, String link1, String link2) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.titleText = titleText;
        this.contentText = contentText;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        this.pattern1 = pattern1;
        this.pattern2 = pattern2;
        this.link1 = link1;
        this.link2 = link2;
        dialogType = DialogType.LINKVIEW;
    }

    // TrafficTicket Link 다이얼로그
    public CustomDialog(Context context, String titleText, String contentText, String leftText, String rightText, View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener,
                        Pattern pattern1, String link1) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.titleText = titleText;
        this.contentText = contentText;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        this.pattern1 = pattern1;
        this.link1 = link1;
        dialogType = DialogType.LINKVIEW;
    }

    //custom view가 들어가는 다이얼로그
    public CustomDialog(Context context, int customResourceId, OnCustomInitialize onCustomInitialize, String leftText, String rightText , View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.onCustomInitialize = onCustomInitialize;
        this.customResourceId = customResourceId;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        this.dialogType = DialogType.CUSTOMVIEW;
    }

    public CustomDialog(Context context, String titleText, String contentText, int customResourceId, OnCustomInitialize onCustomInitialize, String leftText, String rightText , View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.titleText = titleText;
        this.contentText = contentText;
        this.onCustomInitialize = onCustomInitialize;
        this.customResourceId = customResourceId;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        this.dialogType = DialogType.CUSTOMVIEW;
    }

    //custom view가 들어가는 다이얼로그
    public CustomDialog(Context context, int customResourceId, OnCustomInitialize onCustomInitialize, String okText , View.OnClickListener okListener, boolean isColorButton)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.onCustomInitialize = onCustomInitialize;
        this.customResourceId = customResourceId;
        if(isColorButton) {
            this.rightText = okText;
            this.rightOnClickListener = okListener;
        }
        else
        {
            this.leftText = okText;
            this.leftOnClickListener = okListener;
        }
        this.dialogType = DialogType.CUSTOMVIEW;
    }

    public CustomDialog(Context context, String titleText, String contentText, int customResourceId, OnCustomInitialize onCustomInitialize, String okText , View.OnClickListener okListener, boolean isColorButton)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.titleText = titleText;
        this.contentText = contentText;
        this.onCustomInitialize = onCustomInitialize;
        this.customResourceId = customResourceId;
        if(isColorButton) {
            this.rightText = okText;
            this.rightOnClickListener = okListener;
        }
        else
        {
            this.leftText = okText;
            this.leftOnClickListener = okListener;
        }
        this.dialogType = DialogType.CUSTOMVIEW;
    }


    //custom view가 들어가는 다이얼로그
    //top bar 보여줄지 여부 플래그 추가
    public CustomDialog(Context context, int customResourceId, OnCustomInitialize onCustomInitialize, String leftText, String rightText , View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener, boolean isTopBarVisible)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.onCustomInitialize = onCustomInitialize;
        this.customResourceId = customResourceId;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        this.dialogType = DialogType.CUSTOMVIEW;
        this.isTopBarVisible = isTopBarVisible;
    }

    public CustomDialog(Context context, String titleText, String contentText, int customResourceId, OnCustomInitialize onCustomInitialize, String leftText, String rightText , View.OnClickListener leftOnClickListener, View.OnClickListener rightOnClickListener, boolean isTopBarVisible)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.titleText = titleText;
        this.contentText = contentText;
        this.onCustomInitialize = onCustomInitialize;
        this.customResourceId = customResourceId;
        this.leftText = leftText;
        this.rightText = rightText;
        this.leftOnClickListener = leftOnClickListener;
        this.rightOnClickListener = rightOnClickListener;
        this.dialogType = DialogType.CUSTOMVIEW;
        this.isTopBarVisible = isTopBarVisible;
    }


    //custom view가 들어가는 다이얼로그
    //x버튼 없으며 하단 확인버튼도 없음.
    public CustomDialog(Context context, int customResourceId, OnCustomInitialize onCustomInitialize, boolean onlyShowContentView)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.onCustomInitialize = onCustomInitialize;
        this.customResourceId = customResourceId;
        this.dialogType = DialogType.CUSTOMVIEW;
        this.onlyShowContentView = onlyShowContentView;
    }

    public CustomDialog(Context context, String titleText, String contentText , int customResourceId, OnCustomInitialize onCustomInitialize, boolean onlyShowContentView)
    {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.titleText = titleText;
        this.contentText = contentText;
        this.onCustomInitialize = onCustomInitialize;
        this.customResourceId = customResourceId;
        this.dialogType = DialogType.CUSTOMVIEW;
        this.onlyShowContentView = onlyShowContentView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setAllTranslucent(true);
        switch (dialogType)
        {
            case CUSTOMVIEW:
                setContentView(R.layout.custom_customized_dialog);
                break;
            default:
                setContentView(R.layout.custom_default_dialog);
                break;
        }
        setView();
    }

    public void setAllTranslucent(boolean setAllTranslucent) {
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        if (setAllTranslucent) {
            lpWindow.dimAmount = 0.0f;
        } else {
            lpWindow.dimAmount = 0.8f;
        }
        getWindow().setAttributes(lpWindow);
    }

    private void setView()
    {
        setContentTitle();
        setContentText();
        btn1LinearLayout =  findViewById(R.id.button1_linear_layout);
        btn2LinearLayout =  findViewById(R.id.button2_linear_layout);

        switch (dialogType)
        {
            case BTN1:
                btn1LinearLayout.setVisibility(View.VISIBLE);
                btn2LinearLayout.setVisibility(View.GONE);
                oneButton = findViewById(R.id.ok_button);
                oneButton.setText(okText);
                View.OnClickListener rootOkOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissDialog();
                        if (oneOnClickListener != null) {
                            oneOnClickListener.onClick(v);
                        }
                    }
                };
                oneButton.setOnClickListener(rootOkOnClickListener);
                break;
            case BTN2:
                btn1LinearLayout.setVisibility(View.GONE);
                btn2LinearLayout.setVisibility(View.VISIBLE);
                leftButton =  findViewById(R.id.left_button);
                rightButton = findViewById(R.id.right_button);
                leftButton.setText(leftText);
                rightButton.setText(rightText);

                if (isShowDialogCheckbox) {
                    dialogCheckbox = findViewById(R.id.dialog_checkbox);
                    dialogCheckbox.setVisibility(View.VISIBLE);
                    dialogCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (checkboxListener != null) {
                                checkboxListener.onCheckedChanged(buttonView, isChecked);
                            }
                        }
                    });
                }

                // 좌측 버튼에 색상 주기
                if (leftBtnColored) {
                    leftButton.setTextColor(context.getResources().getColorStateList(R.color.text_common_dialog_button_right));
                    rightButton.setTextColor(context.getResources().getColorStateList(R.color.text_common_dialog_button_left));
                }

                View.OnClickListener rootLeftOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissDialog();
                        if (leftOnClickListener != null) {
                            leftOnClickListener.onClick(v);
                        }
                    }
                };

                View.OnClickListener rootRightOnClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissDialog();
                        if (rightOnClickListener != null) {
                            rightOnClickListener.onClick(v);
                        }
                    }
                };
                leftButton.setOnClickListener(rootLeftOnClickListener);
                rightButton.setOnClickListener(rootRightOnClickListener);
                break;
            case CUSTOMVIEW:
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // body layout 생성
                LinearLayout layout = findViewById(R.id.dialog_content_customview);
                View view = inflater.inflate(customResourceId, null);
                layout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                if(onCustomInitialize != null)
                    onCustomInitialize.onInitialize(view,this);
                if(!TextUtils.isEmpty(leftText))
                {
                    leftButton =  findViewById(R.id.left_button);
                    leftButton.setText(leftText);
                    leftButton.setVisibility(View.VISIBLE);
                    rootLeftOnClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissDialog();
                            if (leftOnClickListener != null) {
                                leftOnClickListener.onClick(v);
                            }
                        }
                    };
                    leftButton.setOnClickListener(rootLeftOnClickListener);
                }

                if(!TextUtils.isEmpty(rightText))
                {
                    rightButton = findViewById(R.id.right_button);
                    rightButton.setText(rightText);
                    rightButton.setVisibility(View.VISIBLE);
                    rootRightOnClickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dismissDialog();
                            if (rightOnClickListener != null) {
                                rightOnClickListener.onClick(v);
                            }
                        }
                    };
                    rightButton.setOnClickListener(rootRightOnClickListener);
                }
                if(!TextUtils.isEmpty(leftText)&&!TextUtils.isEmpty(rightText))
                    findViewById(R.id.view_CustomDialog_bottomDivider).setVisibility(View.VISIBLE);

                RelativeLayout rel_CustomDialog_TopBar = findViewById(R.id.rel_CustomDialog_TopBar);
                rel_CustomDialog_TopBar.setVisibility(isTopBarVisible? View.VISIBLE: View.GONE);
                if(onlyShowContentView)
                {
                    findViewById(R.id.rel_CustomDialog_BottomView).setVisibility(View.GONE);
                    rel_CustomDialog_TopBar.setVisibility(View.GONE);
                }
                break;
            case LINKVIEW:
                btn1LinearLayout.setVisibility(View.GONE);
                btn2LinearLayout.setVisibility(View.VISIBLE);
                leftButton =  findViewById(R.id.left_button);
                rightButton = findViewById(R.id.right_button);
                leftButton.setText(leftText);
                rightButton.setText(rightText);
                contentTextView = findViewById(R.id.dialog_content_textview);

                // 좌측 버튼에 색상 주기
                if (leftBtnColored) {
                    leftButton.setTextColor(context.getResources().getColorStateList(R.color.text_common_dialog_button_right));
                    rightButton.setTextColor(context.getResources().getColorStateList(R.color.text_common_dialog_button_left));
                }
                leftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissDialog();
                        if (leftOnClickListener != null) {
                            leftOnClickListener.onClick(v);
                        }
                    }
                });
                rightButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissDialog();
                        if (rightOnClickListener != null) {
                            rightOnClickListener.onClick(v);
                        }
                    }
                });
                contentTextView.setMovementMethod(LinkMovementMethod.getInstance());
                try {
                    Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
                        @Override
                        public String transformUrl(Matcher match, String url) {
                            return "";
                        }
                    };
                    if (pattern1 != null && link1 != null) {
                        Linkify.addLinks(contentTextView, pattern1, link1, new Linkify.MatchFilter() {
                            @Override
                            public boolean acceptMatch(CharSequence charSequence, int start, int end) {
                                int firstIndex = charSequence.toString().indexOf(pattern1.pattern()) + pattern1.pattern().length();
                                return end < firstIndex + 1;
                            }
                        }, mTransform);
                    }
                    if (pattern2 != null && link2 != null) {
                        Linkify.addLinks(contentTextView, pattern2, link2, new Linkify.MatchFilter() {
                            @Override
                            public boolean acceptMatch(CharSequence charSequence, int start, int end) {
                                int firstIndex = charSequence.toString().indexOf(pattern2.pattern()) + pattern2.pattern().length();
                                return end < firstIndex + 1;
                            }
                        }, mTransform);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setContentTitle()
    {
        TextView txt_CustomDialog_contentTitle = findViewById(R.id.txt_CustomDialog_contentTitle);
        if(!TextUtils.isEmpty(titleText))
        {
            txt_CustomDialog_contentTitle.setText(titleText);
            txt_CustomDialog_contentTitle.setVisibility(View.VISIBLE);
        }
    }

    private void setContentText()
    {
        TextView dialog_content_textview = findViewById(R.id.dialog_content_textview);
        if(!TextUtils.isEmpty(contentText))
        {
            dialog_content_textview.setText(contentText);
            dialog_content_textview.setVisibility(View.VISIBLE);
            if (isShowDialogCheckbox || contentText.equals(context.getString(R.string.driver_license_popup_content_ble_bluelink)) || contentText.equals(context.getString(R.string.driver_license_popup_content_ble_kia))) {
                dialog_content_textview.setGravity(Gravity.LEFT);
            }
        }
    }


    private void dismissDialog()
    {
        try
        {
            dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public TextView getRightButton()
    {
        return rightButton;
    }
}