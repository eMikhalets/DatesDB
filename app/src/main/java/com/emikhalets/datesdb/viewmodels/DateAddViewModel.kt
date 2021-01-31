package com.emikhalets.datesdb.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.database.DateItem
import com.emikhalets.datesdb.data.database.DbResult
import com.emikhalets.datesdb.data.repository.DateAddRepository
import kotlinx.coroutines.launch

class DateAddViewModel : ViewModel() {

    private val repository = DateAddRepository(AppDatabase.get().datesDao)

    private val _adding = MutableLiveData<Long>()
    val adding get(): LiveData<Long> = _adding

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    fun insert(name: String, date: Long, type: String) {
        viewModelScope.launch {
            val dateItem = DateItem(name, date, type)
            when (val result = repository.insert(dateItem)) {
                is DbResult.Success -> _adding.postValue(result.result)
                is DbResult.Error -> _notice.postValue(result.msg)
            }
        }
    }
}