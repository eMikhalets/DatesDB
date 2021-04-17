package com.emikhalets.datesdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.model.database.AppDatabase
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.data.repository.DateItemRepository
import kotlinx.coroutines.launch

class DateItemViewModel : ViewModel() {

    private val repository = DateItemRepository(AppDatabase.get().datesDao)

    private val _date = MutableLiveData<DateItem>()
    val date get(): LiveData<DateItem> = _date

    private val _deleting = MutableLiveData<Int>()
    val deleting get(): LiveData<Int> = _deleting

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    var id: Int = -1

    fun getDate(id: Int) {
        viewModelScope.launch {
            when (val result = repository.getDate(id)) {
                is com.emikhalets.datesdb.data.entities.ResultDb.Result.Success -> _date.postValue(result.result)
                is com.emikhalets.datesdb.data.entities.ResultDb.Result.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            val dateItem = _date.value
            dateItem?.let {
                when (val result = repository.delete(it)) {
                    is com.emikhalets.datesdb.data.entities.ResultDb.Result.Success -> _deleting.postValue(result.result)
                    is com.emikhalets.datesdb.data.entities.ResultDb.Result.Error -> _notice.postValue(result.msg)
                }
            }
        }
    }
}