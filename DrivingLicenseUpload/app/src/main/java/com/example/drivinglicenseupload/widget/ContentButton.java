package com.example.drivinglicenseupload.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.drivinglicenseupload.R;
import com.example.drivinglicenseupload.util.Util;


public class ContentButton extends LinearLayout
{
    private final int BUTTONSTYLE_A = 0;
    private final int BUTTONSTYLE_B = 1;
    private final int BUTTONSTYLE_C = 2;

    private Context context;
    private TypedArray typedArray;
    private TextView txt_ContentButton;
    private LinearLayout lin_ContentButton_Background,lin_ContentButton;
    private int buttonStyle = BUTTONSTYLE_A;

    public ContentButton(Context context)
    {
        super(context);
        this.context = context;
        initLayout();
    }

    public ContentButton(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        typedArray = context.obtainStyledAttributes(attributeSet,R.styleable.ContentButton,0,0);
        this.context = context;
        initLayout();
    }

    private void initLayout()
    {
        LayoutInflater.from(context).inflate(R.layout.layout_contentbutton, this);
        txt_ContentButton = findViewById(R.id.txt_ContentButton);
        lin_ContentButton_Background = findViewById(R.id.lin_ContentButton_Background);
        lin_ContentButton = findViewById(R.id.lin_ContentButton);
        if(typedArray!=null)
        {
            txt_ContentButton.setText(typedArray.getString(R.styleable.ContentButton_text));
            setEnabled(typedArray.getBoolean(R.styleable.ContentButton_enabled,true));
            buttonStyle = typedArray.getInt(R.styleable.ContentButton_button_style,BUTTONSTYLE_A);
        }
        switch (buttonStyle)
        {
            case BUTTONSTYLE_A:
                lin_ContentButton_Background.setBackgroundResource(R.drawable.btn_content_a);
                txt_ContentButton.setTextColor(getResources().getColorStateList(R.color.button_text_content_a));
                lin_ContentButton.setPadding(Util.dpToPx(20),Util.dpToPx(5),Util.dpToPx(20),Util.dpToPx(5));
                break;
            case BUTTONSTYLE_B:
                lin_ContentButton_Background.setBackgroundResource(R.drawable.btn_content_b);
                txt_ContentButton.setTextColor(getResources().getColorStateList(R.color.button_text_content_b));
                lin_ContentButton.setPadding(Util.dpToPx(20),Util.dpToPx(5),Util.dpToPx(20),Util.dpToPx(5));
                break;
            case BUTTONSTYLE_C:
                lin_ContentButton_Background.setBackgroundResource(R.drawable.btn_content_c);
                txt_ContentButton.setTextColor(getResources().getColorStateList(R.color.button_text_content_c));
                lin_ContentButton.setPadding(Util.dpToPx(30),Util.dpToPx(5),Util.dpToPx(30),Util.dpToPx(5));
                break;
        }
    }

    public void setText(String text)
    {
        txt_ContentButton.setText(text);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        findViewById(R.id.lin_ContentButton).setEnabled(enabled);
        lin_ContentButton_Background.setEnabled(enabled);
        txt_ContentButton.setEnabled(enabled);
    }
}
