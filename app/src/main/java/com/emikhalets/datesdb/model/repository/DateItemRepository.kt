package com.emikhalets.datesdb.model.repository

import android.database.sqlite.SQLiteException
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.model.database.DatesDao
import com.emikhalets.datesdb.model.entities.DbResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DateItemRepository(
        private val datesDao: DatesDao
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

    suspend fun delete(dateItem: DateItem) = withContext(Dispatchers.IO) {
        try {
            val result = datesDao.delete(dateItem)
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