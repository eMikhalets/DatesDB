package com.emikhalets.datesdb.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.emikhalets.datesdb.data.AppDatabase;
import com.emikhalets.datesdb.data.DateItem;
import com.emikhalets.datesdb.utils.Const;

import java.util.List;

public class GetAllDatesWorker extends Worker {

    public GetAllDatesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        List<DateItem> datesList = AppDatabase.getInstance(getApplicationContext()).getDatesDao()
                .getAllDates();

        int[] ids = new int[datesList.size()];
        String[] names = new String[datesList.size()];
        long[] dates = new long[datesList.size()];
        String[] types = new String[datesList.size()];

        for (int i = 0; i < datesList.size(); i++) {
            ids[i] = datesList.get(i).getId();
            names[i] = datesList.get(i).getName();
            dates[i] = datesList.get(i).getDate();
            types[i] = datesList.get(i).getType();
        }

        Data output = new Data.Builder()
                .putIntArray(Const.KEY_DATE_ID, ids)
                .putStringArray(Const.KEY_DATE_NAME, names)
                .putLongArray(Const.KEY_DATE_DATE, dates)
                .putStringArray(Const.KEY_DATE_TYPE, types)
                .build();

        return Result.success(output);
    }
}