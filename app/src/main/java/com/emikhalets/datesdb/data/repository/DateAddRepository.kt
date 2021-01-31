package com.emikhalets.datesdb.data.repository

import android.database.sqlite.SQLiteException
import com.emikhalets.datesdb.data.database.DateItem
import com.emikhalets.datesdb.data.database.DatesDao
import com.emikhalets.datesdb.data.database.DbResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DateAddRepository(
        private val datesDao: DatesDao
) {

    suspend fun insert(dateItem: DateItem) = withContext(Dispatchers.IO) {
        try {
            val result = datesDao.insert(dateItem)
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