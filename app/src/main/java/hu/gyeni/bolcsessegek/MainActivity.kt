package hu.gyeni.bolcsessegek

import DayAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.gyeni.bolcsessegek.data.QuoteEntryDatabase
import hu.gyeni.bolcsessegek.databinding.ActivityMainBinding
import hu.gyeni.bolcsessegek.model.DayModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread



class MainActivity : AppCompatActivity(), DayAdapter.DayModelClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: QuoteEntryDatabase
    private lateinit var adapter: DayAdapter
    private var easterEggCnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = QuoteEntryDatabase.getDatabase(applicationContext)

        initClickables()
        //initSearchFocus()
        initRecyclerView()

        thread {
            val randomQuote = database.QuoteEntryDao().randomQuote()
            runOnUiThread {
                if (randomQuote != null) {
                    binding.quoteOfTheDay.text = "„ " + randomQuote.quote + " “"
                    binding.quoteOfTheDayDate.text = randomQuote.date + " " + randomQuote.timestamp
                }
            }
        }

        val dateText: TextView = findViewById(R.id.editTextDate)
        dateText.text = SimpleDateFormat("yyyy. M. dd.").format(Date())

        val monthText: TextView = findViewById(R.id.monthName)
        monthText.text = SimpleDateFormat("MMMM").format(Date()).uppercase(Locale.getDefault())

    }



    private fun initRecyclerView() {
        adapter = DayAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        generateMonthList()
    }

    private fun generateMonthList() {
        val items: ArrayList<DayModel> = ArrayList()

        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.clear(Calendar.MINUTE)
        cal.clear(Calendar.SECOND)
        cal.clear(Calendar.MILLISECOND)

        thread {
            for (i in 1..cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                cal.set(Calendar.DAY_OF_MONTH, i);
                val moods = database.QuoteEntryDao().getDailyMoods(SimpleDateFormat("yyyy. MMMM dd.").format(cal.time).toString())
                runOnUiThread {
                    var oneDay = DayModel(i, moods)
                    oneDay.fillBulbs()
                    items.add(oneDay)
                    adapter.update(items)
                }
            }

        }
    }

    override fun onItemClicked(item: DayModel) {
        val intent: Intent = Intent(this, ListActivity::class.java).apply {
            putExtra("title", item.fullDate)
        }
        startActivity(intent)
    }



    fun initClickables() {
        binding.fab.setOnClickListener{
            val intent: Intent = Intent(this, ListActivity::class.java).apply {
                putExtra("autoadd", "true")
                putExtra("title", SimpleDateFormat("yyyy. MMMM dd.").format(Date()).toString())
            }
            startActivity(intent)
        }

        binding.editTextDate.setOnClickListener {
            easterEggCnt++
            if (easterEggCnt == 22) {
                Toast.makeText(this, "Easter egg #2 found!", Toast.LENGTH_LONG).show()
                binding.editTextDate.text = "2222. 22. 22."
            }
            if (easterEggCnt > 22) {
                easterEggCnt = 0
                binding.editTextDate.text = SimpleDateFormat("yyyy. M. dd.").format(Date())
            }
        }

        binding.searchBlock.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        /*
        binding.tapout.setOnClickListener {
            binding.searchBtn.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.searchBtn.hideKeyboard()
            binding.searchBlock.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            binding.searchBtn.clearFocus()
        }

        binding.searchBtn.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                binding.searchBtn.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                binding.searchBtn.clearFocus()
                val searchQuery = binding.searchBtn.text.toString().trim()
                binding.searchBtn.text.clear()
                binding.searchBtn.hideKeyboard()
                binding.searchBlock.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT

                val intent: Intent = Intent(this, ListActivity::class.java).apply {
                    putExtra("nap", searchQuery)
                }
                startActivity(intent)

                return@OnKeyListener true
            }
            false
        })



    }
    fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
        layoutParams<ViewGroup.MarginLayoutParams> {
            left?.run { leftMargin = dpToPx(this) }
            top?.run { topMargin = dpToPx(this) }
            right?.run { rightMargin = dpToPx(this) }
            bottom?.run { bottomMargin = dpToPx(this) }
        }
    }

    inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
        if (layoutParams is T) block(layoutParams as T)
    }

    fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
    fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)

    private fun initSearchFocus() {
        binding.searchBtn.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.searchBtn.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                binding.searchBlock.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                binding.searchBtn.margin(right = 16F)
            }
            else
                binding.searchBtn.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
    }
         */

    }
}