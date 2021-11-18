package hu.gyeni.bolcsessegek.model

import androidx.appcompat.app.AppCompatActivity
import hu.gyeni.bolcsessegek.MainActivity
import hu.gyeni.bolcsessegek.data.QuoteEntry
import hu.gyeni.bolcsessegek.data.QuoteEntryDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.Random.Default.nextBoolean


class DayModel(var inDay: Int, var moods: List<QuoteEntry.Mood>) {

    val fullDateFormat = SimpleDateFormat("yyyy. MMMM ")
    val nameOfDayFormat = SimpleDateFormat("EEEE")

    val cal = Calendar.getInstance()


    var day: Int = inDay
    var dayName: String = formatDate().capitalize()
    var fullDate: String = fullDateFormat.format(Date()).toString() + day.toString() + "."

    var b1: Boolean = false
    var b2: Boolean = false
    var b3: Boolean = false
    var b4: Boolean = false
    var b5: Boolean = false
    var b6: Boolean = false

    fun formatDate(): String {

        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        cal.set(Calendar.DAY_OF_MONTH, inDay);

        return nameOfDayFormat.format(cal.time)
    }

    fun generateDayKey(): String {
        return fullDateFormat.format(cal.time) + inDay.toString() + "."
    }

    fun fillBulbs() {
                if (moods.contains(QuoteEntry.Mood.DREAM)) {
                    b1 = true
                }
                if (moods.contains(QuoteEntry.Mood.LOVE)) {
                    b2 = true
                }
                if (moods.contains(QuoteEntry.Mood.HAPPINESS)) {
                    b3 = true
                }
                if (moods.contains(QuoteEntry.Mood.JOKE)) {
                    b4 = true
                }
                if (moods.contains(QuoteEntry.Mood.WISDOM)) {
                    b5 = true
                }
                if (moods.contains(QuoteEntry.Mood.ANGST)) {
                    b6 = true
                }

    }

}