package co.edu.ut.jrbustosm.calina.domain.encode

import co.edu.ut.jrbustosm.calina.domain.crypto.*
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState

class EncodeQRCard: EncodeQR() {

    override fun invoke(cardUIState: CardUIState): String {
        return Mask()(
                Encode()(
                    Compress()(cardUIState.toJson())
                )
            )
    }
}