package co.edu.ut.jrbustosm.calina.domain.triggers.conditions

import co.edu.ut.jrbustosm.calina.data.asLocalDate
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class ConditionDateCreate(
    cardUIState: CardUIState,
    params: List<String>
): Condition(cardUIState, params) {

    var n: Int = 0

    init {
        if(params.isNotEmpty()) n = params[0].toIntOrNull()?:0
    }

    override fun invoke(appViewModel: AppViewModel, params: List<Any>): Boolean{
        val localDate = cardUIState.date_create.asLocalDate()
        val dayDiff = ChronoUnit.DAYS.between(localDate, LocalDate.now())
        return n >= dayDiff
    }
}