package com.emikhalets.datesdb.ui.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.database.DateItem
import com.emikhalets.datesdb.data.database.DbResult
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

    fun getDate(id: Int) {
        viewModelScope.launch {
            when (val result = repository.getDate(id)) {
                is DbResult.Success -> _date.postValue(result.result)
                is DbResult.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            val dateItem = _date.value
            dateItem?.let {
                when (val result = repository.delete(it)) {
                    is DbResult.Success -> _deleting.postValue(result.result)
                    is DbResult.Error -> _notice.postValue(result.msg)
                }
            }
        }
    }
}