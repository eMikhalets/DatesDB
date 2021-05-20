package com.emikhalets.datesdb.ui

import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.databinding.ActivityMainBinding
import com.emikhalets.datesdb.mvi.MviActivity
import com.emikhalets.datesdb.utils.drawer.DrawerHelper
import com.emikhalets.datesdb.utils.drawer.DrawerItem

class MainActivity :
    MviActivity<ActivityIntent, ActivityAction, ActivityState, ActivityViewModel>(
        R.layout.activity_main,
        ActivityViewModel::class.java
    ) {

    override val viewModel: ActivityViewModel by viewModels()

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun initData() {
        val sp = getSharedPreferences(SP_NAME, 0)
        val isTypesDbExist = sp.getBoolean(SP_KEY_TYPES_DB_EXS, false)
        if (!isTypesDbExist) {
            val groupNames = listOf(
                getString(R.string.group_birthdays),
                getString(R.string.group_holidays),
                getString(R.string.group_anniversaries)
            )
            viewModel.dispatchIntent(ActivityIntent.InitGroupsDB(groupNames))
        }
        viewModel.dispatchIntent(ActivityIntent.LoadGroupsList)
    }

    override fun initView() {
        setUpToolBar()
        setUpDrawer()
    }

    override fun fetchState(state: ActivityState) {
        when (state) {
            is ActivityState.Error -> {
            }
            ActivityState.GroupsCreated -> {
                getSharedPreferences(SP_NAME, 0).edit()
                    .putBoolean(SP_KEY_TYPES_DB_EXS, true).apply()
            }
            is ActivityState.ResultGroupsList -> {
            }
        }
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