package com.example.mylicenseregistry.ui.user;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.mylicenseregistry.ui.BaseActivity_CommonGNB;
import com.example.mylicenseregistry.util.PictureUtils;
import com.example.mylicenseregistry.util.Util;
import com.example.mylicenseregistry.widget.ContentButton;

import java.io.File;
import java.io.FileOutputStream;

public class VehicleRegistrationDetailActivity extends BaseActivity_CommonGNB {

    ImageView img_VehicleRegistrationDetailActivity_Front;
    ImageView img_VehicleRegistrationDetailActivity_Back;
    ContentButton btn_VehicleRegistrationDetailActivity_Save;
    ContentButton btn_VehicleRegistrationDetailActivity_Delete;
    Bitmap VehicleRegistrationFrontImage;
    Bitmap VehicleRegistrationBackImage;
    private String TAG = "VehicleRegistrationDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registration_detail);
        initLayout();
        initProcess();
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

        String frontImagePath = (bundle.getString("FrontImagePath")) + "/_LicenseFront.jpg";
        String backImagePath = (bundle.getString("BackImagePath")) + "/_LicenseBack.jpg";
        int position = bundle.getInt("Position");
//        File file = new File(frontImagePath);
//        FileInputStream frontFis = openFileInput("file",frontImagePath);

        Bitmap frontBitmap = PictureUtils.getSampleSizeBitmap(frontImagePath, 4);
        Bitmap backBitmap = PictureUtils.getSampleSizeBitmap(backImagePath, 4);


        if (frontBitmap != null) {
            if (VehicleRegistrationFrontImage != null) {
                VehicleRegistrationFrontImage.recycle();
                VehicleRegistrationFrontImage = null;
            }
            VehicleRegistrationFrontImage = frontBitmap;
            VehicleRegistrationFrontImage = PictureUtils.rotate(VehicleRegistrationFrontImage, PictureUtils.exifOrientationToDegrees(frontImagePath));

        }
        if (backBitmap != null) {
            if (VehicleRegistrationBackImage != null) {
                VehicleRegistrationBackImage.recycle();
                VehicleRegistrationBackImage = null;
            }
            VehicleRegistrationBackImage = backBitmap;
            VehicleRegistrationBackImage = PictureUtils.rotate(VehicleRegistrationBackImage, PictureUtils.exifOrientationToDegrees(backImagePath));

        }
        setImageResources(VehicleRegistrationFrontImage, VehicleRegistrationBackImage);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_VehicleRegistrationDetailActivity_Save:
                        saveImageToGallery(mContext,  VehicleRegistrationFrontImage,"VehicleRegistration", position);
                        saveImageToGallery(mContext,  VehicleRegistrationBackImage,"VehicleRegistration", position);
                        Util.confirmDialog(mContext, getString(R.string.Common_CustomDialog_Save_Text));

                        break;
                    case R.id.btn_VehicleRegistrationDetailActivity_Delete:
                        Util.selectDialog(mContext, getString(R.string.Common_ConfirmDelete),getString(R.string.Common_Cancel),getString(R.string.Common_Confirm),null, new View.OnClickListener() {
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

        btn_VehicleRegistrationDetailActivity_Save.setOnClickListener(clickListener);
        btn_VehicleRegistrationDetailActivity_Delete.setOnClickListener(clickListener);

    }

    private void setImageResources(Bitmap VehicleRegistrationFrontImage, Bitmap VehicleRegistrationBackImage) {
        Glide.with(mContext).load(VehicleRegistrationFrontImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerInside()
                .skipMemoryCache(true)
                .into(img_VehicleRegistrationDetailActivity_Front);

        Glide.with(mContext).load(VehicleRegistrationBackImage)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerInside()
                .skipMemoryCache(true)
                .into(img_VehicleRegistrationDetailActivity_Back);
    }

    @Override
    public void initProcess() {

    }




    private boolean saveImageToGallery(Context context, Bitmap bitmap, String path, int position) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED == state) {

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

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}