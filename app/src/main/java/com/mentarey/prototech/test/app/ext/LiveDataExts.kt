@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.mentarey.prototech.test.app.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect

open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    fun getContentIfNotHandled(): T? {
        return when (hasBeenHandled) {
            true -> null
            else -> {
                hasBeenHandled = true
                content
            }
        }
    }
}

inline fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(owner, Observer { it.getContentIfNotHandled()?.let(onEventUnhandledContent) })
}