package co.edu.ut.jrbustosm.calina.domain.encode

import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

abstract class EncodeQR {

    abstract operator fun invoke(cardUIState: CardUIState):String
}