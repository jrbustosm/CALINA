package co.edu.ut.jrbustosm.calina.data.db

import androidx.room.Entity
import java.util.*

@Entity(tableName = "Card", primaryKeys = ["imei_maker", "imei_card"])
data class Card(
    var imei_maker: String,         //Primary key
    var imei_card: String,          //Primary key
    var title: String,
    var description: String,
    var type: Char,                 //(X) Action, (I) Information, (R) Reward, and (G) Group
    var difficulty: Char,           //(C) Very Easy, (B) Easy, (A) Normal, (S) Hard (SS as W) Very Hard, (X) Special
    var number: Int,                //Number of card is different of id
    var imei_owner: String,         //Change if state is transfer
    var image: String,              //item1 - item404 or symbol1 - symbol34
    var trigger: String = "",       //Read documentation for syntax
    var state: Char = 'N',          //Possible states: (N) normal, (U) used, (B) buy
    var count: Int,
    var date_create: Date,
    var date_expire: Date?,         //If null, never expire
    var date_reg: Date?,            //date for register
    var scope: String? = null,      //If null then scope toMaker, else toGroup where imei_card==scope && imei_marker is same
    var isTransfer: Boolean = true,
    var isCloneable: Boolean = false,
    //SPECIAL FOR INFORMATION CARD
    var isEdit:Boolean = false,
    //SPECIALS FOR GROUP CARDS
    var cash: Int = 0,
    var cash_symbol: Char = '¢',
    var isSelect: Boolean = false,
    var isSecondary: Boolean = false,
    var levels: String = "",            //UNSUPPORTED
    var level: String = "",             //UNSUPPORTED
    var url: String = ""
)
