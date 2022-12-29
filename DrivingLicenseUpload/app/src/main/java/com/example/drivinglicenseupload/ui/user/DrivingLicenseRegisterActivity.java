package com.example.drivinglicenseupload.ui.user;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drivinglicenseupload.R;
import com.example.drivinglicenseupload.constant.AppConfig;
import com.example.drivinglicenseupload.constant.PrefKeys;
import com.example.drivinglicenseupload.ui.BaseActivity_CommonGNB;
import com.example.drivinglicenseupload.ui.custom.CustomDialog;
import com.example.drivinglicenseupload.util.Common;
import com.example.drivinglicenseupload.util.DBUtils;
import com.example.drivinglicenseupload.util.PermissionUtil;
import com.example.drivinglicenseupload.util.PictureUtils;
import com.example.drivinglicenseupload.util.PreferenceUtil;
import com.example.drivinglicenseupload.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class DrivingLicenseRegisterActivity extends BaseActivity_CommonGNB {

    Bitmap drivingLicensePhoto = null;
    private final int TAKE_PICTURE = 1;
    private final int SELECT_PICTURE = 2;
    private final int TAKE_PHOTO = 3;
    private final int EDIT_DELETE_PHOTO = 4;

    String capturedImageFileName;
    String capturedImagePath;
    String tempDirectoryStr;

    private String UserID;
    private String captureUri;

    public PreferenceUtil preferenceUtil;

    public static final int REQUEST_CODE_CAMERA = 23221;
    public static final int REQUEST_CODE_GALLERY = 23222;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.rel_Registry_Driving_License_Front:
                    popupDrivingLicenseSelect(true);
                    break;

                case R.id.rel_Registry_Driving_License_Back:
                    popupDrivingLicenseSelect(false);
                    break;
            }
            Log.d(TAG,"popupDrivingLicenseSelect");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_license_register);
        initLayout();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK || resultCode == RESULT_CANCELED) {
                    String captureUri = capturedImagePath + capturedImageFileName;
                    String manufacture = android.os.Build.MANUFACTURER;
                    if (!manufacture.equalsIgnoreCase("Sony") && resultCode == RESULT_CANCELED) {
                        PictureUtils.deleteBitmapFile(mContext, captureUri);
                        return;
                    }
                    Bitmap bitmap = PictureUtils.getSampleSizeBitmap(captureUri, 4);
                    if (bitmap != null) {
                        if (drivingLicensePhoto != null) {
                            drivingLicensePhoto.recycle();
                            drivingLicensePhoto = null;
                        }
                        drivingLicensePhoto = bitmap;
                        drivingLicensePhoto = PictureUtils.rotate(drivingLicensePhoto, PictureUtils.exifOrientationToDegrees(captureUri));
//                        saveImageToLocal();
                    }
//                    PictureUtils.deleteBitmapFile(mContext, capturedImageFileName);
                }
                break;
        }
    }

    private void initLayout(){
        setTitleText(getString(R.string.DrivingLicenseCertificate_Text));
        RelativeLayout rel_DrivingLicenseRegisterActivity_FrontImageAdd = findViewById(R.id.rel_Registry_Driving_License_Front);
        RelativeLayout rel_DrivingLicenseRegisterActivity_BackImageAdd = findViewById(R.id.rel_Registry_Driving_License_Back);
//        RecyclerView mDrivingLicenseListRecyclerView = findViewById(R.id.driving_License_Register_RecyclerView);

        rel_DrivingLicenseRegisterActivity_FrontImageAdd.setOnClickListener(clickListener);
        rel_DrivingLicenseRegisterActivity_BackImageAdd.setOnClickListener(clickListener);

    }

//    private void saveImageToLocal() {
//        if (drivingLicensePhoto != null) {
//            byte[] bitmapByteArray = Util.bitmapToByteArray(drivingLicensePhoto);
//            boolean isSaveSuccess = DBUtils.setProfileImage(mContext, Util.base64Encode(bitmapByteArray));
//            if (isSaveSuccess) {
//                Util.confirmDialog(mContext,getString(R.string.ChangeSMSNoticeSettingActivity_SuccessSaveSetting));
//            } else {
//                Util.confirmDialog(mContext,getString(R.string.common_fail));
//            }
//        }
//    }

    private void popupDrivingLicenseSelect(Boolean isFront) {
        tempDirectoryStr = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/temp/";
        File path = new File(tempDirectoryStr);
        deleteTempFile(path);
        Util.customViewDialog(this, getString(R.string.DrivingLicenseCustomDialog_Title_Text), contentText(isFront), R.layout.customdialog_drivinglicense_select, new CustomDialog.OnCustomInitialize() {
            CustomDialog dialog;
            @Override
            public void onInitialize(View contentView, CustomDialog dialog) {
                this.dialog = dialog;
                contentView.findViewById(R.id.btn_driving_license_take_photo).setOnClickListener(popupListener);
                contentView.findViewById(R.id.btn_driving_license_find_gallery).setOnClickListener(popupListener);
            }
            private View.OnClickListener popupListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    switch (v.getId())
                    {
                        case R.id.btn_driving_license_take_photo:
                            if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                if(!PermissionUtil.huaweiShouldShowRequestPermission(mContext, PermissionUtil.CAMERA_PERMISSION)
                                        || (!PermissionUtil.isHuaweiDevice() && !ActivityCompat.shouldShowRequestPermissionRationale(DrivingLicenseRegisterActivity.this, Manifest.permission.CAMERA))) {
                                    ActivityCompat.requestPermissions(DrivingLicenseRegisterActivity.this, CHECK_CAMERA_PERMISSIONS, PERMISSION_CHECK_CAMERA_ID);

                                } else {
                                    String permissions = getString(R.string.permission_camera);
                                    Util.selectDialog(mContext,
                                            String.format(getString(R.string.permissions_deny_optional), AppConfig.getAppName(), permissions),
                                            getString(R.string.Common_Cancel), getString(R.string.set),
                                            null,
                                            new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                                    intent.setData(uri);
                                                    startActivity(intent);
                                                }
                                            });
                                }
                            } else {
                                Intent intent = getTakePictureIntent();
                                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                            }
                            break;
                        case R.id.btn_driving_license_find_gallery:

                            break;
                    }
                }
            };
        },getString(R.string.Common_Close),null,false);
    }


    private boolean deleteTempFile(File path) {
        if (!path.isDirectory()) {
            return false;
        }
        File[] files = path.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteTempFile(file);
            } else {
                file.delete();
            }
        }
        return path.delete();
    }

    public Intent getTakePictureIntent() {
        capturedImagePath = createPicturePath(mContext);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", true);
        Uri capturePath;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            capturePath = Uri.fromFile(new File(capturedImagePath, capturedImageFileName));
            captureUri = capturePath.getPath();
        } else {
            File path = new File(capturedImagePath, capturedImageFileName);
            capturePath = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", path);
            captureUri = path.getAbsolutePath();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, capturePath);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        return intent;
    }

    private String createPicturePath(Context context) {
        String newImgPath = null;
        capturedImageFileName = UserID + "_DLicense.jpg";
        try {
            FileOutputStream fos = context.openFileOutput(capturedImageFileName, Context.MODE_PRIVATE);
            fos.close();
            newImgPath = tempDirectoryStr;
            Common.FileUtil.createDirectory(newImgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newImgPath;
    }

    private String contentText(boolean isFront){
        if(isFront){
            return getString(R.string.DrivingLicenseCustomDialog_ContentButton_Front_Text);
        }
        return getString(R.string.DrivingLicenseCustomDialog_ContentButton_Back_Text);
    }


}