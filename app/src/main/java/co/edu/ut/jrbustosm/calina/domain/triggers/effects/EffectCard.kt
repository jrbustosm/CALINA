package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import co.edu.ut.jrbustosm.calina.domain.GenIMEI
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.DifficultyCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA
import java.util.*

class EffectCard(
    cardUIState: CardUIState,
    params: List<String>,
): Effect(cardUIState, params) {

    var title: String = ""
    var description: String = ""
    var type = TypeCardCALINA.INFORMATION
    var difficulty = DifficultyCardCALINA.EASY
    var number = 0
    var image = "symbol1"
    var isTransfer = false

    init {
        if(params.size==7){
            title = params[0]
            description = params[1]
            type = TypeCardCALINA.getType(params[2][0])
            difficulty = DifficultyCardCALINA.getDifficulty(params[3][0])
            number =params[4].toIntOrNull()?:0
            image = params[5]
            isTransfer = params[6][0] == '1'
        }
    }

    override fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>,
    ): Pair<Boolean, String> {
        val newCard = CardUIState(
            title = title,
            description = description,
            type = type,
            difficulty = difficulty,
            number = number,
            imageResource = image,
            isTransfer = isTransfer,
            cash = 0,
            count = 0,
            isSymbol = image.startsWith("symbol"),
            isCloneable = false,
            scope = cardUIState.scope,
            date_create = Date(),
            date_expire = null,
            cash_symbol = '\u0000',
            level = "",
            levels = "",
            isEdit = false,
            isSecondary = type == TypeCardCALINA.GROUP,
            isSelect = false,
            state = StateCardCALINA.NORMAL,
            imei_maker = cardUIState.imei_maker,
            imei_owner = appViewModel.appUIState.my_imei,
            imei_card = GenIMEI()(),
            trigger = "",
            date_reg = null,
            url = "",
            lang = appViewModel.appUIState.language,
            isDeletable = true
        )
        appViewModel.insert(newCard)
        //newCard.execTriggers(EventType.OnReceive, appViewModel)
        return Pair(false, "")
    }
}