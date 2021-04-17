package com.emikhalets.datesdb.common

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<I : ViewIntent, A : ViewAction, S : ViewState, VM : BaseViewModel<I, A, S>>(
        @LayoutRes val layoutId: Int,
        private val modelClass: Class<VM>
) : IViewRenderer<S>, Fragment(layoutId) {

    abstract val viewModel: VM

    abstract fun initData(savedInstanceState: Bundle?)
    abstract fun initEvent()
    abstract fun initView()

    private lateinit var viewState: S
    val state get() = viewState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(savedInstanceState)
        initEvent()
        initView()
        observeState()
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner, { state ->
            viewState = state
            render(state)
        })
    }


    fun dispatchIntent(intent: I) {
        viewModel.dispatchIntent(intent)
    }
}