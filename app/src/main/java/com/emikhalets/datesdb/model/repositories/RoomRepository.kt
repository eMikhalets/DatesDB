package com.emikhalets.datesdb.model.repositories

import com.emikhalets.datesdb.model.CompleteResult
import com.emikhalets.datesdb.model.ListResult
import com.emikhalets.datesdb.model.SingleResult
import com.emikhalets.datesdb.model.database.DatesDao
import com.emikhalets.datesdb.model.database.TypesDao
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.entities.DateType

class RoomRepository(
    private val datesDao: DatesDao,
    private val typesDao: TypesDao,
) : DatabaseRepository {

    override suspend fun getAllDates(): ListResult<List<DateItem>> {
        return try {
            val result = datesDao.getAll()
            if (result.isEmpty()) ListResult.EmptyList
            else ListResult.Success(result)
        } catch (ex: Exception) {
            ListResult.Error(ex)
        }
    }

    override suspend fun getDateById(id: Long): SingleResult<DateItem> {
        return try {
            val result = datesDao.getItem(id)
            SingleResult.Success(result)
        } catch (ex: Exception) {
            SingleResult.Error(ex)
        }
    }

    override suspend fun insertDate(dateItem: DateItem): CompleteResult<Nothing> {
        return try {
            datesDao.insert(dateItem)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun updateDate(dateItem: DateItem): CompleteResult<Nothing> {
        return try {
            datesDao.update(dateItem)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun deleteDate(dateItem: DateItem): CompleteResult<Nothing> {
        return try {
            datesDao.delete(dateItem)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun getAllTypes(): ListResult<List<DateType>> {
        return try {
            val result = typesDao.getAll()
            if (result.isEmpty()) ListResult.EmptyList
            else ListResult.Success(result)
        } catch (ex: Exception) {
            ListResult.Error(ex)
        }
    }

    override suspend fun getTypeById(id: Long): SingleResult<DateType> {
        return try {
            val dateType = typesDao.getItem(id)
            SingleResult.Success(dateType)
        } catch (ex: Exception) {
            SingleResult.Error(ex)
        }
    }

    override suspend fun insertType(type: DateType): CompleteResult<Nothing> {
        return try {
            typesDao.insert(type)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun insertTypes(list: List<DateType>): CompleteResult<Nothing> {
        return try {
            typesDao.insertAll(list)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun updateType(type: DateType): CompleteResult<Nothing> {
        try {
            typesDao.update(type)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }

    override suspend fun deleteType(type: DateType): CompleteResult<Nothing> {
        try {
            typesDao.delete(type)
            CompleteResult.Complete
        } catch (ex: Exception) {
            CompleteResult.Error(ex)
        }
    }
}