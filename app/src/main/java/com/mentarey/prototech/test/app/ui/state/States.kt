package com.mentarey.prototech.test.app.ui.state

import com.mentarey.prototech.test.app.domain.ForexSignalUI

sealed class UserAuthState {
    object Success : UserAuthState()
    class Error(val message: String?) : UserAuthState()
}

sealed class RemoteSignal {
    class Data(val forexSignalUIListUI: List<ForexSignalUI>) : RemoteSignal()
    class Error(val message: String?) : RemoteSignal()
}

sealed class DateRangeDialogState{
    object Open: DateRangeDialogState()
    object Close: DateRangeDialogState()
}