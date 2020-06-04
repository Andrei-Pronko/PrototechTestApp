package com.mentarey.prototech.test.app.ui.utils

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ToolbarManager(builder(), view).prepareToolbar()
    }

    protected abstract fun builder(): FragmentToolbar
}