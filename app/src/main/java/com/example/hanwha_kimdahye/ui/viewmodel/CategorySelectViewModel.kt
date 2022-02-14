package com.example.hanwha_kimdahye.ui.viewmodel

import androidx.lifecycle.* // ktlint-disable no-wildcard-imports
import com.example.hanwha_kimdahye.util.Event

class CategorySelectViewModel : ViewModel() {

    private val _openEvent = MutableLiveData<Event<Int>>()
    val openEvent: LiveData<Event<Int>>
        get() = _openEvent

    fun onClickEvent(idx: Int) {
        _openEvent.value = Event(idx)
    }
}

inline fun <T> LiveData<Event<T>>.eventObserve(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<Event<T>> {
    val wrappedObserver = Observer<Event<T>> { t ->
        t.getContentIfNotHandled()?.let {
            onChanged.invoke(it)
        }
    }
    observe(owner, wrappedObserver)
    return wrappedObserver
}