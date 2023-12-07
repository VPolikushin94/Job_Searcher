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
        binding.tvVacancyAndArea.text = itemView.context.getString(R.string.vacancy_area, model.vacancy, model.area)
        val salary = buildString {
            if (model.salaryFrom != null) {
                val salaryFrom = itemView.context.getString(R.string.salary_from, model.salaryFrom)
                this.append(salaryFrom)
            }
            if (model.salaryTo != null) {
                val salaryTo = itemView.context.getString(R.string.salary_to, model.salaryTo)
                this.append(salaryTo)
            }
            this.append(model.currency)
        }
        binding.tvSalary.text = salary
        binding.tvCategory.text = model.category
    }
}
