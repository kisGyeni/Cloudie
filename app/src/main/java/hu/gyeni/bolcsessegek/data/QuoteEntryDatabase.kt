package hu.gyeni.bolcsessegek.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [QuoteEntry::class], version = 1)
@TypeConverters(value = [QuoteEntry.Mood::class])
abstract class QuoteEntryDatabase : RoomDatabase() {
    abstract fun QuoteEntryDao(): QuoteEntryDao

    companion object {
        fun getDatabase(applicationContext: Context): QuoteEntryDatabase {
            return Room.databaseBuilder(
                applicationContext,
                QuoteEntryDatabase::class.java,
                "quote-entry"
            ).build();
        }
    }
}
