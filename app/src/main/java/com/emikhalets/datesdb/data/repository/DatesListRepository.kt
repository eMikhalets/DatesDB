package com.emikhalets.datesdb.data.repository

import com.emikhalets.datesdb.data.database.DatesDao
import com.emikhalets.datesdb.data.database.TypesDao
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.data.entities.DateType
import com.emikhalets.datesdb.utils.safeDatabaseCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatesListRepository(
        private val typesDao: TypesDao,
        private val datesDao: DatesDao
) {

    suspend fun getAllDates() = withContext(Dispatchers.IO) {
        safeDatabaseCall { datesDao.getAllDates() }
    }

    suspend fun delete(dateItem: DateItem) = withContext(Dispatchers.IO) {
        safeDatabaseCall { datesDao.delete(dateItem) }
    }

    suspend fun update(dateItem: DateItem) = withContext(Dispatchers.IO) {
        safeDatabaseCall { datesDao.update(dateItem) }
    }

    suspend fun insertTypes(list: List<DateType>) = withContext(Dispatchers.IO) {
        safeDatabaseCall { typesDao.insertAll(list) }
    }
}