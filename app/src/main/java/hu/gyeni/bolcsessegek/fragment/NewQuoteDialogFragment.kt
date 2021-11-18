package hu.gyeni.bolcsessegek.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.gyeni.bolcsessegek.R
import hu.gyeni.bolcsessegek.data.QuoteEntry
import hu.gyeni.bolcsessegek.databinding.DialogNewQuoteBinding
import java.text.SimpleDateFormat
import java.util.*

class NewQuoteDialogFragment : DialogFragment() {
    interface NewQuoteDialogListener {
        fun onQuoteEntryCreated(newItem: QuoteEntry)
    }

    private lateinit var listener: NewQuoteDialogListener

    private lateinit var binding: DialogNewQuoteBinding

    private val timestampFormat = SimpleDateFormat("kk:mm")
    private val keyFormat = SimpleDateFormat("yyyy. MMMM dd.")
    private var selectedMood = QuoteEntry.Mood.EMPTY

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewQuoteDialogListener
            ?: throw RuntimeException("Activity must implement the NewQuoteDialogListener interface!")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogNewQuoteBinding.inflate(LayoutInflater.from(context))

        inactivate_all()
        binding.liveText.visibility = View.VISIBLE

        binding.blobOfDream.setOnClickListener {
            inactivate_all()
            selectedMood = QuoteEntry.Mood.DREAM
            binding.blobOfDream.setImageResource(R.drawable.dream_active)
            binding.liveTextDream.visibility = View.VISIBLE
        }
        binding.blobOfLove.setOnClickListener {
            inactivate_all()
            selectedMood = QuoteEntry.Mood.LOVE
            binding.blobOfLove.setImageResource(R.drawable.love_active)
            binding.liveTextLove.visibility = View.VISIBLE
        }
        binding.blobOfHappiness.setOnClickListener {
            inactivate_all()
            selectedMood = QuoteEntry.Mood.HAPPINESS
            binding.blobOfHappiness.setImageResource(R.drawable.happy_active)
            binding.liveTextHappiness.visibility = View.VISIBLE
        }
        binding.blobOfJoke.setOnClickListener {
            inactivate_all()
            selectedMood = QuoteEntry.Mood.JOKE
            binding.blobOfJoke.setImageResource(R.drawable.joke_active)
            binding.liveTextJoke.visibility = View.VISIBLE
        }
        binding.blobOfWisdom.setOnClickListener {
            inactivate_all()
            selectedMood = QuoteEntry.Mood.WISDOM
            binding.blobOfWisdom.setImageResource(R.drawable.wisdom_active)
            binding.liveTextWisdom.visibility = View.VISIBLE
        }
        binding.blobOfAngst.setOnClickListener {
            inactivate_all()
            selectedMood = QuoteEntry.Mood.ANGST
            binding.blobOfAngst.setImageResource(R.drawable.angst_active)
            binding.liveTextAngst.visibility = View.VISIBLE
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_quote)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->
                if (isValid()) {
                    listener.onQuoteEntryCreated(getQuoteEntry())
                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }

    private fun inactivate_all() {
        binding.blobOfAngst.setImageResource(R.drawable.angst_inactive)
        binding.blobOfWisdom.setImageResource(R.drawable.wisdom_inactive)
        binding.blobOfJoke.setImageResource(R.drawable.joke_inactive)
        binding.blobOfHappiness.setImageResource(R.drawable.happy_inactive)
        binding.blobOfLove.setImageResource(R.drawable.love_inactive)
        binding.blobOfDream.setImageResource(R.drawable.dream_inactive)

        binding.liveText.visibility = View.GONE
        binding.liveTextAngst.visibility = View.GONE
        binding.liveTextWisdom.visibility = View.GONE
        binding.liveTextJoke.visibility = View.GONE
        binding.liveTextHappiness.visibility = View.GONE
        binding.liveTextLove.visibility = View.GONE
        binding.liveTextDream.visibility = View.GONE
    }

    private fun isValid() = binding.inQuote.text.isNotEmpty()

    private fun getQuoteEntry() = QuoteEntry(
          quote = binding.inQuote.text.toString(),
          mood = selectedMood,
          timestamp = timestampFormat.format(Date()),
          date = keyFormat.format(Date()).toString()
        )

    companion object {
        const val TAG = "NewQuoteEntryDialogFragment"
    }
}
