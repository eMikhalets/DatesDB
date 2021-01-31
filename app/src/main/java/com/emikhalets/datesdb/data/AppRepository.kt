package com.emikhalets.datesdb.data

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.emikhalets.datesdb.data.database.AppDatabase
import com.emikhalets.datesdb.data.database.DateItem
import com.emikhalets.datesdb.data.database.DatesDao
import com.emikhalets.datesdb.utils.Const
import com.emikhalets.datesdb.workers.DeleteWorker
import com.emikhalets.datesdb.workers.GetDateWorker
import com.emikhalets.datesdb.workers.InsertWorker
import com.emikhalets.datesdb.workers.UpdateWorker
import io.reactivex.Single
import java.util.*

class AppRepository private constructor(private val context: Context) {
    private val datesDao: DatesDao?
    val allDates: Single<List<DateItem?>?>?
        get() = datesDao!!.getAllDates

    fun getDate(id: Int): UUID {
        val data = Data.Builder()
                .putInt(Const.KEY_DATE_ID, id)
                .build()
        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(GetDateWorker::class.java)
                .setInputData(data)
                .build()
        WorkManager.getInstance(context).enqueue(workRequest)
        return workRequest.id
    }

    fun insert(name: String?, date: Long, type: String?): UUID {
        val data = Data.Builder()
                .putString(Const.KEY_DATE_NAME, name)
                .putLong(Const.KEY_DATE_DATE, date)
                .putString(Const.KEY_DATE_TYPE, type)
                .build()
        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(InsertWorker::class.java)
                .setInputData(data)
                .build()
        WorkManager.getInstance(context).enqueue(workRequest)
        return workRequest.id
    }

    fun update(dateItem: DateItem): UUID {
        val data = Data.Builder()
                .putInt(Const.KEY_DATE_ID, dateItem.id)
                .putString(Const.KEY_DATE_NAME, dateItem.name)
                .putLong(Const.KEY_DATE_DATE, dateItem.date)
                .putString(Const.KEY_DATE_TYPE, dateItem.type)
                .build()
        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(UpdateWorker::class.java)
                .setInputData(data)
                .build()
        WorkManager.getInstance(context).enqueue(workRequest)
        return workRequest.id
    }

    fun delete(dateItem: DateItem): UUID {
        val data = Data.Builder()
                .putInt(Const.KEY_DATE_ID, dateItem.id)
                .putString(Const.KEY_DATE_NAME, dateItem.name)
                .putLong(Const.KEY_DATE_DATE, dateItem.date)
                .putString(Const.KEY_DATE_TYPE, dateItem.type)
                .build()
        val workRequest: WorkRequest = OneTimeWorkRequest.Builder(DeleteWorker::class.java)
                .setInputData(data)
                .build()
        WorkManager.getInstance(context).enqueue(workRequest)
        return workRequest.id
    }

    companion object {
        private var instance: AppRepository? = null
        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): AppRepository? {
            if (instance == null) {
                instance = AppRepository(context)
            }
            return instance
        }
    }

    init {
        datesDao = AppDatabase.getInstance(context)!!.datesDao
    }
}