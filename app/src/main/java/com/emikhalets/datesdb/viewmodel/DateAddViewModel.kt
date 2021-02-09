package com.emikhalets.datesdb.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.data.entities.DateType
import com.emikhalets.datesdb.data.entities.ResultDb
import com.emikhalets.datesdb.data.repository.DateAddRepository
import com.emikhalets.datesdb.utils.computeAge
import com.emikhalets.datesdb.utils.computeDaysLeft
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateAddViewModel : ViewModel() {

    private val repository = DateAddRepository(
            AppDatabase.get().datesDao,
            AppDatabase.get().typesDao
    )

    private val _adding = MutableLiveData<Long>()
    val adding get(): LiveData<Long> = _adding

    private val _types = MutableLiveData<List<String>>()
    val types get(): LiveData<List<String>> = _types

    private val _typeAdding = MutableLiveData<DateType>()
    val typeAdding get(): LiveData<DateType> = _typeAdding

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    val typeItems = mutableListOf("+ New type")
    var imageUri: Uri? = null
    var dateTime: LocalDateTime = LocalDateTime.now()
    var isYear = true
    var isDateVisible = false
    var isDateDialogOpen = false

    // To check the entered data
    private var isNameEntered = false
    private var isTypeEntered = false

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

    fun insert(name: String, type: String) {
        viewModelScope.launch {
            if (checkData(name, type)) {
                val dateItem = computeDate(name, type)
                when (val result = repository.insert(dateItem)) {
                    is ResultDb.Success -> _adding.postValue(result.result)
                    is ResultDb.Error -> _notice.postValue(result.msg)
                }
            } else {
                _notice.postValue("Enter all data")
            }
        }
    }

    fun insertType(name: String) {
        viewModelScope.launch {
            val type = DateType(name, 0)
            when (val result = repository.insertType(type)) {
                is ResultDb.Success -> {
                    typeItems.add(type.name)
                    _typeAdding.postValue(type)
                }
                is ResultDb.Error -> _notice.postValue(result.msg)
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

    private fun checkData(name: String, type: String): Boolean {
        isNameEntered = name.isNotEmpty()
        isTypeEntered = type.isNotEmpty()
        return isNameEntered && isTypeEntered
    }

    private fun computeDate(name: String, type: String): DateItem {
        val daysLeft = computeDaysLeft(dateTime)
        val age = computeAge(dateTime)
        val date = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli()
        return DateItem(name, date, type, daysLeft, age, isYear, imageUri.toString())
    }
}