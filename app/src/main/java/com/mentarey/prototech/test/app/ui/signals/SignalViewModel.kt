package com.mentarey.prototech.test.app.ui.signals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mentarey.prototech.test.app.domain.SignalsInteractor
import com.mentarey.prototech.test.app.ui.state.DateRangeDialogState
import com.mentarey.prototech.test.app.ui.state.RemoteSignal
import kotlinx.coroutines.launch

class SignalViewModel(private val signalsInteractor: SignalsInteractor) : ViewModel() {

    private val _remoteSignalState = MutableLiveData<RemoteSignal>()
    val remoteSignal: LiveData<RemoteSignal> = _remoteSignalState

    private val _loadingProgress = MutableLiveData<Boolean>()
    val loadingProgress: LiveData<Boolean> = _loadingProgress

    private val _dateRangeDialogState = MutableLiveData<DateRangeDialogState>()
    val dateRangeDialogState: LiveData<DateRangeDialogState> = _dateRangeDialogState

    fun openDateRangeDialog() {
        _dateRangeDialogState.value = DateRangeDialogState.Open
    }

    fun closeDateRangeDialog() {
        _dateRangeDialogState.value = DateRangeDialogState.Close
    }

    fun getSignals(startDate: Long?, endDate: Long?) {
        viewModelScope.launch {
            _loadingProgress.value = true
            val remoteSignalState = signalsInteractor.getSignalsBetween(startDate, endDate)
            _remoteSignalState.value = remoteSignalState
            _loadingProgress.value = false
        }
    }
}