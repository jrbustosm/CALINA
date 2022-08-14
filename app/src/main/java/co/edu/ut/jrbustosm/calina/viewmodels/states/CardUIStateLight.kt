package co.edu.ut.jrbustosm.calina.viewmodels.states

import java.util.*

data class CardUIStateLight (
    val imei_card: String,
    val imei_maker: String,
    val title: String,
    val description: String,
    val imageResource: String,
    val type: TypeCardCALINA,
    val difficulty: DifficultyCardCALINA,
    val isSymbol: Boolean,
    val number: Int,
    val imei_owner: String,
    val trigger: String,
    val state: StateCardCALINA,
    val count: Int,
    val date_create: Date,
    val date_expire: Date?,
    val date_reg: Date?,
    val scope: String?,
    val isTransfer: Boolean,
    val isCloneable: Boolean,
    val isEdit: Boolean,
    val cash: Int,
    val cash_symbol: Char,
    val isSelect: Boolean,
    val isSecondary: Boolean,
    val levels: String,
    val level: String,
    val url: String
)
