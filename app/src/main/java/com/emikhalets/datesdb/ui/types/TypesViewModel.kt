package com.emikhalets.datesdb.ui.types

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DateType
import com.emikhalets.datesdb.data.repository.DateEditRepository
import com.emikhalets.datesdb.utils.computeAge
import com.emikhalets.datesdb.utils.computeDaysLeft
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class TypesViewModel(private val repository: DateEditRepository) : ViewModel() {

    private val _dateItem = MutableLiveData<DateItem>()
    val dateItem get(): LiveData<DateItem> = _dateItem

    private val _dateString = MutableLiveData<String>()
    val dateString get(): LiveData<String> = _dateString

    private val _insertingType = MutableLiveData<DateType>()
    val insertingType get(): LiveData<DateType> = _insertingType

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    var dateItemId = -1L
    var imageUri = ""
    val typesItems = mutableListOf("+ New type")
    private var localDateTime: LocalDateTime = LocalDateTime.now()

    var name = ""
    var isYear = true
    var type = ""

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

    fun getAllTypes() {
        viewModelScope.launch {
            when (val result = repository.getAllTypes()) {
                is Result.Success -> {
                    val list = result.result.map { it.name }
                    typesItems.addAll(list)
                    _types.postValue(list)
                }
                is Result.Error -> _notice.postValue(result.msg)
            }
        }
    }

    fun insertType(name: String) {
        viewModelScope.launch {
            val type = DateType(name, 0)
            when (val result = repository.insertType(type)) {
                is Result.Success -> {
                    typesItems.add(type.name)
                    _insertingType.postValue(type)
                }
                is Result.Error -> _notice.postValue(result.msg)
            }
        }
    }

    private fun formatDateString(date: LocalDateTime): String {
        return if (isYear) date.format(DateTimeFormatter.ofPattern("d MMM y"))
        else date.format(DateTimeFormatter.ofPattern("d MMM"))
    }

    private fun checkData(): Boolean {
        return name.isNotEmpty() && type.isNotEmpty()
    }
}