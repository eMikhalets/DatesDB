package com.emikhalets.datesdb.ui.dates_list

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.databinding.FragmentDatesListBinding
import com.emikhalets.datesdb.model.entities.DateItem
import com.emikhalets.datesdb.mvi.MviFragment

class DatesListFragment :
    MviFragment<DatesListIntent, DatesListAction, DatesListState, DatesListViewModel>(
        R.layout.fragment_dates_list,
        DatesListViewModel::class.java
    ) {

    override val viewModel: DatesListViewModel by viewModels()

    private val binding by viewBinding(FragmentDatesListBinding::bind)
    private lateinit var datesAdapter: DatesAdapter

    override fun initData() {
        dispatchIntent(DatesListIntent.LoadDatesList)
    }

    override fun initView() {
        datesAdapter = DatesAdapter { onDateClick(it) }
        binding.listDates.apply {
            setHasFixedSize(true)
            adapter = datesAdapter
        }
    }

    override fun initIntents() {
        binding.btnAddDateItem.setOnClickListener {
            val action = DatesListFragmentDirections.actionDatesListToAddDate()
            findNavController().navigate(action)
        }
    }

    private fun onDateClick(dateItem: DateItem) {
        val action = DatesListFragmentDirections.actionDatesListToDateItem(dateItem)
        findNavController().navigate(action)
    }

    override fun fetchState(state: DatesListState) {
        when (state) {
            DatesListState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.textError.visibility = View.GONE
                binding.listDates.visibility = View.GONE
            }
            DatesListState.ResultEmptyDatesList -> {
                binding.textError.text = getString(R.string.dates_list_text_empty_list)
                binding.textError.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.listDates.visibility = View.GONE
            }
            is DatesListState.ResultDatesList -> {
                datesAdapter.submitList(state.data)
                binding.listDates.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.textError.visibility = View.GONE
            }
            is DatesListState.Error -> {
                binding.textError.text = state.message
                binding.textError.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                binding.listDates.visibility = View.GONE
            }
        }
    }
}