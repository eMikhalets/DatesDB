package com.emikhalets.datesdb.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.model.database.AppDatabase
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DbResult
import com.emikhalets.datesdb.model.repository.DateAddRepository
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

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    val typeItems = mutableListOf("+ New type")
    var dateTime: LocalDateTime = LocalDateTime.now()
    var isDateVisible = false
    var isYear = true
    var imageUri: Uri? = null

    private var isNameEntered = false
    private var isTypeEntered = false

    fun getAllTypes() {
        viewModelScope.launch {
            when (val result = repository.getAllTypes()) {
                is DbResult.Success -> {
                    val list = result.result.map { it.name }
                    typeItems.addAll(list)
                    _types.postValue(list)
                }
                is DbResult.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun insert(name: String, type: String) {
        viewModelScope.launch {
            if (checkData(name, type)) {
                val dateItem = computeDate(name, type)
                when (val result = repository.insert(dateItem)) {
                    is DbResult.Success -> _adding.postValue(result.result)
                    is DbResult.Error -> _notice.postValue(result.msg)
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