package com.emikhalets.datesdb.mvi

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class MviActivity<I : MviIntent, A : MviAction, S : MviState, VM : MviViewModel<I, A, S>>(
    @LayoutRes val layoutId: Int,
    private val modelClass: Class<VM>
) : AppCompatActivity(layoutId) {

    abstract val viewModel: VM

    abstract fun initData()
    abstract fun initView()
    abstract fun fetchState(state: S)

    // need for render views
    private lateinit var viewState: S
    val state get() = viewState

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (savedInstanceState == null) initData()
        initView()

        viewModel.state.observe(this, { state ->
            viewState = state
            fetchState(state)
        })
    }

    fun dispatchIntent(intent: I) {
        viewModel.dispatchIntent(intent)
    }
}