package com.emikhalets.datesdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.model.database.AppDatabase
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DbResult
import com.emikhalets.datesdb.model.repository.DatesListRepository
import kotlinx.coroutines.launch

class DatesListViewModel : ViewModel() {

    private val repository = DatesListRepository(AppDatabase.get().datesDao)

    private val _dates = MutableLiveData<List<DateItem>>()
    val dates get(): LiveData<List<DateItem>> = _dates

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    fun getAllDates() {
        viewModelScope.launch {
            when (val result = repository.getAllDates()) {
                is DbResult.Success -> _dates.postValue(result.result)
                is DbResult.Error -> _notice.postValue(result.msg)
            }
        }
    }
}