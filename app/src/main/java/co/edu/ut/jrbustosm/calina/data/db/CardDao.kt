package co.edu.ut.jrbustosm.calina.data.db

import androidx.room.*

@Dao
interface CardDao{

    @Query("SELECT * FROM Card")
    suspend fun getAll(): List<Card>

    @Query("SELECT * FROM Card WHERE imei_card = :imeiCard AND imei_maker = :imeiMaker")
    suspend fun getCard(imeiMaker: String, imeiCard: String): Card?

    @Query("SELECT COUNT(imei_card) FROM Card")
    suspend fun count(): Int

    @Update
    suspend fun update(card: Card)

    @Insert
    suspend fun insert(cards: List<Card>)

    @Insert
    suspend fun insert(card: Card)

    @Delete
    suspend fun delete(card: Card)
}