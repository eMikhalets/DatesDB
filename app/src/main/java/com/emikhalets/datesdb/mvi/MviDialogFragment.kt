package com.emikhalets.datesdb.mvi

import android.app.Dialog
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.emikhalets.datesdb.databinding.FragmentDatesListBinding

abstract class MviDialogFragment<I : MviIntent, A : MviAction, S : MviState, VM : MviViewModel<I, A, S>>(
    @LayoutRes val layoutId: Int,
    private val modelClass: Class<VM>
) : DialogFragment(layoutId) {

    abstract val viewModel: VM

    abstract fun initData()
    abstract fun initView()
    abstract fun initIntents()
    abstract fun fetchState(state: S)

    // need for render views
    private lateinit var viewState: S
    val state get() = viewState
    lateinit var dialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (savedInstanceState == null) initData()
        initView()
        initIntents()

        viewModel.state.observe(viewLifecycleOwner, { state ->
            viewState = state
            fetchState(state)
        })

        dialog = AlertDialog.Builder(requireContext())
            .setView(layoutId)
            .create()

        return dialog
    }

    fun dispatchIntent(intent: I) {
        viewModel.dispatchIntent(intent)
    }
}