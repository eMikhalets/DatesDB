package com.emikhalets.datesdb.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emikhalets.datesdb.data.AppDatabase;
import com.emikhalets.datesdb.data.AppRepository;
import com.emikhalets.datesdb.data.DateItem;

import java.util.List;

public class DatesListViewModel extends AndroidViewModel {

    private AppRepository repository;
    private MutableLiveData<List<DateItem>> liveDataDatesList;
    private MutableLiveData<DateItem> liveDataDate;

    public DatesListViewModel(Application application) {
        super(application);

        repository = new AppRepository(AppDatabase.getInstance(application).getDatesDao());
        liveDataDatesList = new MutableLiveData<>();
        liveDataDate = new MutableLiveData<>();
    }

    public LiveData<List<DateItem>> getLiveDataDatesList() {
        return liveDataDatesList;
    }

    public LiveData<DateItem> getLiveDataDate() {
        return liveDataDate;
    }

    public void insert(String name, String date, String type) {
        DateItem dateitem = new DateItem(name, date, type);
        repository.insert(dateitem);
    }

    public void update(String name, String date, String type) {
        DateItem dateitem = new DateItem(name, date, type);
        repository.update(dateitem);
    }

    public void delete(String name, String date, String type) {
        DateItem dateitem = new DateItem(name, date, type);
        repository.update(dateitem);
    }

    public void getAllDates() {
        liveDataDatesList.setValue(repository.getAllDates());
    }

    public void getDate(int id) {
        liveDataDate.setValue(repository.getDate(id));
    }
}
