package com.emikhalets.datesdb.data.repository

import com.emikhalets.datesdb.data.database.DatesDao
import com.emikhalets.datesdb.data.entities.DateItem
import com.emikhalets.datesdb.utils.safeDatabaseCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DateItemRepository(
        private val datesDao: DatesDao
) {

    suspend fun getDate(id: Int) = withContext(Dispatchers.IO) {
        safeDatabaseCall { datesDao.getDate(id) }
    }

    suspend fun delete(dateItem: DateItem) = withContext(Dispatchers.IO) {
        safeDatabaseCall { datesDao.delete(dateItem) }
    }
}