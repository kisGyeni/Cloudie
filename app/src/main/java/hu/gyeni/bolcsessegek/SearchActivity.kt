package hu.gyeni.bolcsessegek

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import hu.gyeni.bolcsessegek.data.QuoteEntry
import java.text.SimpleDateFormat
import java.util.*

class SearchActivity : AppCompatActivity() {

    private lateinit var dreamSelector: ImageView
    private lateinit var loveSelector: ImageView
    private lateinit var happinessSelector: ImageView
    private lateinit var jokeSelector: ImageView
    private lateinit var wisdomSelector: ImageView
    private lateinit var angstSelector: ImageView

    private lateinit var finalSearchText: TextView

    private lateinit var searchAsButton: Button
    private lateinit var searchAsText: SearchView

    private lateinit var calendarSelector: CalendarView

    private var dateIntentString = ""                                                   // holds INTENT value
    private var selectedMoodIntentFilter: QuoteEntry.Mood = QuoteEntry.Mood.EMPTY       // holds INTENT data
    private var searchFilteredIntentString: String = "false"                            // holds INTENT value

    private var dateFormatString = " " // holds FORMATTING value
    private var searchTypeString = "" // holds FORMATTING value

    private lateinit var intentAsSearch: Intent

    val sdf = SimpleDateFormat("yyyy. MMMM dd.")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        intentAsSearch = Intent(this, ListActivity::class.java)


        dreamSelector = findViewById(R.id.dreamSearchFilter)
        loveSelector = findViewById(R.id.loveSearchFilter)
        happinessSelector = findViewById(R.id.happySearchFilter)
        jokeSelector = findViewById(R.id.jokeSearchFilter)
        wisdomSelector = findViewById(R.id.wisdomSearchFilter)
        angstSelector = findViewById(R.id.angstSearchFilter)

        finalSearchText = findViewById(R.id.finalSearchHint)

        searchAsButton = findViewById(R.id.searchButtonNoText)
        searchAsText = findViewById(R.id.searchBar)

        calendarSelector = findViewById(R.id.calendarView)



        setSwitchListeners()
        setMoodListeners()
        setCalendarListener()
        updateFinalTextHint()

