package com.emikhalets.datesdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.data.entities.DateType
import com.emikhalets.datesdb.data.entities.ResultDb
import com.emikhalets.datesdb.data.repository.DatesListRepository
import com.emikhalets.datesdb.utils.computeAge
import com.emikhalets.datesdb.utils.computeDaysLeft
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DatesListViewModel : ViewModel() {

    private val repository = DatesListRepository(
            AppDatabase.get().typesDao,
            AppDatabase.get().datesDao
    )

    private val _dates = MutableLiveData<List<DateItem>>()
    val dates get(): LiveData<List<DateItem>> = _dates

    private val _defTypesCreating = MutableLiveData<Boolean>()
    val defTypesCreating get(): LiveData<Boolean> = _defTypesCreating

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    fun getAllDates() {
        viewModelScope.launch {
            when (val result = repository.getAllDates()) {
                is ResultDb.Success -> {
                    val list = result.result
                    computeAndUpdateDates(list)
                    list.sortedBy { it.daysLeft }
                    _dates.postValue(result.result)
                }
                is ResultDb.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun createDefaultTypesTable(name1: String, res1: Int, name2: String, res2: Int, name3: String, res3: Int) {
        viewModelScope.launch {
            val list = mutableListOf<DateType>()
            list.add(DateType(name1, res1))
            list.add(DateType(name2, res2))
            list.add(DateType(name3, res3))
            when (val result = repository.insertTypes(list)) {
                is ResultDb.Success -> _defTypesCreating.postValue(true)
                is ResultDb.Error -> _notice.postValue(result.msg)
            }
        }
    }

    private fun computeAndUpdateDates(list: List<DateItem>) {
        for (i in list.indices) {
            val selected = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(list[i].date),
                    ZoneId.of("UTC")
            )
            val oldDaysLeft = list[i].daysLeft
            val oldAge = list[i].age
            list[i].apply {
                daysLeft = computeDaysLeft(selected)
                if (list[i].isYear) list[i].age = computeAge(selected)
            }

            if (oldDaysLeft != list[i].daysLeft || oldAge != list[i].age) {
                viewModelScope.launch { repository.update(list[i]) }
            }
        }
    }
}