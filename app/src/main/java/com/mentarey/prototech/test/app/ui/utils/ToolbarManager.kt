package com.mentarey.prototech.test.app.ui.utils

import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar

class ToolbarManager constructor(
    private val builder: FragmentToolbar,
    private val container: View
) {

    fun prepareToolbar() {
        if (builder.resId == FragmentToolbar.NO_TOOLBAR_ID)
            return
        val fragmentToolbar = container.findViewById(builder.resId) as Toolbar
        setUpToolbarTitle(fragmentToolbar)
        setUpToolbarMenu(fragmentToolbar)
        setUpMenuClicks(fragmentToolbar)
    }

    private fun setUpToolbarTitle(fragmentToolbar: Toolbar) {
        builder.titleId?.let { titleId ->
            fragmentToolbar.setTitle(titleId)
        }
    }

    private fun setUpToolbarMenu(fragmentToolbar: Toolbar) {
        builder.menuId?.let { menuId ->
            fragmentToolbar.inflateMenu(menuId)
        }
    }

    private fun setUpMenuClicks(fragmentToolbar: Toolbar) {
        val menuWithClicks = builder.menuWithClicks
        if (menuWithClicks.isEmpty())
            return

        val toolbarMenu = fragmentToolbar.menu
        menuWithClicks.forEach { menuWithClick ->
            val menuItem = toolbarMenu.findItem(menuWithClick.menuItemId) as MenuItem
            menuItem.setOnMenuItemClickListener {
                menuWithClick.menuItemClickLambda()
                true
            }
        }
    }
}