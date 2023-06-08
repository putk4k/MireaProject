package ru.mirea.vikulov.mireaproject.ui.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class UploadWorker extends Worker {
    static final String TAG = "UploadWorker";
    public UploadWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: start");
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Прошло 60 секунд");
        Log.d(TAG, "doWork: end");
        return Result.success();
    }
}
