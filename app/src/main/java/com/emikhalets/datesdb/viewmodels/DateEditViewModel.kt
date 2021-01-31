package com.emikhalets.datesdb.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.emikhalets.datesdb.data.AppRepository.Companion.getInstance

class DateEditViewModel(application: Application?) : AndroidViewModel(application!!) {
    private val repository: AppRepository?

    init {
        repository = getInstance(application!!)
    }
}