package com.mentarey.prototech.test.app.ui.utils

import androidx.annotation.IdRes

class MenuWithClick(@IdRes val menuItemId: Int, val menuItemClickLambda: () -> Unit)