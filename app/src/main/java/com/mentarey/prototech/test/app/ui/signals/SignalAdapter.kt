package com.mentarey.prototech.test.app.ui.signals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mentarey.prototech.test.app.R
import com.mentarey.prototech.test.app.domain.ForexSignalUI
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_forex_signal.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignalAdapter internal constructor(private val forexSignals: MutableList<ForexSignalUI> = mutableListOf()) :
    RecyclerView.Adapter<SignalAdapter.SignalViewHolder>() {

    class SignalViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View
            get() = itemView

        fun bind(forexSignalUI: ForexSignalUI) {
            textView_signal_time.text = forexSignalUI.actualTime
            textView_signal_pair.text = forexSignalUI.pair
            textView_period_value.text = forexSignalUI.period
            textView_price.text = forexSignalUI.price
            textView_comment.text = forexSignalUI.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_forex_signal, parent, false)
        return SignalViewHolder(itemView)
    }

    override fun getItemCount(): Int = forexSignals.size

    override fun onBindViewHolder(holder: SignalViewHolder, position: Int) {
        holder.bind(forexSignals[position])
    }

    suspend fun setNewData(newData: List<ForexSignalUI>) {
        val diffResult = withContext(Dispatchers.Default) {
            val callback = SignalDiffUtilCallback(forexSignals, newData)
            DiffUtil.calculateDiff(callback)
        }
        forexSignals.clear()
        forexSignals.addAll(newData)
        notifyDataSetChanged()
        diffResult.dispatchUpdatesTo(this)
    }

    internal class SignalDiffUtilCallback constructor(
        private val oldItems: List<ForexSignalUI>,
        private val newItems: List<ForexSignalUI>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size
    }
}