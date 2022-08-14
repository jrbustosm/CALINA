package co.edu.ut.jrbustosm.calina.domain.crypto

import java.util.*

class Decode {
    operator fun invoke(text: String): String{
        val decoder = Base64.getDecoder()
        return String(decoder.decode(text))
    }
}