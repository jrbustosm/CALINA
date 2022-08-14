package co.edu.ut.jrbustosm.calina.domain.triggers.conditions

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

abstract class Condition(
    val cardUIState: CardUIState,
    val params: List<String>
) {

    abstract operator fun invoke(appViewModel: AppViewModel, params: List<Any>): Boolean

    companion object{
        fun factory(
            conditionType: ConditionType,
            cardUIState: CardUIState,
            params: List<String>
        ): Condition{
            return when(conditionType){
                ConditionType.TRUE -> ConditionTrue(cardUIState, params)
                ConditionType.RANDOM -> ConditionRandom(cardUIState, params)
                ConditionType.COUNT -> ConditionCount(cardUIState, params)
                ConditionType.DATE_CREATE -> ConditionDateCreate(cardUIState, params)
                ConditionType.NEIGHBOUR -> ConditionNeighbour(cardUIState, params)
                ConditionType.IMEI -> ConditionIMEI(cardUIState, params)
                ConditionType.STATE -> ConditionState(cardUIState, params)
                ConditionType.DATE_EXPIRED -> ConditionDateExpired(cardUIState, params)
                ConditionType.DESCRIPTION -> ConditionDescription(cardUIState, params)
                ConditionType.MONEY -> ConditionMoney(cardUIState, params)
                ConditionType.COUNT_CARDS -> ConditionCountCards(cardUIState, params)
                ConditionType.LEVEL -> ConditionLevel(cardUIState, params)
                ConditionType.DATE_REG -> ConditionDateReg(cardUIState, params)
                else -> ConditionFalse(cardUIState, params)
            }
        }
    }
}