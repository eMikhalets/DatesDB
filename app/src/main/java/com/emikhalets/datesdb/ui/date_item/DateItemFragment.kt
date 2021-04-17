package com.emikhalets.datesdb.ui.date_item

import android.net.Uri
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.datesdb.R
import com.emikhalets.datesdb.common.BaseFragment
import com.emikhalets.datesdb.databinding.FragmentDateItemBinding
import com.emikhalets.datesdb.view.DateItemFragmentArgs
import com.emikhalets.datesdb.view.DateItemFragmentDirections

class DateItemFragment : BaseFragment<DatesItemIntent, DatesItemAction, DatesItemState, DatesItemViewModel>(
        R.layout.fragment_date_item,
        DatesItemViewModel::class.java
) {

    private val binding by viewBinding(FragmentDateItemBinding::bind)
    override val viewModel: DatesItemViewModel by viewModels()
    private val args: DateItemFragmentArgs by navArgs()

    override fun initView() {
    }

    override fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState == null && args.id > 0) {
            dispatchIntent(DatesItemIntent.LoadDateItem(args.id))
        }
    }

    override fun initEvent() {
        binding.apply {
            fabDeleteDate.setOnClickListener {
                if (args.id > 0) {
                    viewModel.dispatchIntent(DatesItemIntent.DeleteDateItem(args.id))
                }
            }
            fabEditDate.setOnClickListener {
                if (args.id > 0) {
                    val action = DateItemFragmentDirections.actionDateItemToDateEdit(args.id)
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun render(state: DatesItemState) {
        binding.progressBar.isVisible = state is DatesItemState.Loading
        binding.textError.isVisible = state is DatesItemState.Error
        binding.layoutDateItem.isVisible = state is DatesItemState.ResultDateItem

        when (state) {
            is DatesItemState.Error -> binding.textError.text = state.message
            is DatesItemState.Deleted -> findNavController().popBackStack()
            is DatesItemState.ResultDateItem -> {
                binding.apply {
                    // TODO: set data
                    if (state.data.image.isEmpty()) {
                        imageAvatar.isVisible = false
                    } else {
                        imageAvatar.setImageURI(Uri.parse(state.data.image))
                        imageAvatar.isVisible = true
                    }

//                    if (state.data.isYear) textDate.text = formatDate(state.data.date)
//                    else textDate.text = formatDateWithoutYear(state.data.date)

                    textName.text = state.data.name
                    textAge.text = getString(R.string.text_item_age, state.data.age)
                    textType.text = state.data.type
                    textDaysLeft.text = getString(R.string.text_item_days_left, state.data.daysLeft)
                }
            }
            else -> {
            }
        }
    }
}