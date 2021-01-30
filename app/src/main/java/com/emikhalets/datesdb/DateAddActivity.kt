package com.emikhalets.datesdb

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.emikhalets.datesdb.databinding.ActivityDateAddBinding
import com.emikhalets.datesdb.viewmodels.DateAddViewModel
import java.text.SimpleDateFormat
import java.util.*

class DateAddActivity : AppCompatActivity() {
    private var viewModel: DateAddViewModel? = null
    private var binding: ActivityDateAddBinding? = null
    private var selectedDate: Long = 0
    private var isInserted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateAddBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(DateAddViewModel::class.java)
        viewModel!!.getLiveDataNotice().observe(this, { notice: String ->
            if (notice == "OK") {
                isInserted = true
                finish()
            }
        })
        binding!!.btnDatePick.setOnClickListener { view: View? -> onBtnDatePickerClick() }
        initPickDate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_date_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_save -> {
                onClickSave()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (isInserted) {
            super.onBackPressed()
        }
    }

    private fun onClickSave() {
        val name = binding!!.etName.text.toString().trim { it <= ' ' }
        val type = binding!!.spinnerDateType.selectedItem.toString()
        viewModel!!.insert(this, name, selectedDate, type)
    }

    private fun onBtnDatePickerClick() {
        val current = Calendar.getInstance()
        DatePickerDialog(this, dateSetListener,
                current[Calendar.YEAR],
                current[Calendar.MONTH],
                current[Calendar.DAY_OF_MONTH])
                .show()
    }

    private val dateSetListener = OnDateSetListener { datePicker: DatePicker?, year: Int, month: Int, day: Int ->
        val selected = Calendar.getInstance()
        selected[year, month] = day
        val dateFormat = SimpleDateFormat("d LLLL y ', ' EEEE")
        binding!!.btnDatePick.text = dateFormat.format(selected.time)
        selectedDate = selected.timeInMillis
    }

    private fun initPickDate() {
        val current = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("d LLLL y ', ' EEEE")
        binding!!.btnDatePick.text = dateFormat.format(current.time)
        selectedDate = current.timeInMillis
    }
}