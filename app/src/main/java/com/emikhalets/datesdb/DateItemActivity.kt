package com.emikhalets.datesdb

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.emikhalets.datesdb.databinding.ActivityDateItemBinding
import com.emikhalets.datesdb.utils.Const
import com.emikhalets.datesdb.viewmodels.DateItemViewModel
import java.text.SimpleDateFormat
import java.util.*

class DateItemActivity : AppCompatActivity() {
    private var viewModel: DateItemViewModel? = null
    private var binding: ActivityDateItemBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateItemBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(DateItemViewModel::class.java)
        viewModel!!.getLiveDataNotice().observe(this, { notice: String ->
            if (notice == "DATE_ITEM") {
                fillFields()
                binding!!.layoutDateItem.animate().alpha(1f).setDuration(1000).start()
            } else if (notice == "DELETED") {
                finish()
            }
        })
        dateFromIntent
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_date_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_delete -> viewModel!!.delete(this)
        }
        return super.onOptionsItemSelected(item)
    }

    private val dateFromIntent: Unit
        private get() {
            val intent = intent
            val id = intent.getIntExtra(Const.EXTRA_DATE_ID, -1)
            if (id != -1) {
                viewModel!!.getDate(this, id)
            }
        }

    private fun fillFields() {
        val current = Calendar.getInstance()
        val currentDay = current[Calendar.DAY_OF_YEAR]
        val selected = Calendar.getInstance()
        selected.timeInMillis = viewModel!!.dateItem!!.date
        val selectedDay = selected[Calendar.DAY_OF_YEAR]
        val selectedHour = current[Calendar.HOUR]
        val selectedMin = current[Calendar.MINUTE]
        val selectedSec = current[Calendar.SECOND]
        var daysLeft = 0
        daysLeft = if (currentDay > selectedDay) {
            365 + selectedDay - currentDay
        } else {
            selectedDay - currentDay
        }
        val dateFormat = SimpleDateFormat("d LLLL y ', ' EEEE")
        binding!!.textName.text = viewModel!!.dateItem!!.name
        binding!!.textDate.text = dateFormat.format(viewModel!!.dateItem!!.date)
        binding!!.textType.text = viewModel!!.dateItem!!.type
        binding!!.textDaysLeft.text = (daysLeft - 1).toString()
        binding!!.textHoursLeft.text = (24 - selectedHour).toString()
        binding!!.textMinutesLeft.text = (60 - selectedMin).toString()
        binding!!.textSecondsLeft.text = (60 - selectedSec).toString()
    }
}