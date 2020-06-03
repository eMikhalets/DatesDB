package com.emikhalets.datesdb.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emikhalets.datesdb.data.AppDatabase;
import com.emikhalets.datesdb.data.AppRepository;
import com.emikhalets.datesdb.data.DateItem;

import java.util.HashMap;
import java.util.Map;

public class ItemDateViewModel extends AndroidViewModel {

    private AppRepository repository;
    private MutableLiveData<DateItem> liveDataDate;
    private MutableLiveData<Map<String, String>> liveDataFields;

    public ItemDateViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(AppDatabase.getInstance(application).getDatesDao());
        liveDataDate = new MutableLiveData<>();
        liveDataFields = new MutableLiveData<>();
    }

    public LiveData<DateItem> getLiveDataDate() {
        return liveDataDate;
    }

    public LiveData<Map<String, String>> getLiveDataFields() {
        return liveDataFields;
    }

    public void update(DateItem dateitem) {
        repository.update(dateitem);
    }

    public void delete(DateItem dateitem) {
        repository.delete(dateitem);
    }

    public void setDate(DateItem dateItem) {
        liveDataDate.setValue(dateItem);
    }

    public DateItem getDate() {
        return liveDataDate.getValue();
    }

    public void setFields() {
        DateItem dateItem = liveDataDate.getValue();
        Map<String, String> map = new HashMap<>();

        if (dateItem != null) {
            map.put("name", dateItem.getName());
            map.put("date", dateItem.getDate());
            map.put("type", dateItem.getType());

            liveDataFields.setValue(map);
        }
    }
}
