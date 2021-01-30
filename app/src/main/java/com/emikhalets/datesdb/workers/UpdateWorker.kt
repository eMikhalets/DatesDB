package com.emikhalets.datesdb.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.emikhalets.datesdb.data.AppDatabase.Companion.getInstance
import com.emikhalets.datesdb.data.DateItem
import com.emikhalets.datesdb.utils.Const

class UpdateWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val dateItem = DateItem(
                inputData.getInt(Const.KEY_DATE_ID, -1),
                inputData.getString(Const.KEY_DATE_NAME)!!,
                inputData.getLong(Const.KEY_DATE_DATE, -1),
                inputData.getString(Const.KEY_DATE_TYPE)!!)
        getInstance(applicationContext)!!.datesDao!!.update(dateItem)
        return Result.success()
    }
}