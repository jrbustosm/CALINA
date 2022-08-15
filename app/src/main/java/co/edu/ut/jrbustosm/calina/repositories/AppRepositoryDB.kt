package co.edu.ut.jrbustosm.calina.repositories

import android.content.Context
import androidx.room.Room
import co.edu.ut.jrbustosm.calina.R
import co.edu.ut.jrbustosm.calina.data.db.CalinaDB
import co.edu.ut.jrbustosm.calina.data.toCard
import co.edu.ut.jrbustosm.calina.data.toCardUI
import co.edu.ut.jrbustosm.calina.domain.populate.GetStartCALINADB
import co.edu.ut.jrbustosm.calina.viewmodels.states.CardUIState
import co.edu.ut.jrbustosm.calina.viewmodels.states.StateCardCALINA
import co.edu.ut.jrbustosm.calina.viewmodels.states.TypeCardCALINA

class AppRepositoryDB(context: Context) : AppRepository(context){

    private val room = Room
        .databaseBuilder(context, CalinaDB::class.java, "CALINA")
        .build()

    override suspend fun byType(
        type: TypeCardCALINA?,
        state: StateCardCALINA?,
        currentGroup: CardUIState?,
    ): List<CardUIState> {
        return room.cardDao().getAll()
            .filter {
                (type == null || it.type == type.id) &&
                (state == null || it.state == state.id) &&
                (
                    (currentGroup == null) ||
                    (currentGroup.imei_maker == it.imei_maker && currentGroup.imei_card == it.imei_card) ||
                    (
                        (it.imei_maker == currentGroup.imei_maker) &&
                        (
                            (it.scope==null && it.type != TypeCardCALINA.GROUP.id) ||
                            (it.scope==null && it.type == TypeCardCALINA.GROUP.id && it.isSecondary) ||
                            (it.scope == currentGroup.imei_card && it.type == TypeCardCALINA.GROUP.id && it.isSecondary) ||
                            (it.scope == currentGroup.imei_card && it.type != TypeCardCALINA.GROUP.id)
                        )
                    )
                )
            }.map { it.toCardUI() }
    }

    override suspend fun getByID(
        imei_maker: String,
        imei_card: String,
    ): CardUIState? =
        room.cardDao().getCard(imei_maker, imei_card)?.toCardUI()

    override suspend fun count(): Int = room.cardDao().count()

    override suspend fun insert(cardUIState: CardUIState) =
        room.cardDao().insert(cardUIState.toCard())

    override suspend fun update(cardUIState: CardUIState) =
        room.cardDao().update(cardUIState.toCard())

    override suspend fun delete(cardUIState: CardUIState) =
        room.cardDao().delete(cardUIState.toCard())

    override suspend fun getMyIMEI(): String =
        room.cardDao().getCard(context.getString(R.string.IMEI_BI), "0")?.imei_owner!!

    override suspend fun getLanguage(): String {
        val card = room.cardDao().getCard(context.getString(R.string.IMEI_BI), "0")
        return card!!.lang
    }

    override suspend fun setLanguage(s:String){
        val card = room.cardDao().getCard(context.getString(R.string.IMEI_BI), "0")
        if(card!=null) {
            room.cardDao().update(card.copy(
                lang = s
            ))
        }
    }



    override suspend fun populate() = room.cardDao().insert(GetStartCALINADB(context)().toList())
}