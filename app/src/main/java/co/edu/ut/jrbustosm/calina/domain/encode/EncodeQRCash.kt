package co.edu.ut.jrbustosm.calina.domain.encode

import android.content.Context
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.data.asDate
import co.edu.ut.jrbustosm.calina.data.getResource
import co.edu.ut.jrbustosm.calina.domain.GenIMEI
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.DifficultyCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA
import java.time.LocalDate
import java.time.Period
import java.util.*

class EncodeQRCash: EncodeQR() {
    override fun invoke(cardUIState: CardUIState): String {
        return EncodeQRCard()(cardUIState)
    }

    operator fun invoke(amount: Int, identity: String, appViewModel: AppViewModel, context: Context): String {
        val period = Period.of(0, 0, 1)
        val expired = LocalDate.now().plus(period)
        val cardUIState = CardUIState(
            state = StateCardCALINA.NORMAL,
            isSymbol = false,
            isCloneable = false,
            imei_maker = appViewModel.currentGroup!!.imei_maker,
            imei_owner = identity,
            type = TypeCardCALINA.ACTION,
            difficulty = DifficultyCardCALINA.EASY,
            imageResource = "item167",
            trigger = "",
            title = context.getString(R.string.money_card),
            count = 0,
            scope = appViewModel.currentGroup!!.imei_card,
            date_create = Date(),
            date_expire = expired.asDate(),
            isEdit = false,
            cash = amount,
            cash_symbol = '\u0000',
            isTransfer = true,
            level = "",
            levels = "",
            imei_card = GenIMEI()(),
            isSecondary = false,
            isSelect = false,
            description = "${context.getString(R.string.when_youreceive)} " +
                    "${appViewModel.currentGroup!!.cash_symbol} $amount",
            number = 7777,
            date_reg = null,
            url = ""
        )
        return this(cardUIState)
    }
}