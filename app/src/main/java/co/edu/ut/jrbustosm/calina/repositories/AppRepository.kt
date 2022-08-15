package co.edu.ut.jrbustosm.calina.repositories

import android.content.Context
import co.edu.ut.jrbustosm.calina.viewmodels.AppViewModel
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

abstract class AppRepository(
    protected val context: Context
) {

    abstract suspend fun byType(
        type: TypeCardCALINA? = null,
        state: StateCardCALINA? = null,
        currentGroup: CardUIState? = null,
    ): List<CardUIState>
    abstract suspend fun getByID(
        imei_maker: String,
        imei_card: String,
    ): CardUIState?
    abstract suspend fun count(): Int
    abstract suspend fun populate()

    abstract suspend fun insert(cardUIState: CardUIState)
    abstract suspend fun update(cardUIState: CardUIState)
    abstract suspend fun delete(cardUIState: CardUIState)
    abstract suspend fun getMyIMEI(): String?
    abstract suspend fun getLanguage(): String?
    abstract suspend fun setLanguage(s: String)
}