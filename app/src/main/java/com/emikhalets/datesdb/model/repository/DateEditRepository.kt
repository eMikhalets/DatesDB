package com.emikhalets.datesdb.model.repository

import android.database.sqlite.SQLiteException
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.database.DatesDao
import com.emikhalets.datesdb.model.entities.DbResult
import com.emikhalets.datesdb.model.database.TypesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DateEditRepository(
        private val datesDao: DatesDao,
        private val typesDao: TypesDao
) {

    suspend fun getDate(id: Int) = withContext(Dispatchers.IO) {
        try {
            val result = datesDao.getDate(id)
            DbResult.Success(result)
        } catch (sqex: SQLiteException) {
            sqex.printStackTrace()
            DbResult.Error(sqex.message ?: "SQLiteException", sqex)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DbResult.Error(ex.message ?: "Exception", ex)
        }
    }

    suspend fun getAllTypes() = withContext(Dispatchers.IO) {
        try {
            val result = typesDao.getAllTypes()
            DbResult.Success(result)
        } catch (sqex: SQLiteException) {
            sqex.printStackTrace()
            DbResult.Error(sqex.message ?: "SQLiteException", sqex)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DbResult.Error(ex.message ?: "Exception", ex)
        }
    }

    suspend fun update(dateItem: DateItem) = withContext(Dispatchers.IO) {
        try {
            val result = datesDao.update(dateItem)
            DbResult.Success(result)
        } catch (sqex: SQLiteException) {
            sqex.printStackTrace()
            DbResult.Error(sqex.message ?: "SQLiteException", sqex)
        } catch (ex: Exception) {
            ex.printStackTrace()
            DbResult.Error(ex.message ?: "Exception", ex)
        }
    }
}