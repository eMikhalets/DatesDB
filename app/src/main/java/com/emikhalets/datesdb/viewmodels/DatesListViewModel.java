package com.emikhalets.datesdb.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.emikhalets.datesdb.data.AppRepository;
import com.emikhalets.datesdb.data.DateItem;
import com.emikhalets.datesdb.utils.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatesListViewModel extends AndroidViewModel {

    private AppRepository repository;
    private MutableLiveData<List<DateItem>> liveDataDates;

    public DatesListViewModel(Application application) {
        super(application);

        repository = AppRepository.getInstance(application);

        liveDataDates = new MutableLiveData<>();
    }

    public LiveData<List<DateItem>> getLiveDataDates() {
        return liveDataDates;
    }

    public void getAllDates(LifecycleOwner lifecycleOwner) {
        UUID id = repository.getAllDates();

        WorkManager.getInstance(getApplication()).getWorkInfoByIdLiveData(id)
                .observe(lifecycleOwner, info -> {
                    if (info.getState() == WorkInfo.State.SUCCEEDED) {
                        List<DateItem> datesList = new ArrayList<>();

                        int[] ids = info.getOutputData().getIntArray(Const.KEY_DATE_ID);
                        String[] names = info.getOutputData().getStringArray(Const.KEY_DATE_NAME);
                        long[] dates = info.getOutputData().getLongArray(Const.KEY_DATE_DATE);
                        String[] types = info.getOutputData().getStringArray(Const.KEY_DATE_TYPE);

                        for (int i = 0; i < ids.length; i++) {
                            DateItem dateItem = new DateItem(ids[i], names[i], dates[i], types[i]);
                            datesList.add(dateItem);
                        }

                        liveDataDates.setValue(datesList);
                    }
                });
    }
}