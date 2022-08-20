package kz.ali.alarmclock.ui.presentation.createalarm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kz.ali.alarmclock.R

class NumbersPickerAdapter(
    size: Int
) : RecyclerView.Adapter<NumbersViewHolder>() {

    private val listOfFragments = createAList(size)
    private val newList = mutableListOf<Int>()
    init {
        newList.apply {
            add(listOfFragments.last())
            addAll(listOfFragments)
            add(listOfFragments.first())
        }
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    private fun createAList(size: Int): List<Int> {
        val list = mutableListOf<Int>()
        if (size >= 0) {
            for (i in 0 until size) {
                list.add(i)
            }
        }
        return list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        return NumbersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.time_element_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        holder.bind(newList[position])
    }
}

class NumbersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val number: MaterialTextView = itemView.findViewById(R.id.number)

    fun bind(value: Int) {
        number.text = if (value in 0..9) "0$value" else value.toString()
    }
}
