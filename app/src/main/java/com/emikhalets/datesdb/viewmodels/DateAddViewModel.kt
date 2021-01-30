package com.emikhalets.datesdb.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.emikhalets.datesdb.data.AppRepository
import com.emikhalets.datesdb.data.AppRepository.Companion.getInstance

class DateAddViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val repository: AppRepository?
    private val liveDataNotice: MutableLiveData<String>
    fun getLiveDataNotice(): LiveData<String> {
        return liveDataNotice
    }

    fun insert(lifecycleOwner: LifecycleOwner?, name: String?, date: Long, type: String?) {
        val id = repository!!.insert(name, date, type)
        WorkManager.getInstance(getApplication())
                .getWorkInfoByIdLiveData(id)
                .observe(lifecycleOwner!!, { info: WorkInfo ->
                    if (info.state == WorkInfo.State.SUCCEEDED) {
                        liveDataNotice.value = "OK"
                    }
                })
    }

    init {
        repository = getInstance(application!!)
        liveDataNotice = MutableLiveData()
    }
}