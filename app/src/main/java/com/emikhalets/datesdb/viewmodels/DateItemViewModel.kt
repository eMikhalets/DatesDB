package com.emikhalets.datesdb.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.emikhalets.datesdb.data.AppRepository;
import com.emikhalets.datesdb.data.DateItem;
import com.emikhalets.datesdb.utils.Const;

import java.util.UUID;

public class DateItemViewModel extends AndroidViewModel {

    private AppRepository repository;
    private MutableLiveData<String> liveDataNotice;
    private DateItem dateItem;

    public DateItemViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application);

        liveDataNotice = new MutableLiveData<>();
        dateItem = null;
    }

    public LiveData<String> getLiveDataNotice() {
        return liveDataNotice;
    }

    public DateItem getDateItem() {
        return dateItem;
    }

    public void setDateItem(DateItem dateItem) {
        this.dateItem = dateItem;
    }

    public void getDate(LifecycleOwner lifecycleOwner, int dateId) {
        UUID id = repository.getDate(dateId);

        WorkManager.getInstance(getApplication())
                .getWorkInfoByIdLiveData(id)
                .observe(lifecycleOwner, info -> {
                    if (info.getState() == WorkInfo.State.SUCCEEDED) {
                        setDateItem(new DateItem(
                                info.getOutputData().getInt(Const.KEY_DATE_ID, -1),
                                info.getOutputData().getString(Const.KEY_DATE_NAME),
                                info.getOutputData().getLong(Const.KEY_DATE_DATE, -1),
                                info.getOutputData().getString(Const.KEY_DATE_TYPE)
                        ));

                        liveDataNotice.setValue("DATE_ITEM");
                    }
                });
    }

    public void delete(LifecycleOwner lifecycleOwner) {
        if (dateItem != null) {
            UUID id = repository.delete(dateItem);

            WorkManager.getInstance(getApplication())
                    .getWorkInfoByIdLiveData(id)
                    .observe(lifecycleOwner, info -> {
                        if (info.getState() == WorkInfo.State.SUCCEEDED) {
                            liveDataNotice.setValue("DELETED");
                        }
                    });
        }
    }
}
