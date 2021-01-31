package com.emikhalets.datesdb.workers

    import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.emikhalets.datesdb.data.database.AppDatabase.Companion.getInstance
import com.emikhalets.datesdb.utils.Const

class GetDateWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val id = inputData.getInt(Const.KEY_DATE_ID, -1)
        val dateItem = getInstance(applicationContext)!!.datesDao!!.getDate(id)
        val output = Data.Builder()
                .putInt(Const.KEY_DATE_ID, dateItem!!.id)
                .putString(Const.KEY_DATE_NAME, dateItem.name)
                .putLong(Const.KEY_DATE_DATE, dateItem.date)
                .putString(Const.KEY_DATE_TYPE, dateItem.type)
                .build()
        return Result.success(output)
    }
}