package ru.practicum.android.diploma.filter.ui.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Industry

class IndustryViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.industry_item, parentView, false)
) {

    private val industryNameView: RadioButton by lazy { itemView.findViewById(R.id.industry_item) }

    fun bind(model: Industry, checkedIndustry: Industry?) {
        industryNameView.text = model.name
        industryNameView.isChecked = model == checkedIndustry
    }
}
