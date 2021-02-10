package com.emikhalets.datesdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.data.entities.DateType
import com.emikhalets.datesdb.data.entities.ResultDb
import com.emikhalets.datesdb.data.repository.DateEditRepository
import com.emikhalets.datesdb.utils.computeAge
import com.emikhalets.datesdb.utils.computeDaysLeft
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateEditViewModel : ViewModel() {

    private val repository = DateEditRepository(
            AppDatabase.get().datesDao,
            AppDatabase.get().typesDao
    )

    private val _date = MutableLiveData<DateItem>()
    val date get(): LiveData<DateItem> = _date

    private val _insertingDate = MutableLiveData<Long>()
    val insertingDate get(): LiveData<Long> = _insertingDate

    private val _updatingDate = MutableLiveData<Int>()
    val updatingDate get(): LiveData<Int> = _updatingDate

    private val _types = MutableLiveData<List<String>>()
    val types get(): LiveData<List<String>> = _types

    private val _insertingType = MutableLiveData<DateType>()
    val insertingType get(): LiveData<DateType> = _insertingType

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    var currentDateId = -1
    val typesItems = mutableListOf("+ New type")
    var localDateTime: LocalDateTime = LocalDateTime.now()

    var image = ""
    var name = ""
    var dateField = 0L
    var isYear = true
    var type = ""

    var isDateVisible = false
    var isDateDialogOpen = false

    // To check the entered data
    private var isNameEntered = false
    private var isTypeEntered = false
    var isDateEntered = false

    fun getDate(id: Int) {
        viewModelScope.launch {
            when (val result = repository.getDate(id)) {
                is ResultDb.Success -> _date.postValue(result.result)
                is ResultDb.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun insertDate() {
        viewModelScope.launch {
            if (checkData()) {
                val dateItem = computeDate()
                when (val result = repository.insertDate(dateItem)) {
                    is ResultDb.Success -> _insertingDate.postValue(result.result)
                    is ResultDb.Error -> _notice.postValue(result.msg)
                }
            } else {
                _notice.postValue("Enter all data")
            }
        }
    }

    fun updateDate() {
        viewModelScope.launch {
            if (checkData()) {
                val dateItem = DateItem(
                        name = name,
                        date = dateField,
                        type = type,
                        daysLeft = 1,
                        age = 1,
                        isYear = false,
                        image = image,
                        id = currentDateId
                )
                when (val result = repository.updateDate(dateItem)) {
                    is ResultDb.Success -> _updatingDate.postValue(result.result)
                    is ResultDb.Error -> _notice.postValue(result.msg)
                }
            } else {
                _notice.postValue("Enter all data")
            }
        }
    }

    fun getAllTypes() {
        viewModelScope.launch {
            when (val result = repository.getAllTypes()) {
                is ResultDb.Success -> {
                    val list = result.result.map { it.name }
                    typesItems.addAll(list)
                    _types.postValue(list)
                }
                is ResultDb.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun insertType(name: String) {
        viewModelScope.launch {
            val type = DateType(name, 0)
            when (val result = repository.insertType(type)) {
                is ResultDb.Success -> {
                    typesItems.add(type.name)
                    _insertingType.postValue(type)
                }
                is ResultDb.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun formatDateString(): String {
        if (isDateVisible) {
            return if (isYear) {
                localDateTime.format(DateTimeFormatter.ofPattern("d MMM y"))
            } else {
                localDateTime.format(DateTimeFormatter.ofPattern("d MMM"))
            }
        }
        return ""
    }

    fun setDateTime(timestamp: Long) {
        localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.of("UTC")
        )
    }

    private fun checkData(): Boolean {
        isNameEntered = name.isNotEmpty()
        isTypeEntered = type.isNotEmpty()
        return isNameEntered && isDateEntered && isTypeEntered
    }

    private fun computeDate(): DateItem {
        val daysLeft = computeDaysLeft(localDateTime)
        val age = computeAge(localDateTime)
        val date = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        return DateItem(name, date, type, daysLeft, age, isYear, image)
    }
}