package com.emikhalets.datesdb.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emikhalets.datesdb.data.AppRepository;
import com.emikhalets.datesdb.data.DateItem;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DatesListViewModel extends AndroidViewModel {

    private AppRepository repository;
    private MutableLiveData<List<DateItem>> liveDataDates;
    private MutableLiveData<String> liveDataNotice;

    public DatesListViewModel(Application application) {
        super(application);

        repository = AppRepository.getInstance(application);

        liveDataDates = new MutableLiveData<>();
        liveDataNotice = new MutableLiveData<>();
    }

    public LiveData<List<DateItem>> getLiveDataDates() {
        return liveDataDates;
    }

    public LiveData<String> getLiveDataNotice() {
        return liveDataNotice;
    }

    public void getAllDates(LifecycleOwner lifecycleOwner) {
        repository.getAllDates()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<DateItem>>() {
                    @Override
                    public void onSuccess(List<DateItem> dateItemList) {
                        liveDataDates.setValue(dateItemList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveDataNotice.setValue(e.toString());
                    }
                });
    }
}