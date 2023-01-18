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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.datadto.BluelinkModel;
import com.example.mylicenseregistry.datadto.BluelinkSettings;
import com.example.mylicenseregistry.datadto.VehicleRegistrationImage;
import com.example.mylicenseregistry.ui.BaseActivity_CommonGNB;
import com.example.mylicenseregistry.util.Util;
import com.example.mylicenseregistry.widget.ContentButton;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class VehicleRegistrationDetailActivity extends BaseActivity_CommonGNB {

    ImageView img_VehicleRegistrationDetailActivity_Front;
    ImageView img_VehicleRegistrationDetailActivity_Back;
    ContentButton btn_VehicleRegistrationDetailActivity_Save;
    ContentButton btn_VehicleRegistrationDetailActivity_Delete;
    Bitmap VehicleRegistrationFrontImage;
    Bitmap VehicleRegistrationBackImage;
    private String TAG = "VehicleRegistrationDetailActivity";

    private ArrayList<VehicleRegistrationImage> mDataList = new ArrayList<VehicleRegistrationImage>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration_detail);
        initLayout();
    }

    @Override
    public void getInitializeParameter() {

    }

    @Override
    public void initLayout() {
        setTitleText(getString(R.string.VehicleRegistrationDetailActivity));
        img_VehicleRegistrationDetailActivity_Front = findViewById(R.id.img_VehicleRegistrationDetailActivity_FrontImage);
        img_VehicleRegistrationDetailActivity_Back = findViewById(R.id.img_VehicleRegistrationDetailActivity_BackImage);
        btn_VehicleRegistrationDetailActivity_Save = findViewById(R.id.btn_VehicleRegistrationDetailActivity_Save);
        btn_VehicleRegistrationDetailActivity_Delete = findViewById(R.id.btn_VehicleRegistrationDetailActivity_Delete);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
          int position = bundle.getInt("Position");

        mDataList = new ArrayList<>();
        ArrayList<Bundle> dbArrayList = null;

        dbArrayList = BluelinkModel.getInst(mContext).selectPositionVehicleRegistrationImage(position);
        if (dbArrayList != null) {
            for (Bundle item : dbArrayList) {
                String frontBitmapStr = item.getString(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_FRONT);
                VehicleRegistrationFrontImage = Util.getBitmapFromByteArray(Util.base64Decode(frontBitmapStr));
                String backBitmapStr = item.getString(BluelinkSettings.VehicleRegistrationImage.VEHICLE_REGISTRATION_IMAGE_BACK);
                VehicleRegistrationBackImage = Util.getBitmapFromByteArray(Util.base64Decode(backBitmapStr));

            }
        }

        setImageResources(VehicleRegistrationFrontImage, VehicleRegistrationBackImage);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_VehicleRegistrationDetailActivity_Save:
                        saveImageToGallery(mContext,  VehicleRegistrationFrontImage,"BlueLink_VehicleRegistration", position);
                        saveImageToGallery(mContext,  VehicleRegistrationBackImage,"BlueLink_VehicleRegistration", position);
                        Toast.makeText(mContext, R.string.Toast_Save_Complete, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.btn_VehicleRegistrationDetailActivity_Delete:
                        Util.selectDialog(mContext, getString(R.string.Popup_ConfirmDelete),getString(R.string.Common_Cancel),getString(R.string.Common_Confirm),null, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setResult(RESULT_OK, intent);
                                intent.putExtra("Position",position - 1);
                                Toast.makeText(mContext, R.string.Toast_DeleteComplete, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                        break;
                }
            }
        };

        btn_VehicleRegistrationDetailActivity_Save.setOnClickListener(clickListener);
        btn_VehicleRegistrationDetailActivity_Delete.setOnClickListener(clickListener);

    }

    private void setImageResources(Bitmap VehicleRegistrationFrontImage, Bitmap VehicleRegistrationBackImage) {
        if (Util.isValidContextForGlide(mContext)) {
            Glide.with(mContext).load(VehicleRegistrationFrontImage)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(new CenterCrop(), new RoundedCorners(36))
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.drawable_circle_edge)
                    .error(R.drawable.drawable_circle_edge)
                    .fallback(R.drawable.drawable_circle_edge)
                    .into(img_VehicleRegistrationDetailActivity_Front);

            Glide.with(mContext).load(VehicleRegistrationBackImage)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .transform(new CenterCrop(), new RoundedCorners(36))
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.drawable_circle_edge)
                    .error(R.drawable.drawable_circle_edge)
                    .fallback(R.drawable.drawable_circle_edge)
                    .into(img_VehicleRegistrationDetailActivity_Back);
        }
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