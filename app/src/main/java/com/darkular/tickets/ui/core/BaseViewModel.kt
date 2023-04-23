package com.darkular.tickets.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.darkular.tickets.core.Abstract

abstract class BaseViewModel<E : Communication<T>, T>(protected val communication: E) :
    ViewModel(), Observe<T> {

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        communication.observe(owner, observer)
    }
}