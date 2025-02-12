package ru.practicum.android.diploma.core.ui.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.core.models.Vacancy

class VacancyDiffUtilCallback : DiffUtil.ItemCallback<Vacancy>() {
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
        return oldItem == newItem
    }
}
