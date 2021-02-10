package com.emikhalets.datesdb.data.repository

import com.emikhalets.datesdb.data.database.DatesDao
import com.emikhalets.datesdb.data.database.TypesDao
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.data.entities.DateType
import com.emikhalets.datesdb.utils.safeDatabaseCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DateEditRepository(
        private val datesDao: DatesDao,
        private val typesDao: TypesDao
) {

    suspend fun getDate(id: Int) = withContext(Dispatchers.IO) {
        safeDatabaseCall { datesDao.getDate(id) }
    }

    suspend fun insertDate(dateItem: DateItem) = withContext(Dispatchers.IO) {
        safeDatabaseCall { datesDao.insert(dateItem) }
    }

    suspend fun updateDate(dateItem: DateItem) = withContext(Dispatchers.IO) {
        safeDatabaseCall { datesDao.update(dateItem) }
    }

    suspend fun getAllTypes() = withContext(Dispatchers.IO) {
        safeDatabaseCall { typesDao.getAllTypes() }
    }

    suspend fun insertType(type: DateType) = withContext(Dispatchers.IO) {
        safeDatabaseCall { typesDao.insert(type) }
    }
}