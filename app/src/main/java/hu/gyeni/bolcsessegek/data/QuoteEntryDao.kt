package hu.gyeni.bolcsessegek.data

import androidx.room.*

@Dao
interface QuoteEntryDao {
    @Query("SELECT * FROM quoteentry")
    fun getAll(): List<QuoteEntry>

    @Query("SELECT DISTINCT mood FROM quoteentry")
    fun getAllDailyMoods(): List<QuoteEntry.Mood>

    @Query("SELECT DISTINCT mood FROM quoteentry WHERE date = :date")
    fun getDailyMoods(date: String): List<QuoteEntry.Mood>

    @Query("SELECT * FROM quoteentry WHERE date = :date")
    fun getDailyList(date: String?): List<QuoteEntry>

    @Query("SELECT * FROM quoteentry ORDER BY RANDOM() LIMIT 1")
    fun randomQuote(): QuoteEntry?

    //
    @Query("SELECT * FROM quoteentry WHERE quote LIKE :tts AND mood BETWEEN :low AND :high AND date LIKE :date")
    fun complexSearch(tts: String?, low: Int, high: Int, date: String?): List<QuoteEntry>

    @Insert
    fun insert(quoteItems: QuoteEntry): Long

    @Update
    fun update(quoteItem: QuoteEntry)

    @Delete
    fun deleteItem(quoteItem: QuoteEntry)
}