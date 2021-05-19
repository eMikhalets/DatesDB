package com.emikhalets.datesdb.model.repositories

import com.emikhalets.datesdb.model.CompleteResult
import com.emikhalets.datesdb.model.ListResult
import com.emikhalets.datesdb.model.SingleResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DateType
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    suspend fun getAllDates(): ListResult<List<DateItem>>
    suspend fun getDateById(id: Long): SingleResult<DateItem>
    suspend fun insertDate(dateItem: DateItem): CompleteResult<Nothing>
    suspend fun updateDate(dateItem: DateItem): CompleteResult<Nothing>
    suspend fun deleteDate(dateItem: DateItem): CompleteResult<Nothing>

    suspend fun getAllTypes(): ListResult<List<DateType>>
    suspend fun insertTypes(list: List<DateType>): CompleteResult<Nothing>
    suspend fun getTypeById(id: Long): SingleResult<DateType>
    suspend fun insertType(type: DateType): CompleteResult<Nothing>
    suspend fun updateType(type: DateType): CompleteResult<Nothing>
    suspend fun deleteType(type: DateType): CompleteResult<Nothing>
}