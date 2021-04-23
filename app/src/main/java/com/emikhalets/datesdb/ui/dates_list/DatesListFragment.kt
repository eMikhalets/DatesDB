package com.emikhalets.datesdb.ui.dates_list

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.common.BaseFragment
import com.emikhalets.datesdb.databinding.FragmentDatesListBinding

class DatesListFragment : BaseFragment<DatesListIntent, DatesListAction, DatesListState, DatesListViewModel>(
        R.layout.fragment_dates_list,
        DatesListViewModel::class.java
) {

    private val binding by viewBinding(FragmentDatesListBinding::bind)
    private lateinit var datesAdapter: DatesAdapter
    override val viewModel: DatesListViewModel by viewModels()

    override fun initView() {
        datesAdapter = DatesAdapter { onDateClick(it) }
        binding.listDates.apply {
            setHasFixedSize(true)
            adapter = datesAdapter
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) dispatchIntent(DatesListIntent.LoadAllDates)
    }

    override fun initEvents() {
        binding.fabAddDate.setOnClickListener {
            val action = DatesListFragmentDirections.actionDatesListToAddDate()
            findNavController().navigate(action)
        }
    }

    private fun onDateClick(id: Long) {
        val action = DatesListFragmentDirections.actionDatesListToDateItem(id)
        findNavController().navigate(action)
    }

    override fun render(state: DatesListState) {
        binding.progressBar.isVisible = state is DatesListState.Loading
        binding.textError.isVisible = state is DatesListState.Error
        binding.listDates.isVisible = state is DatesListState.ResultAllDates

        when (state) {
            is DatesListState.ResultAllDates -> datesAdapter.submitList(state.data)
            is DatesListState.Error -> binding.textError.text = state.message
            else -> {
            }
        }
    }
}