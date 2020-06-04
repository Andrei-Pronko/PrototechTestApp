package com.mentarey.prototech.test.app.ui.utils

import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes

class FragmentToolbar(
    @IdRes val resId: Int = NO_TOOLBAR_ID,
    @StringRes val titleId: Int?,
    @MenuRes val menuId: Int?,
    val menuWithClicks: MutableList<MenuWithClick>
) {

    companion object {
        const val NO_TOOLBAR_ID = 0
    }

    class Builder {
        private var resId: Int = NO_TOOLBAR_ID
        private var title: Int? = null
        private var menuId: Int? = null
        private var menuWithClicks: MutableList<MenuWithClick> = mutableListOf()

        fun withId(@IdRes resId: Int) = apply { this.resId = resId }
        fun withTitle(@StringRes title: Int) = apply { this.title = title }
        fun withMenu(@MenuRes menuId: Int) = apply { this.menuId = menuId }

        fun addMenuItemClick(newMenuWithClick: MenuWithClick) = apply {
            menuWithClicks.add(newMenuWithClick)
        }

        fun build() = FragmentToolbar(
            resId,
            title,
            menuId,
            menuWithClicks
        )
    }
}