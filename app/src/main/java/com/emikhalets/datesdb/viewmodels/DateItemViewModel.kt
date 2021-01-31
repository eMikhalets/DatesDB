package com.emikhalets.datesdb.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.emikhalets.datesdb.data.AppRepository.Companion.getInstance
import com.emikhalets.datesdb.data.database.DateItem
import com.emikhalets.datesdb.utils.Const

class DateItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository?
    private val liveDataNotice: MutableLiveData<String>
    var dateItem: DateItem?
    fun getLiveDataNotice(): LiveData<String> {
        return liveDataNotice
    }

    fun getDate(lifecycleOwner: LifecycleOwner?, dateId: Int) {
        val id = repository!!.getDate(dateId)
        WorkManager.getInstance(getApplication())
                .getWorkInfoByIdLiveData(id)
                .observe(lifecycleOwner!!, { info: WorkInfo ->
                    if (info.state == WorkInfo.State.SUCCEEDED) {
                        dateItem = DateItem(
                                info.outputData.getInt(Const.KEY_DATE_ID, -1),
                                info.outputData.getString(Const.KEY_DATE_NAME)!!,
                                info.outputData.getLong(Const.KEY_DATE_DATE, -1),
                                info.outputData.getString(Const.KEY_DATE_TYPE)!!
                        )
                        liveDataNotice.value = "DATE_ITEM"
                    }
                })
    }

    fun delete(lifecycleOwner: LifecycleOwner?) {
        if (dateItem != null) {
            val id = repository!!.delete(dateItem!!)
            WorkManager.getInstance(getApplication())
                    .getWorkInfoByIdLiveData(id)
                    .observe(lifecycleOwner!!, { info: WorkInfo ->
                        if (info.state == WorkInfo.State.SUCCEEDED) {
                            liveDataNotice.value = "DELETED"
                        }
                    })
        }
    }

    init {
        repository = getInstance(application)
        liveDataNotice = MutableLiveData()
        dateItem = null
    }
}