package com.example.mylicenseregistry.log;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomizedExceptionHandler implements UncaughtExceptionHandler {
    private UncaughtExceptionHandler defaultUEH;
    private String dirPath;

    public CustomizedExceptionHandler(String dirPath) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.dirPath = dirPath;

    }

    public void uncaughtException(Thread t, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();

        String report = e.toString() + "\n\n";
        report += "--------- Stack trace ---------\n\n";
        for (int i = 0; i < arr.length; i++) {
            report += "    " + arr[i].toString() + "\n";
        }
        report += "-------------------------------\n\n";

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if (cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i = 0; i < arr.length; i++) {
                report += "    " + arr[i].toString() + "\n";
            }
        }
        report += "-------------------------------\n\n";

        writeToFile(report);

        // Used only to prevent from any code getting executed.
        // Not needed in this example
        defaultUEH.uncaughtException(t, e);
    }

    private void writeToFile(String currentStacktrace) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory(), dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            Date date = new Date();
            String filename = dateFormat.format(date) + ".STACKTRACE";

            File reportFile = new File(dir, filename);
            FileWriter fileWriter = new FileWriter(reportFile);
            fileWriter.append(currentStacktrace);
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            Log.e("ExceptionHandler", e.getMessage());
        }
    }
}
