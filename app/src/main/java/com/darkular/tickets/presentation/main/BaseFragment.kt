package com.darkular.tickets.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.darkular.tickets.presentation.main.MainActivity
import com.darkular.tickets.core.Match
import com.darkular.tickets.presentation.core.AbstractView
import com.darkular.tickets.presentation.core.BaseViewModel

abstract class BaseFragment<T : BaseViewModel<*, *>> : Fragment(), Match<String> {

    protected lateinit var viewModel: T
    protected abstract fun viewModelClass(): Class<T>
    protected abstract val titleResId: Int

    abstract fun name(): String

    override fun matches(data: String) = name() == data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as MainActivity).viewModel(viewModelClass(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setTitle(titleResId)
        if (showBottomNavigation())
            (requireActivity() as AbstractView).show()
        else
            (requireActivity() as AbstractView).hide()
    }

    protected open fun showBottomNavigation() = true
}