package com.emikhalets.datesdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.emikhalets.datesdb.databinding.ActivityDateEditBinding
import com.emikhalets.datesdb.viewmodels.DateEditViewModel

class DateEditActivity : AppCompatActivity() {
    private var binding: ActivityDateEditBinding? = null
    private var viewModel: DateEditViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateEditBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(DateEditViewModel::class.java)
    }
}