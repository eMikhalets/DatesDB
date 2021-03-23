package com.emikhalets.datesdb.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.databinding.ActivityMainBinding
import com.emikhalets.datesdb.viewmodel.ActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState ?: createDefaultTypesIfNeed()
        viewModel.defTypesCreating.observe(this) { defTypesObserve(it) }
        viewModel.notice.observe(this) { noticeObserve(it) }
    }

    private fun createDefaultTypesIfNeed() {
        val sp = getSharedPreferences(SP_NAME, 0)
        val isTypesDbExist = sp.getBoolean(SP_KEY_TYPES_DB_EXS, false)
        if (!isTypesDbExist) viewModel.createDefaultTypesTable(listOf(
                getString(R.string.def_type_birthday),
                getString(R.string.def_type_holiday),
                getString(R.string.def_type_anniversary)
        ))
    }

    private fun defTypesObserve(isSuccess: Boolean) {
        getSharedPreferences(SP_NAME, 0).edit()
                .putBoolean(SP_KEY_TYPES_DB_EXS, isSuccess).apply()
    }

    private fun noticeObserve(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val SP_NAME = "shared_preferences_file"
        private const val SP_KEY_TYPES_DB_EXS = "default_types_db_existing"
    }
}