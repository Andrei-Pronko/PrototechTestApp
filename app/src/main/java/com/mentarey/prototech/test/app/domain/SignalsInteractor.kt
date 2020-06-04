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

    suspend fun getSignals(pairs: List<String>): RemoteSignal = withContext(Dispatchers.IO) {
        try {
            val token = userAccountRepository.getUserToken()
            val login = userAccountRepository.getUserLogin()
            val pairsString = pairs.toPairQueryParam()
            val forexSignal = prototechApi.getAnalyticSignals(token, login, pairsString)
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
        actualTime = actualTime.toStringDate,
        pair = pair,
        period = period,
        price = price.toString(),
        comment = comment
    )
}