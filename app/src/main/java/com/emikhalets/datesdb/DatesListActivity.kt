package com.emikhalets.datesdb

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emikhalets.datesdb.adapters.DatesAdapter
import com.emikhalets.datesdb.adapters.DatesAdapter.OnDateItemClickListener
import com.emikhalets.datesdb.data.database.DateItem
import com.emikhalets.datesdb.databinding.ActivityDatesListBinding
import com.emikhalets.datesdb.utils.Const
import com.emikhalets.datesdb.viewmodels.DatesListViewModel

class DatesListActivity : AppCompatActivity(), OnDateItemClickListener {
    private var adapter: DatesAdapter? = null
    private var viewModel: DatesListViewModel? = null
    private var binding: ActivityDatesListBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatesListBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(DatesListViewModel::class.java)
        viewModel!!.getLiveDataDates().observe(this, { dates: List<DateItem?>? -> onDatesListChanged(dates) })
        adapter = DatesAdapter(this)
        binding!!.recyclerDates.layoutManager = LinearLayoutManager(this)
        binding!!.recyclerDates.adapter = adapter
        binding!!.fabAddDate.setOnClickListener { onAddDateClick() }
    }

    override fun onResume() {
        super.onResume()
        viewModel!!.getAllDates(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dates_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_list_search ->                 // search in list
                return true
            R.id.menu_list_settings ->                 // go to settings activity
                return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDateItemClick(id: Int) {
        val intent = Intent(this, DateItemActivity::class.java)
        intent.putExtra(Const.EXTRA_DATE_ID, id)
        startActivity(intent)
    }

    private fun onAddDateClick() {
        val intent = Intent(this, DateAddActivity::class.java)
        startActivity(intent)
    }

    private fun onDatesListChanged(dates: List<DateItem?>?) {
        if (dates != null) {
            adapter!!.setDates(dates)
            handleTextEmptyListVisibility(dates)
        }
    }

    private fun handleTextEmptyListVisibility(dates: List<DateItem?>) {
        if (!dates.isEmpty()) {
            binding!!.textAddFirstDate.visibility = View.INVISIBLE
        } else {
            binding!!.textAddFirstDate.visibility = View.VISIBLE
        }
    }
}