package hu.gyeni.bolcsessegek

import QuoteAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import hu.gyeni.bolcsessegek.data.QuoteEntry
import hu.gyeni.bolcsessegek.data.QuoteEntryDatabase
import hu.gyeni.bolcsessegek.databinding.ActivityListBinding
import hu.gyeni.bolcsessegek.fragment.NewQuoteDialogFragment
import kotlin.concurrent.thread

class ListActivity : AppCompatActivity(), QuoteAdapter.QuoteEntryClickListener, NewQuoteDialogFragment.NewQuoteDialogListener {

    private lateinit var binding: ActivityListBinding

    private lateinit var database: QuoteEntryDatabase
    private lateinit var adapter: QuoteAdapter
    private var titleBuilder: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setSupportActionBar(binding.toolbar)
        database = QuoteEntryDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            NewQuoteDialogFragment().show(
                supportFragmentManager,
                NewQuoteDialogFragment.TAG
            )
        }

        if (intent.getStringExtra("autoadd") == "true") {
            binding.fab.performClick()
        }

        val nameOfDay: TextView = findViewById(R.id.onThisDay)
        if (intent.getStringExtra("title") != "") {           // If title != "", it was a day
            nameOfDay.text = intent.getStringExtra("title")
        }
        else {                                                      //If title == "", it was a search
            titleBuilder = "Showing all results"
            if (intent.getStringExtra("search_date") != "" || intent.getIntExtra("search_mood", 6) != 6 || intent.getStringExtra("search_text") != "")
                titleBuilder += ", where:"
            else
                titleBuilder += ":"

            if (intent.getStringExtra("search_date") != "")
                titleBuilder += "\nDay: " + intent.getStringExtra("search_date")
            if (intent.getIntExtra("search_mood", 6) != 6)
                titleBuilder += "\nMood: " + intent.getIntExtra("search_mood", 6)
            if (intent.getStringExtra("search_text") != "")
                titleBuilder += "\nText: " + intent.getStringExtra("search_text")
            nameOfDay.text = titleBuilder
            nameOfDay.text = "Results:"
        }
        initRecyclerView()
        //setContentView(R.layout.activity_main)
    }

    private fun initRecyclerView() {
        adapter = QuoteAdapter(this)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            var items: List<QuoteEntry>
            if (intent.getStringExtra("title") != "")
                items = database.QuoteEntryDao().getDailyList(intent.getStringExtra("title"))
            else {
                val high = intent.getIntExtra("search_mood",6)
                val low: Int = if (high == 6) 0 else high
                val textToSearch : String? = "%"+intent.getStringExtra("search_text")+"%"
                val searchDate: String? = "%" + intent.getStringExtra("search_date") + "%"
                items = database.QuoteEntryDao().complexSearch(textToSearch, low, high, searchDate)
            }
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: QuoteEntry) {
        thread {
            database.QuoteEntryDao().update(item)
            Log.d("MainActivity", "QuoteItem update was successful")
        }
    }

    override fun onQuoteEntryCreated(newItem: QuoteEntry) {
        thread {
            val insertId = database.QuoteEntryDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }
}