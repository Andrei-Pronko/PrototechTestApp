package com.mentarey.prototech.test.app.ext

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity?.replaceContainer(
    @IdRes containerViewId: Int,
    fragment: Fragment,
    addToBackStack: Boolean = false
) {
    this?.supportFragmentManager?.let {
        with(it.beginTransaction()) {
            replace(containerViewId, fragment)
            if (addToBackStack)
                addToBackStack(null)
            commit()
        }
    }
}

fun Boolean.toVisibility() = when (this) {
    true -> View.VISIBLE
    false -> View.INVISIBLE
}

fun Toolbar.supportPopBackStack(fragment: Fragment) {
    setNavigationOnClickListener {
        fragment.activity.onBackPressed()
    }
}

fun FragmentActivity?.onBackPressed() {
    this?.supportFragmentManager?.popBackStack()
}