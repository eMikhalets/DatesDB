package com.emikhalets.datesdb.utils.drawer

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class DrawerHelper(
    val context: AppCompatActivity,
    val itemsList: List<DrawerItem>
) {

    fun setUp(recyclerView: RecyclerView) {
        setUpList(recyclerView)
    }

    fun addToList(item: DrawerItem) {
    }

    private fun setUpList(recyclerView: RecyclerView) {
        val drawerAdapter = DrawerAdapter { onItemClick(it) }
        recyclerView.apply {
            adapter = drawerAdapter
            setHasFixedSize(true)
        }
    }

    private fun onItemClick(groupName: String) {
    }
}