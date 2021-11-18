import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import hu.gyeni.bolcsessegek.ListActivity
import hu.gyeni.bolcsessegek.MainActivity
import hu.gyeni.bolcsessegek.R
import hu.gyeni.bolcsessegek.databinding.DayListItemBinding
import hu.gyeni.bolcsessegek.model.DayModel
import java.text.SimpleDateFormat
import java.util.*

class DayAdapter(private val listener: DayAdapter.DayModelClickListener) :
    RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    private val items = mutableListOf<DayModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DayViewHolder(
            DayListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val quoteItem = items[position]

        holder.binding.dayListBlobDream.setImageResource(R.drawable.dream_active)
        holder.binding.dayListBlobLove.setImageResource(R.drawable.love_active)
        holder.binding.dayListBlobHappy.setImageResource(R.drawable.happy_active)
        holder.binding.dayListBlobJoke.setImageResource(R.drawable.joke_active)
        holder.binding.dayListBlobWisdom.setImageResource(R.drawable.wisdom_active)
        holder.binding.dayListBlobAngst.setImageResource(R.drawable.angst_active)

        if (quoteItem.b1)
            holder.binding.dayListBlobDream.visibility = View.VISIBLE
        else
            holder.binding.dayListBlobDream.visibility = View.GONE
        if (quoteItem.b2)
            holder.binding.dayListBlobLove.visibility = View.VISIBLE
        else
            holder.binding.dayListBlobLove.visibility = View.GONE
        if (quoteItem.b3)
            holder.binding.dayListBlobHappy.visibility = View.VISIBLE
        else
            holder.binding.dayListBlobHappy.visibility = View.GONE
        if (quoteItem.b4)
            holder.binding.dayListBlobJoke.visibility = View.VISIBLE
        else
            holder.binding.dayListBlobJoke.visibility = View.GONE
        if (quoteItem.b5)
            holder.binding.dayListBlobWisdom.visibility = View.VISIBLE
        else
            holder.binding.dayListBlobWisdom.visibility = View.GONE
        if (quoteItem.b6)
            holder.binding.dayListBlobAngst.visibility = View.VISIBLE
        else
            holder.binding.dayListBlobAngst.visibility = View.GONE


        holder.binding.nameOfTheDay.text = quoteItem.dayName
        holder.binding.numOfTheDay.text = quoteItem.day.toString()

        holder.binding.root.setOnClickListener {
            listener.onItemClicked(quoteItem)
        }

    }

    fun addItem(item: DayModel) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(allEntries: List<DayModel>) {
        items.clear()
        items.addAll(allEntries)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size

    interface DayModelClickListener {
        fun onItemClicked(item: DayModel)
    }

    inner class DayViewHolder(val binding: DayListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
