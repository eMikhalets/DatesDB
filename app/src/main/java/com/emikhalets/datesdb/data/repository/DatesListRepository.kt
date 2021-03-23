package com.emikhalets.datesdb.data.repository

import com.emikhalets.datesdb.data.database.DatesDao
import com.emikhalets.datesdb.data.database.TypesDao
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.utils.safeDataSourceCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatesListRepository(
        private val typesDao: TypesDao,
        private val datesDao: DatesDao
) {

    suspend fun getAllDates() = withContext(Dispatchers.IO) {
        safeDataSourceCall { datesDao.getAllDates() }
    }

    suspend fun delete(dateItem: DateItem) = withContext(Dispatchers.IO) {
        safeDataSourceCall { datesDao.delete(dateItem) }
    }

    suspend fun update(dateItem: DateItem) = withContext(Dispatchers.IO) {
        safeDataSourceCall { datesDao.update(dateItem) }
    }
}