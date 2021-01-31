package com.emikhalets.datesdb.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.emikhalets.datesdb.data.database.AppDatabase.Companion.getInstance
import com.emikhalets.datesdb.data.database.DateItem
import com.emikhalets.datesdb.utils.Const

class InsertWorker(context: Context,
                   workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val dateItem = DateItem(
                inputData.getString(Const.KEY_DATE_NAME)!!,
                inputData.getLong(Const.KEY_DATE_DATE, -1),
                inputData.getString(Const.KEY_DATE_TYPE)!!)
        getInstance(applicationContext)!!.datesDao!!.insert(dateItem)
        return Result.success()
    }
}