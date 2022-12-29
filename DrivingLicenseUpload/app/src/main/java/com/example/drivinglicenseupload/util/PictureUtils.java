package com.example.drivinglicenseupload.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import com.example.drivinglicenseupload.log.LOG;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureUtils
{
    private static final int UNCONSTRAINED = -1;
    public static final int BITMAP_TARGET_SIZE = 900*900;
    public static final int BITMAP_TARGET_QUALITY = 80;
    
    /***************************************************************/
    // Gallery ?먯꽌 ?ъ쭊???살뼱?ㅻ뒗 硫붿꽌??
    /***************************************************************/
    public static void takePictureFromGallery(Activity activity, int requestCode)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /***************************************************************/
    // EXIF?뺣낫瑜??뚯쟾媛곷룄濡?蹂?솚?섎뒗 硫붿꽌??
    /***************************************************************/
    public static int exifOrientationToDegrees(String picturePath)
    {
        ExifInterface exif = null;
        try
        {
            exif = new ExifInterface(picturePath);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
            {
                return 90;
            }
            else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
            {
                return 180;
            }
            else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
            {
                return 270;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    /***************************************************************/
    // ?대?吏?? ?뚯쟾?섎뒗 硫붿꽌??
    /***************************************************************/
    public static Bitmap rotate(Bitmap bitmap, int degrees)
    {
        if (degrees != 0 && bitmap != null)
        {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

            try
            {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted)
                {
                    bitmap.recycle();
                    bitmap = converted;
                }
            }
            catch (OutOfMemoryError ex)
            {
                // 硫붾え由ш? 遺?”?섏뿬 ?뚯쟾???쒗궎吏?紐삵븷 寃쎌슦 洹몃깷 ?먮낯??諛섑솚.
//                LOG.error("OutOfMemoryError error = " + ex.getMessage());
            }
        }
        return bitmap;
    }

    /***************************************************************/
    // ?대?吏?Size 瑜?異뺤냼?섎뒗 硫붿꽌??
    /***************************************************************/
    public static Bitmap getSampleSizeBitmap(String picturePath, int sampleSize)
    {
        byte[] pictureData = getPictureByteData(picturePath);

        if (pictureData == null)
            return null;

        return getSampleSizeBitmap(pictureData, sampleSize);
    }

    public static Bitmap getSampleSizeBitmap(byte[] data, int sampleSize)
    {
        Bitmap bitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.RGB_565;
        options.inSampleSize = sampleSize;

        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

        return bitmap;
    }

    /***************************************************************/
    // ?대?吏?Byte Data 瑜?由ы꽩?섎뒗 硫붿꽌??
    /***************************************************************/
    public static byte[] getPictureByteData(String picturePath)
    {
        FileInputStream fileInpushStream = null;
        byte[] data = null;

        if (picturePath != null && picturePath.length() > 0)
        {
            try
            {
                File file = new File(picturePath);
                fileInpushStream = new FileInputStream(file);
                data = new byte[fileInpushStream.available()];

                if( data.length == 0)
                {
                    fileInpushStream.close();
                    return null;
                }

                while (fileInpushStream.read(data) != -1)
                {
                    ;
                }
                
                fileInpushStream.close();

            }
            catch (FileNotFoundException e)
            {
                LOG.error("FileNotFoundException error = " + e.getMessage());
                data = null;
            }
            catch (IOException e)
            {
                LOG.error("IOException error = " + e.getMessage());
                data = null;
            }
        }
        return data;
    }

//    public static void adjustImageFileQuality(Context a_ctx, File a_file) {
    public static void adjustImageFileQuality(File a_file) {
        if (!a_file.exists())
            return;

        int MAX_IMAGE_SIZE = 200 * 1024; //이미지는 200k이하로
        Bitmap bmpPic = BitmapFactory.decodeFile(a_file.getPath());

        if ((bmpPic.getWidth() >= 1024) && (bmpPic.getHeight() >= 1024)) {
            BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
            bmpOptions.inSampleSize = 1;
            while ((bmpPic.getWidth() >= 1024) && (bmpPic.getHeight() >= 1024)) {
                bmpOptions.inSampleSize++;
                bmpPic = BitmapFactory.decodeFile(a_file.getPath(), bmpOptions);
            }
        }

        Matrix mat = new Matrix();
        mat.postRotate(getImageRotationAngle(a_file));
        bmpPic = Bitmap.createBitmap(bmpPic, 0, 0, bmpPic.getWidth(), bmpPic.getHeight(), mat, true);

        int compressQuality = 104; //압축률 기준 104. +-5 기준
        int streamLength = MAX_IMAGE_SIZE;
        while (streamLength >= MAX_IMAGE_SIZE) {
            ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
            compressQuality -= 5;
            bmpPic.compress(CompressFormat.JPEG, compressQuality,
                    bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
        }

        try {
            FileOutputStream bmpFile = new FileOutputStream(a_file);
            bmpPic.compress(CompressFormat.JPEG, compressQuality, bmpFile);
            bmpFile.flush();
            bmpFile.close();
        } catch (Exception e) {
        }
    }

    public static int getImageRotationAngle(File a_file){
        int iOrientation = ExifInterface.ORIENTATION_NORMAL;
        int iRotateAdjustAngle = 0;

        try {
            ExifInterface exif = new ExifInterface(a_file.getPath());
            iOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (iOrientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    iRotateAdjustAngle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    iRotateAdjustAngle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    iRotateAdjustAngle = 270;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                    iRotateAdjustAngle = 0;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return iRotateAdjustAngle;
    }


    public static byte[] getResourceByteData(Context context, int resouceId)
    {
        byte[] data = null;
        
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resouceId);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, stream);
        data = stream.toByteArray();
        
        return data;
    }

    public static Bitmap loadBitmapFile(Context context, String fileName)
    {
        String path = getPathBitmapFile(context, fileName);

        return BitmapFactory.decodeFile(path);
    }

    public static void saveBitmapFile(Context context, String fileName, Bitmap bitmap)
    {
        FileOutputStream fos;
        try
        {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(CompressFormat.JPEG, BITMAP_TARGET_QUALITY, fos);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void deleteBitmapFile(Context context, String fileName)
    {
        if (isBitmapFileExist(context, fileName))
        {
            File bitmap = new File(getPathBitmapFile(context, fileName));
            bitmap.delete();
        }
    }

    public static boolean isBitmapFileExist(Context context, String fileName)
    {
        boolean bRet = false;
        File filePath = new File(getPathBitmapFile(context, fileName));

        bRet = filePath.isFile();
        filePath = null;

        return bRet;
    }

    public static String getPathBitmapFile(Context context, String fileName)
    {
        return context.getFilesDir().toString() + "/" + fileName;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
    {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8)
        {
            roundedSize = 1;
            while (roundedSize < initialSize)
            {
                roundedSize <<= 1;
            }
        }
        else
        {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels)
    {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == UNCONSTRAINED) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == UNCONSTRAINED) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound)
        {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == UNCONSTRAINED) && (minSideLength == UNCONSTRAINED))
        {
            return 1;
        }
        else if (minSideLength == UNCONSTRAINED)
        {
            return lowerBound;
        }
        else
        {
            return upperBound;
        }
    }

    public static Bitmap makeBitmap(byte[] jpegData, int maxNumOfPixels)
    {
        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length, options);
            if (options.mCancel || options.outWidth == -1 || options.outHeight == -1)
            {
                return null;
            }
            options.inSampleSize = computeSampleSize(options, UNCONSTRAINED, maxNumOfPixels);
            options.inJustDecodeBounds = false;

            options.inDither = false;
            options.inPreferredConfig = Config.ARGB_8888;
            return BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length, options);
        }
        catch (OutOfMemoryError e)
        {
            LOG.error("OutOfMemoryError error = " + e.getMessage());
            return null;
        }
    }
    
}
