package com.example.drivinglicenseupload.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import androidx.core.content.FileProvider;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class LogUtils {

    private static final String LOG_DIRECTORY = "BlueLinkLog";


    private static final Class<?>[] ARRAY_PRIMITIVE_TYPES = {
            int[].class, float[].class, double[].class, boolean[].class,
            byte[].class, short[].class, long[].class, char[].class};

    static Object[] getArray(Object val) {
                Class<?> valKlass = val.getClass();
                Object[] outputArray = null;

                for (Class<?> arrKlass : ARRAY_PRIMITIVE_TYPES) {
                    if (valKlass.isAssignableFrom(arrKlass)) {
                        int arrlength = Array.getLength(val);
                        outputArray = new Object[arrlength];
                        for (int i = 0; i < arrlength; ++i) {
                            outputArray[i] = Array.get(val, i);
                        }
                        break;
                    }
                }
                if (outputArray == null) // not primitive type array
                {
                    outputArray = (Object[]) val;
                }

                return outputArray;
            }

    public static void logcat(String tag, String msg)
    {

       /* if(Util.LOG_SAVE_AS_FILE && com.ubivelox.bluelink_c.BuildConfig.DEBUG) {
            //writeFileLogPerDate(className, msg);
            //Logger.d(tag, msg);
        }

        BluelinkApp.logcatBuilder.append("\n    " + msg);
        Intent logcatAction = new Intent(BaseActivity.ACTION_LOGCAT);
//        logcatAction.putExtra(BaseActivity.KEY_LOGCAT,msg);
        BluelinkApp.getApplication().sendBroadcast(logcatAction);*/
    }

    public static void errorLogcat(String className, String msg, Exception e)
    {
        Writer writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        logcat( className, msg +"\n"+writer.toString());
    }

    public static void errorLogcat(String className, String msg, Throwable t)
    {
        Writer writer = new StringWriter();
        t.printStackTrace(new PrintWriter(writer));
        logcat( className, msg +"\n"+writer.toString());
    }

    static void writeDataLog(String sourceFileName, String log, boolean isFileLogging) {
//        if (isFileLogging) {
//            fileHeungsooLog(sourceFileName, log);
//        } else {
//        if(log.length()>200)
//            log = log.substring(0,200);
        logcat(sourceFileName,log);
//        }
    }


    /**
     * 조흥수임시로그 - 파일기록
     * @param tag
     * @param text
     */
    public static void writeFileLogPerDate(String tag, String text) {
//        String DIRECTORY = "BlueLinkLog";
        File exportDir = new File(Environment.getExternalStorageDirectory(), LOG_DIRECTORY);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        //날짜별로 디렉토리를 만들어 로그를 저장하도록해보자...
        File dayDir = new File(Environment.getExternalStorageDirectory(), LOG_DIRECTORY+"/"+getHeungsooDate("yyyyMMdd"));
        if (!dayDir.exists()) {
            dayDir.mkdirs();
        }
        File logFile = new File(Environment.getExternalStorageDirectory()+ "/"+ LOG_DIRECTORY+"/"+getHeungsooDate("yyyyMMdd")+"/"+getHeungsooDate("yyyyMMdd HH시 ")+"log.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append("[" + getHeungsooDate("MM.dd HH:mm:ss") + "]" + "[" + tag + "]" + text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 조흥수임시추가
     * @return
     */
    static String getHeungsooDate(String format)
    {
        if(TextUtils.isEmpty(format))
            format = "yyyyMMdd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();
        return sdf.format(date);
    }

    public static void openLogDirectory(Context context)
    {
        Uri selectedUri;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/"+ LOG_DIRECTORY +"/");
        }
        else
        {
            File path = new File(Environment.getExternalStorageDirectory() + "/"+ LOG_DIRECTORY +"/");
            selectedUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", path);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "*/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    public static void heungsooSaveTextFile(String fileName, String text) {
        File exportDir = new File(Environment.getExternalStorageDirectory(), LOG_DIRECTORY);
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }
        //날짜별로 디렉토리를 만들어 로그를 저장하도록해보자...
        File dayDir = new File(Environment.getExternalStorageDirectory(), LOG_DIRECTORY+"/"+ getHeungsooDate("yyyyMMdd"));
        if (!dayDir.exists()) {
            dayDir.mkdirs();
        }
        File logFile = new File(Environment.getExternalStorageDirectory()+ "/"+ LOG_DIRECTORY+"/"+ getHeungsooDate("yyyyMMdd")+"/"+fileName + /*getHeungsooDate("_HHmmss")+ */".json");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, false));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 최근1주일간의 로그를 압축해서 메일로 발송.
     * @param mailAddress
     * @param context
     */
    /*public static void sendEmail(Context context, String mailAddress, String message )
    {
//        File logFile = new File(Environment.getExternalStorageDirectory()+ "/"+LOG_DIRECTORY+"/"+getHeungsooDate("yyyyMMdd")+"/"+getHeungsooDate("yyyyMMdd HH시 ")+"log.txt");
        ArrayList<String> folderList = new ArrayList<>();
        for(int i=0;i>-7;i--)
        {
            folderList.add(Environment.getExternalStorageDirectory()+ "/"+ LOG_DIRECTORY+"/"+ Util.addTimeDate(null, Calendar.DAY_OF_MONTH,i,"yyyyMMdd"));
        }
        String zipFilePath= Environment.getExternalStorageDirectory()+ "/"+ LOG_DIRECTORY+"/"+  Build.MODEL.replace("-","_") + "_" + getHeungsooDate("yyyyMMdd")+".zip";
        File zipFile = new File(zipFilePath);
        if(zipFile.isFile())
            zipFile.delete();
        Util.zipFileAtPath(folderList, zipFilePath);
        Uri zipUri = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
        {
            zipUri = Uri.fromFile(zipFile);
        }
        else
        {
            zipUri = LogFileProvider.getUriForFile(context, "com.ubivelox.bluelink_c.fileprovider", zipFile);
        }
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // set the type to 'email'
        emailIntent .setType("vnd.android.cursor.dir/email");
        String to[] = {mailAddress};
        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
        // the attachment
        emailIntent .putExtra(Intent.EXTRA_STREAM, zipUri);
        // the mail subject
        emailIntent .putExtra(Intent.EXTRA_SUBJECT, Build.MODEL + " Log 파일");
        emailIntent.putExtra(Intent.EXTRA_TEXT,message);
        context.startActivity(Intent.createChooser(emailIntent , "Send email..."));
    }*/



}
