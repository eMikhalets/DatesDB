package com.emikhalets.datesdb.model.repositories

import com.emikhalets.datesdb.model.CompleteResult
import com.emikhalets.datesdb.model.ListResult
import com.emikhalets.datesdb.model.SingleResult
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.Group

interface DatabaseRepository {

    suspend fun getAllDates(): ListResult<List<DateItem>>
    suspend fun getDateById(id: Long): SingleResult<DateItem>
    suspend fun insertDate(dateItem: DateItem): CompleteResult<Nothing>
    suspend fun updateDate(dateItem: DateItem): CompleteResult<Nothing>
    suspend fun deleteDate(dateItem: DateItem): CompleteResult<Nothing>

    suspend fun getAllGroups(): ListResult<List<Group>>
    suspend fun insertGroups(list: List<Group>): CompleteResult<Nothing>
    suspend fun getGroupByName(name: String): SingleResult<Group>
    suspend fun insertGroup(group: Group): CompleteResult<Nothing>
    suspend fun updateGroup(group: Group): CompleteResult<Nothing>
    suspend fun deleteGroup(group: Group): CompleteResult<Nothing>
}