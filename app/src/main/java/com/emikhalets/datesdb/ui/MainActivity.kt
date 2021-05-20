package com.emikhalets.datesdb.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.databinding.ActivityMainBinding
import com.emikhalets.datesdb.utils.drawer.DrawerAdapter
import com.emikhalets.datesdb.utils.drawer.DrawerHelper
import com.emikhalets.datesdb.utils.drawer.DrawerItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolBar()

        savedInstanceState ?: createDefaultTypesIfNeed()
        viewModel.defTypesCreating.observe(this) { defTypesObserve(it) }
        viewModel.notice.observe(this) { noticeObserve(it) }
    }

    private fun createDefaultTypesIfNeed() {
        val sp = getSharedPreferences(SP_NAME, 0)
        val isTypesDbExist = sp.getBoolean(SP_KEY_TYPES_DB_EXS, false)
        if (!isTypesDbExist) viewModel.createDefaultTypesTable(
            listOf(
//                getString(R.string.app_def_group),
//                getString(R.string.app)
            )
        )
    }

    private fun defTypesObserve(isSuccess: Boolean) {
        getSharedPreferences(SP_NAME, 0).edit()
            .putBoolean(SP_KEY_TYPES_DB_EXS, isSuccess).apply()
    }

    private fun noticeObserve(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.drawer.setupWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)
    }


    private fun setUpDrawer() {
        val tempList = listOf<DrawerItem>()
        val drawerHelper = DrawerHelper(this, tempList)
        drawerHelper.setUp(binding.drawerList)
    }

    private fun onClickDrawerItem(groupName: String) {
    }

    companion object {
        private const val SP_NAME = "shared_preferences_file"
        private const val SP_KEY_TYPES_DB_EXS = "default_types_db_existing"
    }
}