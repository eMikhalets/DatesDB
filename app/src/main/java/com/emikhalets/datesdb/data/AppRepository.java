package com.emikhalets.datesdb.data;

import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.emikhalets.datesdb.utils.Const;
import com.emikhalets.datesdb.workers.DeleteWorker;
import com.emikhalets.datesdb.workers.GetAllDatesWorker;
import com.emikhalets.datesdb.workers.GetDateWorker;
import com.emikhalets.datesdb.workers.InsertWorker;
import com.emikhalets.datesdb.workers.UpdateWorker;

import java.util.UUID;

public class AppRepository {

    private Context context;
    private static AppRepository instance;

    private AppRepository(Context context) {
        this.context = context;
    }

    public static synchronized AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }
        return instance;
    }

    public UUID getAllDates() {
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(GetAllDatesWorker.class)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);

        return workRequest.getId();
    }

    public UUID getDate(int id) {
        Data data = new Data.Builder()
                .putInt(Const.KEY_DATE_ID, id)
                .build();

        WorkRequest workRequest = new OneTimeWorkRequest.Builder(GetDateWorker.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);

        return workRequest.getId();
    }

    public UUID insert(String name, long date, String type) {
        Data data = new Data.Builder()
                .putString(Const.KEY_DATE_NAME, name)
                .putLong(Const.KEY_DATE_DATE, date)
                .putString(Const.KEY_DATE_TYPE, type)
                .build();

        WorkRequest workRequest = new OneTimeWorkRequest.Builder(InsertWorker.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);

        return workRequest.getId();
    }

    public UUID update(DateItem dateItem) {
        Data data = new Data.Builder()
                .putInt(Const.KEY_DATE_ID, dateItem.getId())
                .putString(Const.KEY_DATE_NAME, dateItem.getName())
                .putLong(Const.KEY_DATE_DATE, dateItem.getDate())
                .putString(Const.KEY_DATE_TYPE, dateItem.getType())
                .build();

        WorkRequest workRequest = new OneTimeWorkRequest.Builder(UpdateWorker.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);

        return workRequest.getId();
    }

    public UUID delete(DateItem dateItem) {
        Data data = new Data.Builder()
                .putInt(Const.KEY_DATE_ID, dateItem.getId())
                .putString(Const.KEY_DATE_NAME, dateItem.getName())
                .putLong(Const.KEY_DATE_DATE, dateItem.getDate())
                .putString(Const.KEY_DATE_TYPE, dateItem.getType())
                .build();

        WorkRequest workRequest = new OneTimeWorkRequest.Builder(DeleteWorker.class)
                .setInputData(data)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);

        return workRequest.getId();
    }
}
