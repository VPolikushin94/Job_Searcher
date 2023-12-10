package ru.practicum.android.diploma.search.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.search.domain.models.SearchedVacancy
import ru.practicum.android.diploma.util.formatNumber

class VacancyViewHolder(private val binding: VacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: SearchedVacancy) {
        Glide.with(itemView)
            .load(model.img)
            .placeholder(R.drawable.icon_vacancy_placeholder)
            .centerCrop()
            .transform(
                RoundedCorners(
                    itemView.resources.getDimensionPixelSize(R.dimen.radius_12)
                )
            )
            .into(binding.ivVacancyImage)
        binding.tvVacancyAndArea.text = itemView.context.getString(R.string.vacancy_area, model.vacancy, model.area)
        val salary = buildString {
            if (model.salaryFrom == null && model.salaryTo == null) {
                this.append(itemView.context.getString(R.string.no_salary))
            } else {
                model.salaryFrom?.let {
                    val salaryFrom = itemView.context.getString(R.string.salary_from, formatNumber(model.salaryFrom))
                    this.append("$salaryFrom ")
                }
                model.salaryTo?.let {
                    val salaryTo = itemView.context.getString(R.string.salary_to, formatNumber(model.salaryTo))
                    this.append("$salaryTo ")
                }
                val currency = when (model.currency) {
                    itemView.context.getString(R.string.rur) -> itemView.context.getString(R.string.ruble)
                    itemView.context.getString(R.string.eur) -> itemView.context.getString(R.string.euro)
                    itemView.context.getString(R.string.usd) -> itemView.context.getString(R.string.dollar)
                    else -> model.currency
                }
                this.append(currency)
            }
        }
        binding.tvSalary.text = salary
        binding.tvEmployerName.text = model.employerName
    }
}
