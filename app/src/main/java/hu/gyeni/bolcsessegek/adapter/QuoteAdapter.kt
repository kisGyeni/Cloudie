import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.gyeni.bolcsessegek.R
import hu.gyeni.bolcsessegek.data.QuoteEntry
import hu.gyeni.bolcsessegek.databinding.QuoteEntryBinding

class QuoteAdapter(private val listener: QuoteEntryClickListener) :
    RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    private val items = mutableListOf<QuoteEntry>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = QuoteViewHolder(
        QuoteEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quoteItem = items[position]

        holder.binding.moodIcon.setImageResource(getImageResource(quoteItem.mood))
        holder.binding.quoteText.text = quoteItem.quote
        holder.binding.timestampText.text = quoteItem.timestamp

    }

    @DrawableRes()
    private fun getImageResource(category: QuoteEntry.Mood): Int {
        return when (category) {
            QuoteEntry.Mood.DREAM -> R.drawable.dream_active
            QuoteEntry.Mood.LOVE -> R.drawable.love_active
            QuoteEntry.Mood.HAPPINESS -> R.drawable.happy_active
            QuoteEntry.Mood.JOKE -> R.drawable.joke_active
            QuoteEntry.Mood.WISDOM -> R.drawable.wisdom_active
            QuoteEntry.Mood.ANGST -> R.drawable.angst_active
            QuoteEntry.Mood.EMPTY -> R.drawable.angst_inactive
        }
    }

    fun addItem(item: QuoteEntry) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(shoppingItems: List<QuoteEntry>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size

    interface QuoteEntryClickListener {
        fun onItemChanged(item: QuoteEntry)
    }

    inner class QuoteViewHolder(val binding: QuoteEntryBinding) : RecyclerView.ViewHolder(binding.root)
}
