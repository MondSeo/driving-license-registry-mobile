package com.example.mylicenseregistry.ui.user;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.datadto.BluelinkModel;
import com.example.mylicenseregistry.datadto.BluelinkSettings;
import com.example.mylicenseregistry.datadto.DrivingLicenseImage;
import com.example.mylicenseregistry.ui.BaseActivity_CommonGNB;
import com.example.mylicenseregistry.util.PictureUtils;
import com.example.mylicenseregistry.util.Util;
import com.example.mylicenseregistry.widget.ContentButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DrivingLicenseDetailActivity extends BaseActivity_CommonGNB {

    ImageView img_DrivingLicenseDetailActivity_Front;
    ImageView img_DrivingLicenseDetailActivity_Back;
    ContentButton btn_DrivingLicenseDetailActivity_Save;
    ContentButton btn_DrivingLicenseDetailActivity_Delete;
    Bitmap drivingLicenseFrontImage;
    Bitmap drivingLicenseBackImage;
    private String TAG = "DrivingLicenseDetailActivity";

    private ArrayList<DrivingLicenseImage> mDataList = new ArrayList<DrivingLicenseImage>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license_detail);
        initLayout();
        initProcess();
    }

    @Override
    public void getInitializeParameter() {

    }

    @Override
    public void initLayout() {
        setTitleText(getString(R.string.DrivingLicenseDetailActivity_Title));
        img_DrivingLicenseDetailActivity_Front = findViewById(R.id.img_DrivingLicenseDetailActivity_FrontImage);
        img_DrivingLicenseDetailActivity_Back = findViewById(R.id.img_DrivingLicenseDetailActivity_BackImage);
        btn_DrivingLicenseDetailActivity_Save = findViewById(R.id.btn_DrivingLicenseDetailActivity_Save);
        btn_DrivingLicenseDetailActivity_Delete = findViewById(R.id.btn_DrivingLicenseDetailActivity_Delete);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        int position = bundle.getInt("Position");

        mDataList = new ArrayList<>();
        ArrayList<Bundle> dbArrayList = null;

        dbArrayList = BluelinkModel.getInst(mContext).selectPositionDrivingLicenseImage(position + 1);
        if (dbArrayList != null) {
            for (Bundle item : dbArrayList) {
                String frontBitmapStr = item.getString(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_FRONT);
                drivingLicenseFrontImage = Util.getBitmapFromByteArray(Util.base64Decode(frontBitmapStr));
                String backBitmapStr = item.getString(BluelinkSettings.DrivingLicenseImage.DRIVING_LICENSE_IMAGE_BACK);
                drivingLicenseBackImage = Util.getBitmapFromByteArray(Util.base64Decode(backBitmapStr));

            }
        }

        setImageResources(drivingLicenseFrontImage, drivingLicenseBackImage);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_DrivingLicenseDetailActivity_Save:
                        saveImageToGallery(mContext,  drivingLicenseFrontImage,"BlueLink_DrivingLicense",position);
                        saveImageToGallery(mContext,  drivingLicenseBackImage,"BlueLink_DrivingLicense",position);
                        Util.confirmDialog(mContext, getString(R.string.Common_CustomDialog_Save_Text));

                    break;
                    case R.id.btn_DrivingLicenseDetailActivity_Delete:
                        Util.selectDialog(mContext, getString(R.string.Common_Delete),getString(R.string.Common_Cancel),getString(R.string.Common_Confirm),null, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setResult(RESULT_OK, intent);
                                intent.putExtra("Position",position);
                                Log.d(TAG,position + "되었습니다.");
                                finish();
                            }
                        });

                    break;
                }
            }
        };

        btn_DrivingLicenseDetailActivity_Save.setOnClickListener(clickListener);
        btn_DrivingLicenseDetailActivity_Delete.setOnClickListener(clickListener);

    }

    private void setImageResources(Bitmap drivingLicenseFrontImage, Bitmap drivingLicenseBackImage) {
        Glide.with(mContext).load(drivingLicenseFrontImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter()
                .skipMemoryCache(true)
                .into(img_DrivingLicenseDetailActivity_Front);

        Glide.with(mContext).load(drivingLicenseBackImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter()
                .skipMemoryCache(true)
                .into(img_DrivingLicenseDetailActivity_Back);
    }

    @Override
    public void initProcess() {

    }




    private void saveImageToGallery(Context context, Bitmap bitmap, String path, int position) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {

            String rootPath =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                            .toString();
            String dirName = "/" + path;
            String fileName = System.currentTimeMillis() + position + ".jpg";
            File savePath = new File(rootPath + dirName);
            if(!savePath.exists()){
                savePath.mkdirs();
            }

            File file = new File(savePath, fileName);
            if (file.exists()) file.delete();

            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

                //갤러리 갱신
                context.sendBroadcast(
                        new Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                                Uri.parse("file://" + Environment.getExternalStorageDirectory())
                        )
                );

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}