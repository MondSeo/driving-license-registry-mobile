package com.example.mylicenseregistry.ui.user;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.mylicenseregistry.R;
import com.example.mylicenseregistry.constant.AppConfig;
import com.example.mylicenseregistry.constant.PrefKeys;
import com.example.mylicenseregistry.ui.BaseActivity_CommonGNB;
import com.example.mylicenseregistry.ui.custom.CustomDialog;
import com.example.mylicenseregistry.util.Common;
import com.example.mylicenseregistry.util.DBUtils;
import com.example.mylicenseregistry.util.PermissionUtil;
import com.example.mylicenseregistry.util.PictureUtils;
import com.example.mylicenseregistry.util.Util;
import com.example.mylicenseregistry.widget.ContentButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VehicleRegistrationRegisterActivity extends BaseActivity_CommonGNB {

    Bitmap VehicleRegistrationImageFront = null;
    Bitmap VehicleRegistrationImageBack = null;

    ImageView img_VehicleRegistrationRegisterActivity_UploadedImage_Front;
    ImageView img_VehicleRegistrationRegisterActivity_UploadedImage_Back;
    LinearLayout lin_VehicleRegistrationRegisterActivity_Front;
    LinearLayout lin_VehicleRegistrationRegisterActivity_Back;

    String capturedImageFileName;
    String capturedImagePath;
    String selectedImagePath;
    String tempDirectoryStr;

    private String UserID;
    byte[] frontBitmapByteArray;
    byte[] backBitmapByteArray;

    public static final int REQUEST_CODE_CAMERA = 23221;
    public static final int REQUEST_CODE_GALLERY = 23222;

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (PermissionUtil.isStoragePermissionDeny(mContext)) {
                if (!PermissionUtil.huaweiShouldShowRequestPermission(mContext, PermissionUtil.STORAGE_PERMISSION)
                        || (!PermissionUtil.isHuaweiDevice() && PermissionUtil.isStorageFirstAskPermission(VehicleRegistrationRegisterActivity.this))) {
                    ActivityCompat.requestPermissions(VehicleRegistrationRegisterActivity.this, (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) ? CHECK_EXTERNAL_STORAGE_PERMISSIONS : CHECK_READ_MEDIA_IMAGES_PERMISSIONS, PERMISSION_CHECK_EXTERNAL_STORAGE_ID);
                } else {
                    String permissions = getString(R.string.permission_storage);
                    Util.selectDialog(VehicleRegistrationRegisterActivity.this,
                            String.format(getString(R.string.permissions_deny_mandatory), AppConfig.getAppName(), permissions),
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
                switch (v.getId()) {
                    case R.id.rel_VehicleRegistrationRegisterActivity_Front:
                        showSelectPopup(true);
                        break;

                    case R.id.rel_VehicleRegistrationRegisterActivity_Back:
                        showSelectPopup(false);
                        break;

                }
                Log.d(TAG, "showSelectPopup");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_registry_register);
        initLayout();
    }

    @Override
    public void getInitializeParameter() {}

    @Override
    protected void onResume() {
        super.onResume();
        if (img_VehicleRegistrationRegisterActivity_UploadedImage_Front.getVisibility() == View.VISIBLE &&
                img_VehicleRegistrationRegisterActivity_UploadedImage_Back.getVisibility() == View.VISIBLE) {
            saveLicenseImageToLocal(VehicleRegistrationImageFront, VehicleRegistrationImageBack);
            Intent intent = new Intent();
            Util.confirmDialog(this, getString(R.string.MyListActivity_DrivingLicense_info_yes), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String strSurface = preferenceUtil.getPreference(PrefKeys.KEY_LICENSE_SURFACE, "true");
        boolean isFrontSurface = Boolean.parseBoolean(strSurface);
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
                    if(isFrontSurface){
                        if (bitmap != null) {
                            if (VehicleRegistrationImageFront != null) {
                                VehicleRegistrationImageFront.recycle();
                                VehicleRegistrationImageFront = null;
                            }
                            VehicleRegistrationImageFront = bitmap;
                            VehicleRegistrationImageFront = PictureUtils.rotate(VehicleRegistrationImageFront, PictureUtils.exifOrientationToDegrees(captureUri));
                            setVehicleRegistrationImage(VehicleRegistrationImageFront, true);
                        }
                    } else {
                        if (bitmap != null) {
                            if (VehicleRegistrationImageBack != null) {
                                VehicleRegistrationImageBack.recycle();
                                VehicleRegistrationImageBack = null;
                            }
                            VehicleRegistrationImageBack = bitmap;
                            VehicleRegistrationImageBack = PictureUtils.rotate(VehicleRegistrationImageBack, PictureUtils.exifOrientationToDegrees(captureUri));
                            setVehicleRegistrationImage(VehicleRegistrationImageBack, false);
                        }
                    }

//                    PictureUtils.deleteBitmapFile(mContext, capturedImageFileName);
                }
                break;
            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK) {
                    Uri selectedImageUri = data.getData();
                    selectedImagePath = getPath(selectedImageUri);
                    if(isFrontSurface){
                        if (VehicleRegistrationImageFront != null) {
                            VehicleRegistrationImageFront.recycle();
                            VehicleRegistrationImageFront = null;
                        }
                        VehicleRegistrationImageFront = PictureUtils.getSampleSizeBitmap(selectedImagePath, 4);
                        VehicleRegistrationImageFront = PictureUtils.rotate(VehicleRegistrationImageFront, PictureUtils.exifOrientationToDegrees(selectedImagePath));
                        if (VehicleRegistrationImageFront != null) {
                            setVehicleRegistrationImage(VehicleRegistrationImageFront, true);
                        }
                    } else{
                        if (VehicleRegistrationImageBack != null) {
                            VehicleRegistrationImageBack.recycle();
                            VehicleRegistrationImageBack = null;
                        }
                        VehicleRegistrationImageBack = PictureUtils.getSampleSizeBitmap(selectedImagePath, 4);
                        VehicleRegistrationImageBack = PictureUtils.rotate(VehicleRegistrationImageBack, PictureUtils.exifOrientationToDegrees(selectedImagePath));
                        if (VehicleRegistrationImageBack != null) {
                            setVehicleRegistrationImage(VehicleRegistrationImageBack, false);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void initLayout() {
        setTitleText(getString(R.string.VehicleRegistrationCertificate_Text));
        RelativeLayout rel_VehicleRegistrationRegisterActivity_FrontImageAdd = findViewById(R.id.rel_VehicleRegistrationRegisterActivity_Front);
        RelativeLayout rel_VehicleRegistrationRegisterActivity_BackImageAdd = findViewById(R.id.rel_VehicleRegistrationRegisterActivity_Back);
        img_VehicleRegistrationRegisterActivity_UploadedImage_Front = findViewById(R.id.img_VehicleRegistrationRegisterActivity_UploadedImage_Front);
        img_VehicleRegistrationRegisterActivity_UploadedImage_Back = findViewById(R.id.img_VehicleRegistrationRegisterActivity_UploadedImage_Back);
        lin_VehicleRegistrationRegisterActivity_Front = findViewById(R.id.lin_VehicleRegistrationRegisterActivity_Front);
        lin_VehicleRegistrationRegisterActivity_Back = findViewById(R.id.lin_VehicleRegistrationRegisterActivity_Back);
        rel_VehicleRegistrationRegisterActivity_FrontImageAdd.setOnClickListener(clickListener);
        rel_VehicleRegistrationRegisterActivity_BackImageAdd.setOnClickListener(clickListener);
    }

    @Override
    public void initProcess() {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        String strSurface = preferenceUtil.getPreference(PrefKeys.KEY_LICENSE_SURFACE, "true");
        boolean isFrontSurface = Boolean.parseBoolean(strSurface);
        if(requestCode == PERMISSION_CHECK_CAMERA_ID) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        } else if(requestCode == PERMISSION_CHECK_EXTERNAL_STORAGE_ID) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showSelectPopup(isFrontSurface);
            }

        }
    }

    private void saveLicenseImageToLocal(Bitmap frontBitmap, Bitmap backBitmap) {
        if (frontBitmap != null && backBitmap != null) {
            frontBitmapByteArray = Util.bitmapToByteArray(frontBitmap);
            backBitmapByteArray = Util.bitmapToByteArray(backBitmap);
            DBUtils.setVehicleRegistrationImage(mContext,
                    Util.base64Encode(frontBitmapByteArray),
                    Util.base64Encode(backBitmapByteArray)
            );
        }
    }

    private void showSelectPopup(Boolean isFrontSurface) {
        tempDirectoryStr = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/temp/";
        preferenceUtil.setPreference(PrefKeys.KEY_LICENSE_SURFACE, isFrontSurface);
        File path = new File(tempDirectoryStr);
        deleteTempFile(path);
        Util.customViewDialog(mContext, getString(R.string.FavoriteAddActivity_AddPhoto),
                getString(R.string.MainActivity_select_share_option_below), R.layout.customdialog_license_select, new CustomDialog.OnCustomInitialize() {
            @Override
            public void onInitialize(View contentView, CustomDialog dialog) {
                ContentButton btn_CustomDialog_Camera = contentView.findViewById(R.id.btn_CustomDialog_LicenseTakePhoto);
                ContentButton btn_CustomDialog_Gallery = contentView.findViewById(R.id.btn_CustomDialog_LicenseFindGallery);
                btn_CustomDialog_Camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera();
                        dialog.dismiss();
                    }
                });
                btn_CustomDialog_Gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                        dialog.dismiss();
                    }
                });

            }


        }, getString(R.string.Common_Close), null, false);
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
        Uri uri;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(new File(capturedImagePath, capturedImageFileName));
        } else {
            File path = new File(capturedImagePath, capturedImageFileName);
            uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".fileprovider", path);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        return intent;
    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (!PermissionUtil.huaweiShouldShowRequestPermission(this, PermissionUtil.CAMERA_PERMISSION)
                    || (!PermissionUtil.isHuaweiDevice() && !ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))) {
                ActivityCompat.requestPermissions(VehicleRegistrationRegisterActivity.this, CHECK_CAMERA_PERMISSIONS, PERMISSION_CHECK_CAMERA_ID);
            } else {
                String permissions = getString(R.string.permission_camera);
                Util.selectDialog(this,
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
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    private String createPicturePath(Context context) {
        String newImgPath = null;
        capturedImageFileName = "license.jpg";
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

    private void setVehicleRegistrationImage(Bitmap imageBitmap, Boolean isFrontSurface) {
        if (Util.isValidContextForGlide(mContext)) {
            if (isFrontSurface) {
                lin_VehicleRegistrationRegisterActivity_Front.setVisibility(View.GONE);
                img_VehicleRegistrationRegisterActivity_UploadedImage_Front.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(imageBitmap)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transform(new CenterCrop(), new RoundedCorners(36))
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.drawable_circle_edge)
                        .error(R.drawable.drawable_circle_edge)
                        .fallback(R.drawable.drawable_circle_edge)
                        .into(img_VehicleRegistrationRegisterActivity_UploadedImage_Front);
            } else {
                lin_VehicleRegistrationRegisterActivity_Back.setVisibility(View.GONE);
                img_VehicleRegistrationRegisterActivity_UploadedImage_Back.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(imageBitmap)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .transform(new CenterCrop(), new RoundedCorners(36))
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.drawable_circle_edge)
                        .error(R.drawable.drawable_circle_edge)
                        .fallback(R.drawable.drawable_circle_edge)
                        .into(img_VehicleRegistrationRegisterActivity_UploadedImage_Back);
            }
        }
    }

    private String getPath(Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = this.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

//    private String contentText(boolean isFrontSurface) {
//        if (isFrontSurface) {
//            return getString(R.string.Common_CustomDialog_ContentButton_LicenseMainPage_Text);
//        }
//        return getString(R.string.Common_CustomDialog_ContentButton_LicenseAttachmentPage_Text);
//    }

}