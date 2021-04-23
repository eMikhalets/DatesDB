package com.emikhalets.datesdb.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emikhalets.datesdb.model.entities.DateType
import com.emikhalets.datesdb.data.repository.ActivityRepository
import kotlinx.coroutines.launch

class ActivityViewModel(private val repository: ActivityRepository) : ViewModel() {

    private val _defTypesCreating = MutableLiveData<Boolean>()
    val defTypesCreating get(): LiveData<Boolean> = _defTypesCreating

    private val _notice = MutableLiveData<String>()
    val notice get(): LiveData<String> = _notice

    fun createDefaultTypesTable(names: List<String>) {
        viewModelScope.launch {
            val list = names.map { DateType(name = it) }
            when (val result = repository.insertTypes(list)) {
                is Result.Success -> _defTypesCreating.postValue(true)
                is Result.Error -> _notice.postValue(result.msg)
            }
        }
    }
}