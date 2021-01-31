package com.emikhalets.datesdb.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emikhalets.datesdb.data.AppRepository.Companion.getInstance
import com.emikhalets.datesdb.data.database.DateItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DatesListViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val repository: AppRepository?
    private val liveDataDates: MutableLiveData<List<DateItem?>>
    private val liveDataNotice: MutableLiveData<String>
    fun getLiveDataDates(): LiveData<List<DateItem?>> {
        return liveDataDates
    }

    fun getLiveDataNotice(): LiveData<String> {
        return liveDataNotice
    }

    fun getAllDates(lifecycleOwner: LifecycleOwner?) {
        repository!!.allDates
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(object : DisposableSingleObserver<List<DateItem?>?>() {
                    override fun onSuccess(dateItemList: List<DateItem?>) {
                        liveDataDates.value = dateItemList
                    }

                    override fun onError(e: Throwable) {
                        liveDataNotice.value = e.toString()
                    }
                })
    }

    init {
        repository = getInstance(application!!)
        liveDataDates = MutableLiveData()
        liveDataNotice = MutableLiveData()
    }
}