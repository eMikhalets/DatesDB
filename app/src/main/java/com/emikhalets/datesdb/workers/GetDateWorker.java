package com.emikhalets.datesdb.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.emikhalets.datesdb.data.AppDatabase;
import com.emikhalets.datesdb.data.DateItem;
import com.emikhalets.datesdb.utils.Const;

public class GetDateWorker extends Worker {

    public GetDateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        int id = getInputData().getInt(Const.KEY_DATE_ID, -1);

        DateItem dateItem =
                AppDatabase.getInstance(getApplicationContext()).getDatesDao().getDate(id);

        Data output = new Data.Builder()
                .putInt(Const.KEY_DATE_ID, dateItem.getId())
                .putString(Const.KEY_DATE_NAME, dateItem.getName())
                .putLong(Const.KEY_DATE_DATE, dateItem.getDate())
                .putString(Const.KEY_DATE_TYPE, dateItem.getType())
                .build();

        return Result.success(output);
    }
}