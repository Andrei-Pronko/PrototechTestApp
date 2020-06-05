package com.mentarey.prototech.test.app.domain

import com.mentarey.prototech.test.app.data.pref.UserAccountRepository
import com.mentarey.prototech.test.app.data.retrofit.PrototechApi
import com.mentarey.prototech.test.app.data.retrofit.entity.ForexSignal
import com.mentarey.prototech.test.app.ui.state.RemoteSignal
import com.mentarey.prototech.test.app.utils.toStringDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SignalsInteractor(
    private val prototechApi: PrototechApi,
    private val userAccountRepository: UserAccountRepository
) {

    suspend fun getSignalsBetween(startDate: Long?, endDate: Long?): RemoteSignal =
        withContext(Dispatchers.IO) {
            if (startDate == null)
                return@withContext RemoteSignal.Error("Не указана дата начала поиска")
            if (endDate == null)
                return@withContext RemoteSignal.Error("Не указана дата окончания поиска")
            try {
                val login = userAccountRepository.getUserLogin()
                val pairsString = pairs.toPairQueryParam()
                val from = startDate.toSeconds()
                val to = endDate.toSeconds()
                val forexSignal = prototechApi.getAnalyticSignals(
                    login = login,
                    pairsString = pairsString,
                    from = from,
                    to = to
                )
                val forexSignalsUI = forexSignal.toSortedForexSignalsUI()
                RemoteSignal.Data(forexSignalsUI)
            } catch (e: Throwable) {
                RemoteSignal.Error(e.localizedMessage)
            }
        }

    private fun List<String>.toPairQueryParam() = joinToString(",")

    private fun List<ForexSignal>.toSortedForexSignalsUI() = sortedBy { it.actualTime }
        .map { it.toForexSignalUI() }

    private fun ForexSignal.toForexSignalUI() = ForexSignalUI(
        id = id,
        actualTime = actualTime.toMillis().toStringDate,
        pair = pair,
        period = period,
        price = price.toString(),
        comment = comment
    )

    private fun Long.toSeconds() = this / 1000

    private fun Long.toMillis() = this * 1000

    companion object {
        private val pairs = listOf(
            "EURUSD",
            "GBPUSD",
            "USDJPY",
            "USDCHF",
            "USDCAD",
            "AUDUSD",
            "NZDUSD"
        )
    }
}