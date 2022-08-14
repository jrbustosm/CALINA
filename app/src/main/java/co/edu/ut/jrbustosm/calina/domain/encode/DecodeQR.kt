package co.edu.ut.jrbustosm.calina.domain.encode

import android.content.Context
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

abstract class DecodeQR {

    abstract operator fun invoke(text: String):CardUIState
}