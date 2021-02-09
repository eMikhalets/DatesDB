package com.emikhalets.datesdb.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emikhalets.datesdb.databinding.ActivityMainBinding
import com.emikhalets.datesdb.data.database.AppDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val database = AppDatabase.create(this.applicationContext)
    }
}