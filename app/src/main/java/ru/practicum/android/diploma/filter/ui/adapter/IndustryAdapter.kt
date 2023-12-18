package ru.practicum.android.diploma.filter.ui.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.viewholder.IndustryViewHolder

class IndustryAdapter(
    private var items: List<Industry>,
    private val clickListener: IndustryClickListener
) : RecyclerView.Adapter<IndustryViewHolder>() {

    private var checkedIndustry: Industry? = null
    private var prevPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        return IndustryViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]
        holder.bind(item, checkedIndustry)
        holder.itemView.setOnClickListener {
            checkedIndustry = item
            this.notifyItemChanged(prevPosition)
            prevPosition = position
            clickListener.onIndustryClick(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(values: List<Industry>) {
        items = values
        this.notifyDataSetChanged()
    }

    fun interface IndustryClickListener {
        fun onIndustryClick(industry: Industry)
    }
}
