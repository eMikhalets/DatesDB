package com.emikhalets.datesdb.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.emikhalets.datesdb.data.AppRepository;

import java.util.UUID;

public class DateAddViewModel extends AndroidViewModel {

    private AppRepository repository;
    private MutableLiveData<String> liveDataNotice;

    public DateAddViewModel(Application application) {
        super(application);

        repository = AppRepository.getInstance(application);
        liveDataNotice = new MutableLiveData<>();
    }

    public LiveData<String> getLiveDataNotice() {
        return liveDataNotice;
    }

    public void insert(LifecycleOwner lifecycleOwner, String name, long date, String type) {
        UUID id = repository.insert(name, date, type);

        WorkManager.getInstance(getApplication())
                .getWorkInfoByIdLiveData(id)
                .observe(lifecycleOwner, info -> {
                    if (info.getState() == WorkInfo.State.SUCCEEDED) {
                        liveDataNotice.setValue("OK");
                    }
                });
    }
}