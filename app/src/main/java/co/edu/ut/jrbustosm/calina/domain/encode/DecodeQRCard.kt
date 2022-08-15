package co.edu.ut.jrbustosm.calina.domain.encode

import co.edu.ut.jrbustosm.calina.domain.crypto.Decode
import co.edu.ut.jrbustosm.calina.domain.crypto.UnMask
import co.edu.ut.jrbustosm.calina.domain.crypto.Uncompress
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIStateLight
import com.google.gson.Gson

class DecodeQRCard: DecodeQR() {

    override fun invoke(text: String): CardUIState {
        val gson = Gson()
        val decodeText = Uncompress()(Decode()(UnMask()(text)))
        val card = gson.fromJson(decodeText, CardUIStateLight::class.java)
        return CardUIState(
            imei_card = card.imei_card,
            imei_owner = card.imei_owner,
            imei_maker = card.imei_maker,
            date_reg = card.date_reg,
            date_expire = card.date_expire,
            date_create = card.date_create,
            trigger = card.trigger,
            state = card.state,
            isSymbol = card.isSymbol,
            imageResource = card.imageResource,
            isEdit = card.isEdit,
            count = card.count,
            difficulty = card.difficulty,
            description = card.description,
            isSelect = card.isSelect,
            levels = card.levels,
            level = card.level,
            cash_symbol = card.cash_symbol,
            scope = card.scope,
            isCloneable = card.isCloneable,
            isSecondary = card.isSecondary,
            cash = card.cash,
            isTransfer = card.isTransfer,
            number = card.number,
            type = card.type,
            title = card.title,
            url = card.url,
            lang = card.lang
        )
    }
}