@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.mentarey.prototech.test.app.ui.signals

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.mentarey.prototech.test.app.R
import com.mentarey.prototech.test.app.domain.ForexSignalUI
import com.mentarey.prototech.test.app.ext.supportPopBackStack
import com.mentarey.prototech.test.app.ext.toVisibility
import com.mentarey.prototech.test.app.ui.state.DateRangeDialogState
import com.mentarey.prototech.test.app.ui.state.RemoteSignal
import com.mentarey.prototech.test.app.ui.utils.BaseFragment
import com.mentarey.prototech.test.app.ui.utils.FragmentToolbar
import kotlinx.android.synthetic.main.fragment_signals.*
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.sendBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SignalFragment : BaseFragment(R.layout.fragment_signals) {

    private val signalViewModel: SignalViewModel by viewModel()
    private var dateRangePicker: MaterialDatePicker<Pair<Long, Long>>? = null

    override fun builder(): FragmentToolbar = FragmentToolbar.Builder()
        .withId(R.id.toolbar_signals)
        .withTitle(R.string.fragment_signals_title)
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        observeViewModel()
        setUpSignalsButton()
        supportNavigationBackSupport()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dateRangePicker?.clearOnDismissListeners()
        closeDatePickerDialog()
        dateRangePicker = null
    }

    private fun setUpRecyclerView() {
        recycler_view_signals.apply {
            adapter = SignalAdapter().apply {
                stateRestorationPolicy =
                    RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            }
        }
    }

    private fun observeViewModel() {
        signalViewModel.remoteSignal.observe(viewLifecycleOwner) {
            when (it) {
                is RemoteSignal.Data ->
                    signalsActor.sendBlocking(it.forexSignalUIListUI)
                is RemoteSignal.Error ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
        signalViewModel.loadingProgress.observe(viewLifecycleOwner) {
            progressBar_signals.apply {
                isIndeterminate = it
                visibility = it.toVisibility()
            }
        }
        signalViewModel.dateRangeDialogState.observe(viewLifecycleOwner) {
            when (it) {
                is DateRangeDialogState.Open -> openDatePickerDialog()
                is DateRangeDialogState.Close -> closeDatePickerDialog()
            }
        }
    }

    private fun setUpSignalsButton() {
        button_get_signals.setOnClickListener {
            signalViewModel.openDateRangeDialog()
        }
    }

    private fun supportNavigationBackSupport() {
        toolbar_signals.supportPopBackStack(this)
    }

    private fun buildDatePicker() {
        dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setCalendarConstraints(getDatePickerRange())
            .setTitleText(R.string.dialog_title_choice_date_range)
            .build()
        dateRangePicker?.apply {
            addOnPositiveButtonClickListener {
                signalViewModel.getSignals(it.first, it.second)
            }
            addOnDismissListener { signalViewModel.closeDateRangeDialog() }
        }
    }

    private fun getDatePickerRange(): CalendarConstraints {
        return CalendarConstraints.Builder().apply {
            setStart(getStartDate())
            setEnd(getEndDate())
        }.build()
    }

    private fun openDatePickerDialog() {
        buildDatePicker()
        dateRangePicker?.show(requireActivity().supportFragmentManager, dateRangePicker.toString())
    }

    private fun closeDatePickerDialog() {
        dateRangePicker?.dismiss()
    }

    private fun getStartDate(): Long {
        val calendarStart: Calendar = GregorianCalendar.getInstance()
        calendarStart.set(2019, 1, 1)
        return calendarStart.timeInMillis
    }

    private fun getEndDate(): Long {
        return GregorianCalendar.getInstance().timeInMillis
    }

    private val signalsActor = lifecycleScope.actor<List<ForexSignalUI>> {
        consumeEach {
            val adapter = recycler_view_signals.adapter as SignalAdapter
            adapter.setNewData(it)
        }
    }
}