        setSearchListeners()

    }


    private fun setSearchListeners() {
        searchAsButton.setOnClickListener {
            val intent: Intent = Intent(this, ListActivity::class.java).apply {
                putExtra("search_date", dateIntentString) // Empty string or yyyy. M. dd.
                putExtra("search_mood", QuoteEntry.Mood.toInt(selectedMoodIntentFilter)) // "EMPTY" or MOOD
                putExtra("search_text", "") // Empty string
                putExtra("title", "") // Indicate that it was a search by passing an empty string
            }
            startActivity(intent)
        }

        searchAsText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                intentAsSearch.putExtra("search_date", dateIntentString) // Empty string or yyyy. M. dd.
                intentAsSearch.putExtra("search_mood", QuoteEntry.Mood.toInt(selectedMoodIntentFilter)) // "EMPTY" or MOOD
                intentAsSearch.putExtra("search_text", query) // "text"
                intentAsSearch.putExtra("title", "") // Indicate that it was a by passing an empty string

                startActivity(intentAsSearch)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }


        })

            /*
            val intent: Intent = Intent(this, ListActivity::class.java).apply {
                putExtra("search_date", dateIntentString) // Empty string or yyyy. M. dd.
                putExtra("search_mood", selectedMoodIntentFilter.name) // "EMPTY" or MOOD
                putExtra("search_text", "text") // "text"
                putExtra("title", "") // Indicate that it was a search
            }
            startActivity(intent)
        }*/
    }

    private fun setCalendarListener() {
        calendarSelector.setOnDateChangeListener(object : CalendarView.OnDateChangeListener {
            override fun onSelectedDayChange(p0: CalendarView, year: Int, month: Int, day: Int) {

                //dateIntentString = year.toString() + ". " + String.format("%02d", month+1) + ". " + String.format("%02d", day) + ".
                dateIntentString = SimpleDateFormatDeEzAzEnyem(Date(year-1900, month, day))
                updateFinalTextHint()
            }
        })


    }

    private fun setMoodListeners() {


        dreamSelector.setOnClickListener {
            deactivateAllMoodSelectors()
            dreamSelector.setImageResource(R.drawable.dream_active)
            selectedMoodIntentFilter = QuoteEntry.Mood.DREAM
            updateFinalTextHint()
        }
        loveSelector.setOnClickListener {
            deactivateAllMoodSelectors()
            loveSelector.setImageResource(R.drawable.love_active)
            selectedMoodIntentFilter = QuoteEntry.Mood.LOVE
            updateFinalTextHint()
        }
        happinessSelector.setOnClickListener {
            deactivateAllMoodSelectors()
            happinessSelector.setImageResource(R.drawable.happy_active)
            selectedMoodIntentFilter = QuoteEntry.Mood.HAPPINESS
            updateFinalTextHint()
        }
        jokeSelector.setOnClickListener {
            deactivateAllMoodSelectors()
            jokeSelector.setImageResource(R.drawable.joke_active)
            selectedMoodIntentFilter = QuoteEntry.Mood.JOKE
            updateFinalTextHint()
        }
        wisdomSelector.setOnClickListener {
            deactivateAllMoodSelectors()
            wisdomSelector.setImageResource(R.drawable.wisdom_active)
            selectedMoodIntentFilter = QuoteEntry.Mood.WISDOM
            updateFinalTextHint()
        }
        angstSelector.setOnClickListener {
            deactivateAllMoodSelectors()
            angstSelector.setImageResource(R.drawable.angst_active)
            selectedMoodIntentFilter = QuoteEntry.Mood.ANGST
            updateFinalTextHint()
        }

    }

    fun setSwitchListeners() {
        val calendarSwitch: Switch = findViewById(R.id.showCalendar)
        val calendaricon: ImageView = findViewById(R.id.calendarIcon)
        val calendarActually: CalendarView = findViewById(R.id.calendarView)
        calendarActually.visibility = View.INVISIBLE

        val moodSwitch: Switch = findViewById(R.id.showMoodSelector)
        val moodicon: ImageView = findViewById(R.id.moodIcon)
        val buttonsActually: LinearLayout = findViewById(R.id.MoodSelector)
        buttonsActually.visibility = View.INVISIBLE

        val searchSwitch: Switch = findViewById(R.id.showSearchBar)
        val searchicon: ImageView = findViewById(R.id.textIcon)
        val searchActually: SearchView = findViewById(R.id.searchBar)
        searchActually.visibility = View.GONE

        moodSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                moodicon.visibility = View.INVISIBLE
                buttonsActually.visibility = View.VISIBLE
            } else {
                // The switch is disabled
                moodicon.visibility = View.VISIBLE
                buttonsActually.visibility = View.INVISIBLE
                deactivateAllMoodSelectors()
                selectedMoodIntentFilter = QuoteEntry.Mood.EMPTY
                updateFinalTextHint()
            }
        }

        searchSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                searchicon.visibility = View.INVISIBLE
                searchActually.visibility = View.VISIBLE
                searchAsButton.visibility = View.GONE
                searchAsText.visibility = View.VISIBLE
                searchTypeString = "that contain:"
                searchFilteredIntentString = "true"
                updateFinalTextHint()
            } else {
                // The switch is disabled
                searchicon.visibility = View.VISIBLE
                searchActually.visibility = View.INVISIBLE
                searchAsButton.visibility = View.VISIBLE
                searchAsText.visibility = View.GONE
                searchTypeString = ""
                searchFilteredIntentString = "false"
                updateFinalTextHint()
            }
        }
        calendarSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                calendaricon.visibility = View.INVISIBLE
                calendarActually.visibility = View.VISIBLE
                calendarSelector.setDate(Date().time, true, true)
                dateFormatString += "recorded on "
                dateIntentString =  sdf.format(Date())
                updateFinalTextHint()
            } else {
                // The switch is disabled
                calendaricon.visibility = View.VISIBLE
                calendarActually.visibility = View.INVISIBLE
                dateIntentString = ""
                dateFormatString = " "
                updateFinalTextHint()
            }
        }
    }

    fun deactivateAllMoodSelectors() {
        dreamSelector.setImageResource(R.drawable.dream_inactive)
        loveSelector.setImageResource(R.drawable.love_inactive)
        happinessSelector.setImageResource(R.drawable.happy_inactive)
        jokeSelector.setImageResource(R.drawable.joke_inactive)
        wisdomSelector.setImageResource(R.drawable.wisdom_inactive)
        angstSelector.setImageResource(R.drawable.angst_inactive)
    }

    fun updateFinalTextHint() {
        //Search for all MOOD quotes recorded on DAY that contains the text STRING
        var finalStringBuilder = "Search for all "
        if (selectedMoodIntentFilter != QuoteEntry.Mood.EMPTY)
            finalStringBuilder += selectedMoodIntentFilter.name.lowercase() + " "
        finalStringBuilder +=  "quotes" + dateFormatString + dateIntentString + searchTypeString

        finalSearchText.text = finalStringBuilder
    }
}

    fun SimpleDateFormatDeEzAzEnyem(date: Date) : String {
    return SimpleDateFormat("yyyy. MMMM dd.").format(date)
}
