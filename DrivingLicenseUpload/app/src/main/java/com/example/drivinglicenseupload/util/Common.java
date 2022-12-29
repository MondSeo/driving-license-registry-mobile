package com.example.drivinglicenseupload.util;


import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.Display;

import com.example.drivinglicenseupload.log.LOG;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class Common
{

    public static final String PUSH_NOTICE_ACTION_BLUELINK = "com.ubivelox.bluelink.alibaba_push.RECEIVED_NOTICE";
    public static final String PUSH_NOTICE_ACTION_UVO = "com.ubivelox.uvo.alibaba_push.RECEIVED_NOTICE";

    public static final String PUSH_ACTION_VALET_DEACTIVATE = "genlink://notification/valet/deactivate";
    private static final String scheme =  "bluelink";
    public static final String PUSH_ACTION_LIGHTSTATUS_LIGHTON = scheme + "://notification/lightStatus/lightOn";  //미등/비상등 푸시 url
    public static final String PUSH_ACTION_LIGHTSTATUS_LIGHTON_CODE = "lighton";    //푸시 수신 시 LampControlActivity 이동 처리용 값
    public static final String PUSH_ACTION_MINI_APP_LINK = "notification/miniapp/execute";  //wechat miniapp link url
    public static final String PUSH_ACTION_MINI_APP_LINK_CODE = "miniapp";  //wechat miniapp link url
    public static final String PUSH_ACTION_FINAL_DESTINATION_APP_LINK = scheme + "://notification/finalDestGuide";
    public static final String PUSH_ACTION_FINAL_DESTINATION_APP_LINK_CODE = "fdg";

    public static class SystemUtil
    {
        public static long currentTimeMillis()
        {
            return System.currentTimeMillis();
        }

        public static void arraycopy(Object src, int srcPos, Object dst,
                                                  int dstPos, int length)
        {
            try
            {
                System.arraycopy(src, srcPos, dst, dstPos, length);
            }
            catch(Exception e)
            {
                LOG.error("Exception error = " + e.getMessage());
            }
        }

        public static void sleep(long millis)
        {
            SystemClock.sleep(millis);
        }

        public static void gc()
        {
            System.gc();
        }
        
        public static void sendLOGEmail(Context context, String strPath)
        {
            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            intent.setType("plain/text");
            ArrayList<Uri> uris = new ArrayList<Uri>();
            
            String strDir = Environment.getExternalStorageDirectory() + "/" + strPath;
            File directory = new File(strDir);
            if(directory.isDirectory())
            {
                File[] files = directory.listFiles();
                for(File file : files)
                {
                    Uri u = Uri.fromFile(file);
                    uris.add(u);
                }
            }
            else
            {
                uris.add(Uri.fromFile(directory));
            }
            
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris); 
            context.startActivity(intent);
        }
    }

    public static class FileUtil
    {
        public static void createDirectory(String directory)
        {
            File path = new File(directory);

            if(path.isDirectory() == false)
            {
                try
                {
                    path.mkdirs();
                }
                catch(Exception e)
                {
                    LOG.error("Exception error = " + e.getMessage());
                }
            }
            path = null;
        }

        public static boolean isFileExist(String name)
        {
            if(name == null || name.length() < 0)
                return false;

            boolean bRet = false;
            File file = new File(name);

            bRet = file.isFile();
            file = null;

            return bRet;
        }

        public static boolean deleteFile(String name)
        {
            LOG.debug(">> deleteFile("+name+")");
            boolean bRet = false;

            if(name == null || name.length() < 0 || isFileExist(name) == false)
                return bRet;

            File file = new File(name);
            bRet = file.delete();
            file = null;
            return bRet;
        } 

        public static boolean deleteSerializeFile(String path, String name)
        {
            LOG.debug(">>deleteSerializeFile("+path+", "+name+")");
            
            boolean bRet = false;

            if(path == null || path.length() < 0)
                return bRet;
            if(name == null || name.length() < 0)
                return bRet;

            if(isSerializeFileExist(path, name) == false)
                return bRet;

            File file = new File(path, name);
            bRet = file.delete();
            file = null;
            return bRet;
        }

        private static boolean isSerializeFileExist(String path, String name)
        {
            boolean bRet = false;
            File filePath = new File(path, name);

            bRet = filePath.isFile();
            filePath = null;

            return bRet;
        }

        public static Object loadSerializeFile(Context context, String fileName)
        {
            if(context == null || fileName == null || fileName.length() < 0)
                return null;

            FileInputStream fis = null;
            ObjectInputStream ois = null;
            Object outputObj = null;

            try
            {
                fis = context.openFileInput(fileName);
                ois = new ObjectInputStream(fis);
                outputObj = ois.readObject();

                fis.close();
                ois.close();
            }
            catch(Exception e)
            {
                LOG.error("Exception error = " + e.getMessage());
                outputObj = null;
            }
            ois = null;
            return outputObj;
        }

        public static boolean saveSerializeFile(Context context, String fileName, Object object)
        {
            if(context == null || fileName == null || fileName.length() < 0)
                return false;

            FileOutputStream fos = null;
            ObjectOutputStream oos = null;

            try 
            {
                fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                oos = new ObjectOutputStream(fos);

                oos.writeObject(object);

                fos.close();
                oos.close();
                oos = null;
                return true;

            }
            catch(FileNotFoundException e1)
            {
                LOG.error("Exception error = " + e1.getMessage());

            }
            catch(IOException e)
            {
                LOG.error("Exception error = " + e.getMessage());
            }

            oos = null;
            return false;
        }


        public static String loadStringFromAssetFile(AssetManager assetManager, String fileName) {
            try {
                InputStream in = assetManager.open(fileName);

                StringBuilder buf = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String str;

                while ((str=reader.readLine()) != null) {
                    buf.append(str);
                }
                reader.close();

                return buf.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    public static class AndroidUtil
    {
        public static void setPendingAlarmManager(Intent intent, Context context, long triggerAtTime)
        {
            PendingIntent sender = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, triggerAtTime, sender);
        }
        
        public static void setPendingAlarmManager(Intent intent, Context context, int requestCode, long triggerAtTime)
        {
            PendingIntent sender = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, triggerAtTime, sender);
        }

        public static void cancelPendingAlarmManager(Intent intent, Context context)
        {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }
        
        public static void cancelPendingAlarmManager(Intent intent, Context context, int requestCode)
        {
            PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
        }

        public static String getAppVersion(Context context)
        {
            String version = "";
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                version = "ver." + packageInfo.versionName;
            } catch(Exception e) {
                return version;
            }
            return version;
        }

        public static String getDeviceName(){
            return Build.MODEL;
        }
        
        public static boolean enableUseNetwork(Context context)
        {
            LOG.debug(">> enableUstNetwork()");
            
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
            
            if (netInfo != null && netInfo.isConnectedOrConnecting())
            {
                return true;
            }
            
            return false;
            
//            ConnectivityManager conMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//            State mobile = conMgr.getNetworkInfo(0).getState();
//            State wifi   = conMgr.getNetworkInfo(1).getState();
//
//            if (mobile.equals(State.DISCONNECTED) && wifi.equals(State.DISCONNECTED))
//            {
//                return false;
//            }
//
//            return true;
        }

        public static boolean isForeground(Context context)
        {
            LOG.debug(">>isForeground()");
            boolean result = false;

            String packageName = context.getPackageName();

            RunningAppProcessInfo info = null;
            ActivityManager actMgr = null;

            if(actMgr == null)
            {
                actMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            }

            List<RunningAppProcessInfo> l = actMgr.getRunningAppProcesses();
            Iterator<RunningAppProcessInfo> iter = l.iterator();
            while(iter.hasNext())
            {
                info = iter.next();
                if(info.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                {
                    if(info.processName.equals(packageName))
                    {
                        result = true;
                        break;
                    }
                }
            }
            
            LOG.debug("++ return "+result);
            return result;
        }

        /**
         * ���� ���÷��� ȭ�鿡 �����?DP������ Pixel�� ��ȯ�Ͽ� ��ȯ.
         * 
         * @param context
         * @param float dp��
         * @return ��ȯ�� ��(pixel)
         */
        public static int pixelFromDp(Activity activity, float dp)
        {
//            DisplayMetrics outMetrics = new DisplayMetrics();
//            activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
//            int density = outMetrics.densityDpi;
//            
//            LOG.debug("density: "+density);
//
//            return (int) (dp * ((float) density / 160));
            
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, activity.getResources().getDisplayMetrics());
        }
        
        /**
         * �ȼ������� ���� ���÷��� ȭ�鿡 �����?ũ���?��ȯ�մϴ�.
         * 
         * @param pixel �ȼ�
         * @return ��ȯ�� �� (DP)
         */
        public static int dPFromPixel(Activity activity, int pixel)
        {
          float scale = activity.getResources().getDisplayMetrics().density;
          
//          return (int)(pixel / 1.5 * scale);
          return (int)(pixel * scale + 0.5f);
        }
        
        /**
         * �̹��������� ȸ�� ������ �����Ѵ�.
         */
        public static int getImageFileDegree(String filePath)
        {
            int exifDegree = 0;
            try
            {
                // �̹����� ��Ȳ�� �°� ȸ���Ų��?
                ExifInterface exif = new ExifInterface(filePath);
                int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                           ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegrees(exifOrientation);
            }
            catch(Exception e)
            {
                LOG.error("Exception error = " + e.getMessage());
            }
            
            LOG.debug("  ++ degree = "+exifDegree);
            
            return exifDegree;
        }

        /**
         * EXIF������ ȸ���?��ȯ�Ͽ� �����Ѵ�
         */
        private static int exifOrientationToDegrees(int exifOrientation)
        {
            if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
            {
                return 90;
            }
            else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
            {
                return 180;
            }
            else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
            {
                return 270;
            }
            return 0;
        }
    }

    public static class TimeUtil
    {
        public static String getNetworkHeaderTime()
        {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            return formatter.format(date) + " GMT";
        }
        
        public static String getNetworkHeaderTimeDebug()
        {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss.SSS", Locale.US);
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            return formatter.format(date) + " GMT";
        }
        
        public static String getHourMinuteFormat(String time)
        {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
            DateFormatSymbols temp = new DateFormatSymbols();
            temp.setAmPmStrings(new String[] { "AM", "PM" });
            format.setDateFormatSymbols(temp);

            int hour = Integer.parseInt(time.substring(6, 8));
            int minute = Integer.parseInt(time.substring(8, 10));

            Date date = new Date();
            date.setHours(hour);
            date.setMinutes(minute);

            return format.format(date);
        }

        // 12byte string("10 09 31 11 50 45")
        public static String getDateFormat(String mDate)
        {
            SimpleDateFormat fomat = new SimpleDateFormat("MM.dd.yyyy hh:mm a");
            DateFormatSymbols temp = new DateFormatSymbols();
            temp.setAmPmStrings(new String[] { "AM", "PM" });
            fomat.setDateFormatSymbols(temp);

            int year = 0;
            int month = 0;
            int day = 0;
            int hour = 0;
            int min = 0;

            if(mDate.length() == 12)
            {
                year = Integer.parseInt("20" + mDate.substring(0, 2)) - 1900;
                month = Integer.parseInt(mDate.substring(2, 4));
                day = Integer.parseInt(mDate.substring(4, 6));
                hour = Integer.parseInt(mDate.substring(6, 8));
                min = Integer.parseInt(mDate.substring(8, 10));
            }
            else if(mDate.length() == 14)
            {
                year = Integer.parseInt(mDate.substring(0, 4)) - 1900;
                month = Integer.parseInt(mDate.substring(4, 6));
                day = Integer.parseInt(mDate.substring(6, 8));
                hour = Integer.parseInt(mDate.substring(8, 10));
                min = Integer.parseInt(mDate.substring(10, 12));
            }

            if(month > 0 && month < 13 && day > 0 && day < 32)
            {
                month -= 1;
                Date date = new Date(year, month, day, hour, min);

                return fomat.format(date);
            }
            else
            {
                char a = 'a';

                if((hour - 12) >= 0 && hour < 24)
                {
                    if(hour > 12)
                        hour -= 12;
                    a = 'p';
                }
                else
                {
                    if(hour == 24)
                        hour = 0;
                }

                return month + "." + day + "." + year + " " + hour + ":" + min + " " + a;
            }
        }

        public static String dateFormat4(String mDate)
        {
            SimpleDateFormat fomat = new SimpleDateFormat("MM.dd.yyyy");
            DateFormatSymbols temp = new DateFormatSymbols();
            fomat.setDateFormatSymbols(temp);

            int year = 0;
            int month = 0;
            int day = 0;
            int hour = 0;
            int min = 0;

            if(mDate.length() == 12)
            {
                year = Integer.parseInt("20" + mDate.substring(0, 2)) - 1900;
                month = Integer.parseInt(mDate.substring(2, 4));
                day = Integer.parseInt(mDate.substring(4, 6));
                hour = Integer.parseInt(mDate.substring(6, 8));
                min = Integer.parseInt(mDate.substring(8, 10));
            }
            else if(mDate.length() == 14)
            {
                year = Integer.parseInt(mDate.substring(0, 4)) - 1900;
                month = Integer.parseInt(mDate.substring(4, 6));
                day = Integer.parseInt(mDate.substring(6, 8));
                hour = Integer.parseInt(mDate.substring(8, 10));
                min = Integer.parseInt(mDate.substring(10, 12));
            }
            // Driving info ��¥ ������ ������ �� ��¥ ���� ǥ�� �����?���� �߰�
            // �����?yyMMdd) ������ ����
            // added by LKJ@20110921
            else if(mDate.length() == 6)
            {
                year = Integer.parseInt("20" + mDate.substring(0, 2)) - 1900;
                month = Integer.parseInt(mDate.substring(2, 4));
                day = Integer.parseInt(mDate.substring(4, 6));
            }

            if(month > 0 && month < 13 && day > 0 && day < 32)
            {
                month -= 1;
                Date date = new Date(year, month, day, hour, min);

                return fomat.format(date);
            }
            else
            {
                return month + "." + day + "." + year;
            }
        }

        public static String dateFormat5(String mDate)
        {
            SimpleDateFormat fomat = new SimpleDateFormat("MM.dd.yyyy  hh:mm a");
            DateFormatSymbols temp = new DateFormatSymbols();
            temp.setAmPmStrings(new String[] { "AM", "PM" });
            fomat.setDateFormatSymbols(temp);

            int year = 0;
            int month = 0;
            int day = 0;
            int hour = 0;
            int min = 0;

            if(mDate.length() == 12)
            {
                year = Integer.parseInt("20" + mDate.substring(0, 2)) - 1900;
                month = Integer.parseInt(mDate.substring(2, 4));
                day = Integer.parseInt(mDate.substring(4, 6));
                hour = Integer.parseInt(mDate.substring(6, 8));
                min = Integer.parseInt(mDate.substring(8, 10));
            }
            else if(mDate.length() == 14)
            {
                year = Integer.parseInt(mDate.substring(0, 4)) - 1900;
                month = Integer.parseInt(mDate.substring(4, 6));
                day = Integer.parseInt(mDate.substring(6, 8));
                hour = Integer.parseInt(mDate.substring(8, 10));
                min = Integer.parseInt(mDate.substring(10, 12));
            }

            if(month > 0 && month < 13 && day > 0 && day < 32)
            {
                month -= 1;
                Date date = new Date(year, month, day, hour, min);

                return fomat.format(date);
            }
            else
            {
                String a = "AM";

                if((hour - 12) >= 0 && hour < 24)
                {
                    if(hour > 12)
                        hour -= 12;
                    a = "PM";
                }
                else
                {
                    if(hour == 24)
                        hour = 0;
                }

                return month + "." + day + "." + year + "  " + hour + ":" + min + " " + a;
            }
        }

        public static int getTimeSecond(int hour, int minute, int second)
        {
            double secondTime = 0;

            if(hour != 0 && hour > 0)
                secondTime = hour * (60 * 60);

            if(minute != 0 && minute > 0)
                secondTime = secondTime + (minute * 60);

            if(second != 0 && second > 0)
                secondTime = secondTime + (second);

            return (int) Math.floor(secondTime);
        }

        public static int getHour(int second)
        {
            double hourTime = 0;

            if(second == 0 || second < 0)
                return 0;

            hourTime = second / 60 / 60;

            return (int) Math.floor(hourTime);
        }

        public static int getMinute(int second)
        {
            double minuteTime = 0;

            if(second == 0 || second < 0)
                return 0;

            double hourTime = second / 60 / 60;
            minuteTime = (second - (60 * 60) * hourTime) / 60;
            return (int) Math.floor(minuteTime);
        }

        public static int getSecond(int second)
        {
            double secondTime = 0;

            if(second == 0 || second < 0)
                return 0;

            double hourTime = second / 60 / 60;
            double minuteTime = (second - ((60 * 60) * hourTime)) / 60;

            secondTime = second - ((60 * 60) * hourTime) - (minuteTime * 60);

            return (int) Math.floor(secondTime);
        }
    }

    public static class BitmapUtil
    {
        private static int mThumnailSize = 200;

        /**
         * �����?���̳ʸ��� sampleSize �� ������. ��) �����?/ sampleSize = ���?
         */
        public static Bitmap getSampleSizeBitmap(byte[] data, int sampleSize)
        {
            Bitmap bitmap = null;

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Config.RGB_565;
            options.inSampleSize = sampleSize;

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

            return bitmap;
        }

        /**
         * ������ ���̳ʸ� �����ͷ� �����Ѵ�.
         */
        public static byte[] getImageFileData(String filePath)
        {
            FileInputStream fileInpushStream = null;
            byte[] data = null;

            if(filePath != null && filePath.length() > 0)
            {
                try
                {
                    File file = new File(filePath);

                    fileInpushStream = new FileInputStream(file);
                    data = new byte[fileInpushStream.available()];

                    while(fileInpushStream.read(data) != -1)
                    {
                        ;
                    }
                    fileInpushStream.close();

                }
                catch(FileNotFoundException e)
                {
                    LOG.error("FileNotFoundException error = " + e.getMessage());
                    data = null;
                }
                catch(IOException e)
                {
                    LOG.error("IOException error = " + e.getMessage());
                    data = null;
                }
            }
            return data;
        }

        /**
         * ���簢�� �̹����� ���簢������ �����?mThumnailSize�� ���� �ʵ��� �����Ѵ�.
         */
        public static Bitmap getSquareThumbnailBitmap(Bitmap bitmap)
        {
            Bitmap resized = null;

            int srcWidth = bitmap.getWidth();
            int srcHeight = bitmap.getHeight();

            int startWidth = 0, startHeight = 0;

            if(srcWidth > srcHeight)
            {
                startWidth = (srcWidth - srcHeight) / 2;
                srcWidth = srcHeight;
            }
            else
            {
                startHeight = (srcHeight - srcWidth) / 2;
                srcHeight = srcWidth;
            }

            Bitmap srcBitmap = Bitmap.createBitmap(bitmap, startWidth, startHeight, srcWidth,
                                                   srcHeight);
            bitmap.recycle();

            if(startWidth < mThumnailSize || startHeight < mThumnailSize)
            {
                 return srcBitmap;
            }
            else
            {
                resized = Bitmap.createScaledBitmap(srcBitmap, mThumnailSize, mThumnailSize,
                                                    false);
                srcBitmap.recycle();

                return resized;
            }
        }

        /**
         * �̹����� ȸ���Ͽ� ���� �Ѵ�.
         */
        public static Bitmap rotate(Bitmap bitmap, int degrees)
        {
            if(degrees != 0 && bitmap != null)
            {
                Matrix m = new Matrix();
                m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                            (float) bitmap.getHeight() / 2);

                try
                {

                    Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                                                           bitmap.getHeight(), m, true);
                    if(bitmap != converted)
                    {

                        bitmap.recycle();
                        bitmap = converted;
                    }
                } catch(OutOfMemoryError ex)
                {
                    // �޸𸮰� �����Ͽ� ȸ���� ��Ű�� ���� ���?�׳� ���� ��ȯ�Ѵ�.
                    LOG.error("OutOfMemoryError error = " + ex.getMessage());
                }
            }
            return bitmap;
        }
        
        // DIP ��ȯ �� ���?.
        public static double getDisplayRatio(Display display)
        {
            final int STANDARDSIZE = 320;
            
            return display.getWidth()/STANDARDSIZE;
        }
    }

    public static class SimpleFormatUtil
    {
        public static String insertEndString(byte[] buff, int offset, int size)
        {
            byte[] temp = new byte[size];

            int i = 0;
            for(i = 0; i < size; i++)
            {
                if(buff[offset + i] == '\0')
                {
                    temp[i] = '\n';
                    break;
                }
                else
                {
                    temp[i] = buff[offset + i];
                }
            }

            String sTemp = new String(buff, offset, size);
            sTemp = sTemp.substring(0, i);

            return sTemp;
        }

        /**
         * ���ڸ� �Է� �޾Ƽ� 3�ڸ� ���� ","�� �������ش�.(�Ҽ��� ����)
         * 
         * @param unit �Է¹��� ����
         * @return ","���� ��
         */
        public static String convertUnitToCommaUnit(long unit)
        {
            DecimalFormat df = new DecimalFormat("###,###");
            return df.format(unit);
        }

        /**
         * �Ѿ��?double���� 3�ڸ� ����","�� ������ �ش�.(�Ҽ��� ����)
         * 
         * @param unit �Է¹��� ����
         * @param decimalPoint �Ҽ��� ǥ���� �ڸ���
         * @return
         */
        public static String convertUnitToDecimalPointCommaUnit(double unit, int decimalPoint)
        {
            DecimalFormat df = null;

            switch(decimalPoint)
            {
                case 1:
                    df = new DecimalFormat("###,###.#");
                    break;
                case 2:
                    df = new DecimalFormat("###,###.##");
                    break;
                case 3:
                    df = new DecimalFormat("###,###.###");
                    break;
                case 4:
                    df = new DecimalFormat("###,###.####");
                    break;
                default:
                    df = new DecimalFormat("###,###.#");
                    break;
            }

            return df.format(unit);
        }

        /**
         * m�� �Ѿ��?���� km�� ��ȯ�Ѵ�.
         * 
         * @param m
         * @return
         */
        public static double convertMeterToKmUnit(long m)
        {
            return m * 0.001;
        }

        public static float convertMeterToKmUnitFloat(long m)
        {
            return m * 0.001f;
        }

        public static String removeComma(String source)
        {
            int commaIdx = 0;

            LOG.debug(source);
            while((commaIdx = source.indexOf(",")) != -1)
            {
                LOG.debug(
                      source.substring(0, commaIdx) + ", " + source.substring(commaIdx + 1));
                source = source.substring(0, commaIdx) + source.substring(commaIdx + 1);
            }
            return source;
        }
    }
    

}
