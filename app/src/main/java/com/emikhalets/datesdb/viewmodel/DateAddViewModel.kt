package com.emikhalets.datesdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.model.database.AppDatabase
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DbResult
import com.emikhalets.datesdb.model.repository.DateAddRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime

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

    fun insert(name: String, date: Long, type: String) {
        viewModelScope.launch {
            if (checkData(name, type)) {
                val dateItem = DateItem(name, date, type)
                when (val result = repository.insert(dateItem)) {
                    is DbResult.Success -> _adding.postValue(result.result)
                    is DbResult.Error -> _notice.postValue(result.msg)
                }
            } else {
                _notice.postValue("Enter all data")
            }
        }
    }

    private fun checkData(name: String, type: String): Boolean {
        isNameEntered = name.isNotEmpty()
        isTypeEntered = type.isNotEmpty()
        return isNameEntered && isTypeEntered
    }
}