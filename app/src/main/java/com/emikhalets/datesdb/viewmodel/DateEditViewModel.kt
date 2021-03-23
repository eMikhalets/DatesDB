package com.emikhalets.datesdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.data.entities.DateType
import com.emikhalets.datesdb.data.entities.Result
import com.emikhalets.datesdb.data.repository.DateEditRepository
import com.emikhalets.datesdb.utils.computeAge
import com.emikhalets.datesdb.utils.computeDaysLeft
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateEditViewModel(private val repository: DateEditRepository) : ViewModel() {

    private val _dateItem = MutableLiveData<DateItem>()
    val dateItem get(): LiveData<DateItem> = _dateItem

    private val _dateString = MutableLiveData<String>()
    val dateString get(): LiveData<String> = _dateString

    private val _insertingType = MutableLiveData<DateType>()
    val insertingType get(): LiveData<DateType> = _insertingType

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    private var localDateTime: LocalDateTime = LocalDateTime.now()
    var dateItemId = -1L
    var imageUri = ""

    fun getDate(id: Long) {
        viewModelScope.launch {
            when (val result = repository.getDate(id)) {
                is Result.Success -> _dateItem.postValue(result.result)
                is Result.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun setDateTime(timestamp: Long) {
        localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.of("UTC"))
    }

    fun getDateString(): String {
        return localDateTime.format(DateTimeFormatter.ofPattern("d MMM y"))
    }

    fun setDateString(date: LocalDateTime) {
        localDateTime = date
        _dateString.postValue(date.format(DateTimeFormatter.ofPattern("d MMM y")))
    }

    suspend fun insertDate(name: String, type: String) {
        withContext(Dispatchers.IO) {
            if (checkData(name, type)) {
                val dateItem = buildDateItem(name, type)
                when (val result = repository.insertDate(dateItem)) {
                    is Result.Success -> {
                    }
                    is Result.Error -> _notice.postValue(result.msg)
                }
            } else {
                _notice.postValue("Enter all data")
            }
        }
    }

    suspend fun updateDate(name: String, type: String) {
        withContext(Dispatchers.IO) {
            if (checkData(name, type)) {
                val dateItem = buildDateItem(name, type)
                when (val result = repository.updateDate(dateItem)) {
                    is Result.Success -> {
                    }
                    is Result.Error -> _notice.postValue(result.msg)
                }
            } else {
                _notice.postValue("Enter all data")
            }
        }
    }

    private fun buildDateItem(name: String, type: String): DateItem {
        val daysLeft = computeDaysLeft(localDateTime)
        val age = computeAge(localDateTime)
        return DateItem(
                name = name,
                date = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli(),
                type = type,
                daysLeft = daysLeft,
                age = age,
                image = if (imageUri == "") "" else imageUri,
                id = if (dateItemId == -1L) 0L else dateItemId
        )
    }

    private fun checkData(name: String, type: String): Boolean {
        return name.isNotEmpty() && type.isNotEmpty()
    }
}