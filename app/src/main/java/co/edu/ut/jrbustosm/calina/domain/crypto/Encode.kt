package co.edu.ut.jrbustosm.calina.domain.crypto

import java.util.*

class Encode {
    operator fun invoke(text: String): String{
        val encoder = Base64.getEncoder()
        return encoder.encodeToString(text.toByteArray())
    }
}