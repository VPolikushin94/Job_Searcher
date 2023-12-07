package ru.practicum.android.diploma.search.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.search.domain.models.SearchedVacancy

class VacancyViewHolder(private val binding: VacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: SearchedVacancy) {
        Glide.with(itemView)
            .load(model.img)
            .placeholder(R.drawable.icon_vacancy_placeholder)
            .centerCrop()
            .into(binding.ivVacancyImage)
        binding.tvVacancyAndArea.text = model.vacancy //TODO
        binding.tvSalary.text = model.salaryFrom.toString() //TODO
        binding.tvCategory.text = model.category
    }
}
