package com.emikhalets.datesdb.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.database.DateItem
import com.emikhalets.datesdb.data.database.DbResult
import com.emikhalets.datesdb.data.repository.DateEditRepository
import kotlinx.coroutines.launch

class DateEditViewModel : ViewModel() {

    private val repository = DateEditRepository(AppDatabase.get().datesDao)

    private val _date = MutableLiveData<DateItem>()
    val date get(): LiveData<DateItem> = _date

    private val _updating = MutableLiveData<Int>()
    val updating get(): LiveData<Int> = _updating

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

    fun update(name: String, date: Long, type: String) {
        viewModelScope.launch {
            if (checkData(name, date, type)) {
                val dateItem = DateItem(name, date, type)
                when (val result = repository.update(dateItem)) {
                    is DbResult.Success -> _updating.postValue(result.result)
                    is DbResult.Error -> _notice.postValue(result.msg)
                }
            }
        }
    }

    private fun checkData(name: String, date: Long, type: String): Boolean {
        return name.isNotEmpty() && date > 0 && type.isNotEmpty()
    }
}