package com.emikhalets.datesdb.model.repositories

import com.emikhalets.datesdb.model.entities.AppResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DateType
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun getAllDates(): Flow<AppResult<List<DateItem>>>
    suspend fun getDateById(id: Long): Flow<AppResult<DateItem>>
    suspend fun insertDate(dateItem: DateItem): Flow<AppResult<Long>>
    suspend fun updateDate(dateItem: DateItem): Flow<AppResult<Int>>
    suspend fun deleteDate(id: Long): Flow<AppResult<Nothing>>

    suspend fun getAllTypes(): Flow<AppResult<List<DateType>>>
    suspend fun getTypeById(id: Long): Flow<AppResult<DateType>>
    suspend fun insertType(type: DateType): Flow<AppResult<Long>>
    suspend fun insertTypes(list: List<DateType>): Flow<AppResult<Int>>
    suspend fun updateType(type: DateType): Flow<AppResult<Int>>
    suspend fun deleteType(type: DateType): Flow<AppResult<Int>>
}