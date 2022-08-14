package co.edu.ut.jrbustosm.calina.domain

import java.util.*
import kotlin.random.Random

class GenIMEI{

    operator fun invoke(): String{
        return "${System.currentTimeMillis()}_${Random.nextInt(0, 1000)}"
    }
}