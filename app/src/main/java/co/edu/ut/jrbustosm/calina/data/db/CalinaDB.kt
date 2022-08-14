package co.edu.ut.jrbustosm.calina.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Card::class],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class CalinaDB: RoomDatabase(){

    abstract fun cardDao(): CardDao

}