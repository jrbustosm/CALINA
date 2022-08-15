package co.edu.ut.jrbustosm.calina.domain.populate

import android.content.Context
import co.edu.ut.jrbustosm.calina.data.db.Card
import co.edu.ut.jrbustosm.calina.domain.GenIMEI
import java.util.*

abstract class GetPopulateDB(val context: Context) {

    private val isRootTest = false
    protected val myIMEI = if(isRootTest) "CALINA_HxH_1729" else GenIMEI()()

    abstract operator fun invoke(): Sequence<Card>
}