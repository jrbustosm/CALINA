package co.edu.ut.jrbustosm.calina.data

import android.content.Context
import co.edu.ut.jrbustosm.calina.data.db.Card
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.DifficultyCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun Context.getResource(name:String): Int {
    return this.resources.getIdentifier(name , "drawable", this.packageName)
}

fun Card.toCardUI():CardUIState = CardUIState(
    imei_card = imei_card,
    imei_maker = imei_maker,
    title = title,
    description = description,
    imageResource = image,
    type = TypeCardCALINA.getType(type),
    difficulty = DifficultyCardCALINA.getDifficulty(difficulty),
    isSymbol = image.startsWith("symbol"),
    number = number,
    imei_owner = imei_owner,
    state = StateCardCALINA.getState(state = state),
    trigger = trigger,
    date_create = date_create,
    date_expire = date_expire,
    count = count,
    scope = scope,
    isTransfer = isTransfer,
    isCloneable = isCloneable,
    isEdit = isEdit,
    cash = cash,
    cash_symbol = cash_symbol,
    isSelect = isSelect,
    isSecondary = isSecondary,
    levels = levels,
    level = level,
    date_reg = date_reg,
    url = url,
    lang = lang,
    isDeletable = isDeletable
)

fun CardUIState.toCard():Card = Card(
    imei_card = imei_card,
    imei_maker = imei_maker,
    title = title,
    description = description,
    image = imageResource,
    number = number,
    imei_owner = imei_owner,
    type = type.id,
    difficulty = difficulty.id,
    count = count,
    date_create = date_create,
    state = state.id,
    trigger = trigger,
    scope = scope,
    date_expire = date_expire,
    isTransfer = isTransfer,
    isCloneable = isCloneable,
    isEdit = isEdit,
    cash = cash,
    cash_symbol = cash_symbol,
    isSelect = isSelect,
    isSecondary = isSecondary,
    levels = levels,
    level = level,
    date_reg = date_reg,
    url = url,
    lang = lang,
    isDeletable = isDeletable
)

fun LocalDate.asDate(): Date =
    Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())

fun Date.asLocalDate(): LocalDate =
    Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDate()