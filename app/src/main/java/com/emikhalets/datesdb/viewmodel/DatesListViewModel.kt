package com.emikhalets.datesdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.data.entities.Result
import com.emikhalets.datesdb.data.repository.DatesListRepository
import com.emikhalets.datesdb.utils.computeAge
import com.emikhalets.datesdb.utils.computeDaysLeft
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DatesListViewModel(private val repository: DatesListRepository) : ViewModel() {

    private val _dates = MutableLiveData<List<DateItem>>()
    val dates get(): LiveData<List<DateItem>> = _dates

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    fun getAllDates() {
        viewModelScope.launch {
            when (val result = repository.getAllDates()) {
                is Result.Success -> {
                    val list = result.result
                    computeAndUpdateDates(list)
                    list.sortedBy { it.daysLeft }
                    _dates.postValue(result.result)
                }
                is Result.Error -> _notice.postValue(result.msg)
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