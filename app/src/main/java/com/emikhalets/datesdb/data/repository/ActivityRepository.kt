package com.emikhalets.datesdb.data.repository

import com.emikhalets.datesdb.data.database.TypesDao
import com.emikhalets.datesdb.data.entities.DateType
import com.emikhalets.datesdb.utils.safeDataSourceCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ActivityRepository(private val typesDao: TypesDao) {

    suspend fun insertTypes(list: List<DateType>) = withContext(Dispatchers.IO) {
        safeDataSourceCall { typesDao.insertAll(list) }
    }
}