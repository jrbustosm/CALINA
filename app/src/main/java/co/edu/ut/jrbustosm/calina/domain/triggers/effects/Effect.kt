package co.edu.ut.jrbustosm.calina.domain.triggers.effects

import android.content.Context
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

abstract class Effect(
    val cardUIState: CardUIState,
    val params: List<String>
) {

    abstract operator fun invoke(
        appViewModel: AppViewModel,
        params: List<Any>
    ): Pair<Boolean, String>

    companion object {
        fun factory(
            effectType: EffectType,
            cardUIState: CardUIState,
            params: List<String>,
        ): Effect {
            return when(effectType){
                EffectType.DESTROY -> EffectDestroy(cardUIState, params)
                EffectType.MONEY -> EffectMoney(cardUIState, params)
                EffectType.CARD -> EffectCard(cardUIState, params)
                EffectType.TITLE -> EffectTitle(cardUIState, params)
                EffectType.DIFFICULTY -> EffectDifficulty(cardUIState, params)
                EffectType.IMAGE -> EffectImage(cardUIState, params)
                EffectType.DESCRIPTION -> EffectDescription(cardUIState, params)
                EffectType.INCREASE -> EffectIncrease(cardUIState, params)
                EffectType.DECREASE -> EffectDecrease(cardUIState, params)
                EffectType.RESET -> EffectReset(cardUIState, params)
                EffectType.EDIT -> EffectEdit(cardUIState, params)
                EffectType.USED -> EffectUsed(cardUIState, params)
                EffectType.CLONE -> EffectClone(cardUIState, params)
                EffectType.MESSAGE -> EffectMessage(cardUIState, params)
                EffectType.REMOVE_TRIGGER -> EffectRemoveTrigger(cardUIState, params)
                EffectType.IMEI_CARD -> EffectIMEICard(cardUIState, params)
                EffectType.DATE_REG -> EffectDateReg(cardUIState, params)
                EffectType.DATE_REG_RESET -> EffectDateRegReset(cardUIState, params)
                else -> EffectNothing(cardUIState, params)
            }
        }
    }
}