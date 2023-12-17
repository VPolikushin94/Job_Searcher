package ru.practicum.android.diploma.core.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.databinding.VacancyItemBinding

class VacancyViewHolder(private val binding: VacancyItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Vacancy) {
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
        binding.tvSalary.text = model.salary
        binding.tvEmployerName.text = model.employerName
    }
}
