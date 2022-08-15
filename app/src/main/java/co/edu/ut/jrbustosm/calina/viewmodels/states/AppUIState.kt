package co.edu.ut.jrbustosm.calina.viewmodels.states

import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import org.intellij.lang.annotations.Language

data class AppUIState(

    val cards: List<CardUIState> = listOf(),
    val errorMessage: ErrorMessage? = null,
    val isFetchingCards: Boolean = false,
    var filterCard: TypeCardCALINA? = null,
    var stateCard: StateCardCALINA? = null,
    val my_imei: String = "",
    val currentGroup: CardUIState? = null,
    var message: String = "",
    var language: String = ""

    ){

    fun isTeacher(): Boolean = my_imei == currentGroup?.imei_maker

}
