package com.emikhalets.datesdb.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.emikhalets.datesdb.data.AppRepository;

public class DateEditViewModel extends AndroidViewModel {

    private AppRepository repository;

    public DateEditViewModel(Application application) {
        super(application);

        repository = AppRepository.getInstance(application);
    }
}
