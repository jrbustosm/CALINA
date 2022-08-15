package co.edu.ut.jrbustosm.calina.domain.populate

import android.content.Context
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.data.db.Card
import co.edu.ut.jrbustosm.calina.viewmodels.states.DifficultyCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA
import java.util.*

class GetStartCALINADB(context: Context): GetPopulateDB(context) {

    override operator fun invoke(): Sequence<Card> = sequence<Card> {
        val l = listOf(
            mapOf(
                "imei_card" to "0",
                "title" to context.getString(R.string.titleCard2),
                "description" to context.getString(R.string.descCard2),
                "type" to TypeCardCALINA.GROUP,
                "image" to "symbol0",
                "number" to 0,
                "cash" to 0,
                "state" to StateCardCALINA.NORMAL,
                "trigger" to "W_N:6_C:${context.getString(R.string.easter_egg)} 1:${context.getString(R.string.congratulation_easter_egg)}:R:W:100:item272:0#V",
                "url" to "http://tinyurl.com/53wuyf4f",
                "difficulty" to DifficultyCardCALINA.VERY_EASY.id,
                "isCloneable" to true,
                "lang" to "en"
            ),
        )
        l.forEach {
            yield(Card(
                imei_card = it["imei_card"] as String,
                imei_maker = context.getString(R.string.IMEI_BI),
                title = it["title"] as String,
                description = it["description"] as String,
                difficulty = it["difficulty"] as Char,
                state = (it["state"] as StateCardCALINA).id,
                type = (it["type"] as TypeCardCALINA).id,
                image = it["image"] as String,
                date_create = Date(),
                imei_owner = myIMEI,
                number = it["number"] as Int,
                count = 0,
                trigger = it["trigger"] as String,
                scope = null,
                date_expire = null,
                isEdit = false,
                isCloneable = it["isCloneable"] as Boolean,
                isTransfer = false,
                cash = it["cash"] as Int,
                cash_symbol = if (it["type"] as TypeCardCALINA == TypeCardCALINA.GROUP) '¢' else '\u0000',
                isSelect = it["type"] as TypeCardCALINA == TypeCardCALINA.GROUP,
                isSecondary = false,
                levels = "",
                level = "",
                date_reg = null,
                url = it["url"] as String,
                lang = it["lang"] as String
            ))
        }
    }
}