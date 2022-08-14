package co.edu.ut.jrbustosm.calina.viewmodels.states

import java.io.IOException

data class ErrorMessage(val id: Long, val message: String?)

fun getMessagesFromThrowable(ioe: IOException): ErrorMessage {
    return ErrorMessage(ioe.hashCode().toLong(), ioe.message)
}


