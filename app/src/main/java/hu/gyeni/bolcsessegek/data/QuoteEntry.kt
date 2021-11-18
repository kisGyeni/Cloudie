package hu.gyeni.bolcsessegek.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "quoteentry")
data class QuoteEntry(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "quote") var quote: String,
    @ColumnInfo(name = "timestamp") var timestamp: String,
    @ColumnInfo(name = "mood") var mood: Mood,
    @ColumnInfo(name = "date") var date: String
) {
    enum class Mood {
        DREAM, LOVE, HAPPINESS, JOKE, WISDOM, ANGST, EMPTY;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Mood? {
                var ret: Mood? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(mood: Mood): Int {
                return mood.ordinal
            }
        }
    }
}
