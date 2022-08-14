package co.edu.ut.jrbustosm.calina.domain.triggers

import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.DifficultyCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class TriggerTest{

    private lateinit var card: CardUIState
    private val triggers = listOf(
        "G_N:666_C:DIAMANTE ARCOÍRIS:RARA PIEDRA PRECIOSA:R:W:888:item311:1#G:Respuesta Correcta|U_T_M:100",
        "U_T_M:100",
        "E_W:RESPUESTA_M:100#E#G:Respuesta Correcta",
        "Z_T_X|Z_C:5_M:100#R",
        "R_R:010_Y:X",
        "V_T_M:100",
        "U_T_I:item64",
        "D_T_C:FANTASMA:CARTA FANTASMA:R:S:666:item210:0",
        "L_T_X|L_C:2_U#R",
        "U_R:200_M:100",
        "U_T_H"
    )

    @Before
    fun onBefore(){
        println("BEGIN##################################")
    }

    @Test
    fun test(){
        triggers.forEachIndexed{index, txtTrigger ->
            println("TRIGGER: $txtTrigger")
            card = CardUIState(
                trigger = txtTrigger,
                title = index.toString(),
                description = index.toString(),
                imei_card = index.toString(),
                imei_owner = index.toString(),
                imei_maker = index.toString(),
                state = StateCardCALINA.NORMAL,
                cash = 0,
                number = index,
                isSelect = false,
                isSecondary = false,
                isTransfer = false,
                isEdit = false,
                levels = "",
                level = "",
                cash_symbol = '$',
                date_expire = null,
                date_create = Date(),
                scope = null,
                count = 0,
                imageResource = "item1",
                difficulty = DifficultyCardCALINA.EASY,
                type = TypeCardCALINA.INFORMATION,
                isCloneable = false,
                isSymbol = false,
                date_reg = null,
                url = ""
            )
        }
    }

    @After
    fun onAfter(){
        println("END##################################")
    }
}