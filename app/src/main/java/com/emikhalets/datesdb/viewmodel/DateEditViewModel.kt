package com.emikhalets.datesdb.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.data.entities.ResultDb
import com.emikhalets.datesdb.data.repository.DateEditRepository
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DateEditViewModel : ViewModel() {

    private val repository = DateEditRepository(
            AppDatabase.get().datesDao,
            AppDatabase.get().typesDao
    )

    private val _date = MutableLiveData<DateItem>()
    val date get(): LiveData<DateItem> = _date

    private val _updating = MutableLiveData<Int>()
    val updating get(): LiveData<Int> = _updating

    private val _types = MutableLiveData<List<String>>()
    val types get(): LiveData<List<String>> = _types

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    val typeItems = mutableListOf("+ New type")
    var dateTime: LocalDateTime = LocalDateTime.now()
    var isYear = true
    var isDateVisible = false
    var isDateDialogOpen = false
    var id = -1
    var imageUri: String = ""

    // To check the entered data
    private var isNameEntered = false
    private var isTypeEntered = false

    fun getDate(id: Int) {
        viewModelScope.launch {
            when (val result = repository.getDate(id)) {
                is ResultDb.Success -> _date.postValue(result.result)
                is ResultDb.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun getAllTypes() {
        viewModelScope.launch {
            when (val result = repository.getAllTypes()) {
                is ResultDb.Success -> {
                    val list = result.result.map { it.name }
                    typeItems.addAll(list)
                    _types.postValue(list)
                }
                is ResultDb.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun update(name: String, date: Long, type: String) {
        viewModelScope.launch {
            if (checkData(name, type)) {
                val dateItem = DateItem(name, date, type, 1, 1, false, imageUri, id)
                when (val result = repository.update(dateItem)) {
                    is ResultDb.Success -> _updating.postValue(result.result)
                    is ResultDb.Error -> _notice.postValue(result.msg)
                }
            } else {
                _notice.postValue("Enter all data")
            }
        }
    }

    fun formatDateString(): String {
        if (isDateVisible) {
            return if (isYear) {
                dateTime.format(DateTimeFormatter.ofPattern("d MMM y"))
            } else {
                dateTime.format(DateTimeFormatter.ofPattern("d MMM"))
            }
        }
        return ""
    }

    fun setDateTime(timestamp: Long) {
        dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.of("UTC")
        )
    }

    private fun checkData(name: String, type: String): Boolean {
        isNameEntered = name.isNotEmpty()
        isTypeEntered = type.isNotEmpty()
        return isNameEntered && isTypeEntered
    }
}