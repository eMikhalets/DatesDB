package com.emikhalets.datesdb.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.emikhalets.datesdb.data.AppDatabase;
import com.emikhalets.datesdb.data.AppRepository;
import com.emikhalets.datesdb.data.DateItem;

public class DateEditViewModel extends AndroidViewModel {

    private AppRepository repository;

    public DateEditViewModel(Application application) {
        super(application);

        repository = new AppRepository(AppDatabase.getInstance(application).getDatesDao());
    }

    public void insert(String name, String date, String type) {
        DateItem dateitem = new DateItem(name, date, type);
        repository.insert(dateitem);
    }
}
