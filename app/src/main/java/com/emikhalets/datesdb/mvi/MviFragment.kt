package com.emikhalets.datesdb.mvi

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class MviFragment<I : MviIntent, A : MviAction, S : MviState, VM : MviViewModel<I, A, S>>(
        @LayoutRes val layoutId: Int,
        private val modelClass: Class<VM>
) : Fragment(layoutId) {

    abstract val viewModel: VM

    abstract fun initData()
    abstract fun initView()
    abstract fun initIntents()
    abstract fun fetchState(state: S)

    // need for render views
    private lateinit var viewState: S
    val state get() = viewState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) initData()
        initView()
        initIntents()

        viewModel.state.observe(viewLifecycleOwner, { state ->
            viewState = state
            fetchState(state)
        })
    }

    fun dispatchIntent(intent: I) {
        viewModel.dispatchIntent(intent)
    }
}