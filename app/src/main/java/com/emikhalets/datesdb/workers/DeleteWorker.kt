package com.emikhalets.datesdb.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.emikhalets.datesdb.data.AppDatabase;
import com.emikhalets.datesdb.data.DateItem;
import com.emikhalets.datesdb.utils.Const;

public class DeleteWorker extends Worker {

    public DeleteWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        DateItem dateItem = new DateItem(
                getInputData().getInt(Const.KEY_DATE_ID, -1),
                getInputData().getString(Const.KEY_DATE_NAME),
                getInputData().getLong(Const.KEY_DATE_DATE, -1),
                getInputData().getString(Const.KEY_DATE_TYPE));
        AppDatabase.getInstance(getApplicationContext()).getDatesDao().delete(dateItem);
        return Result.success();
    }
}
