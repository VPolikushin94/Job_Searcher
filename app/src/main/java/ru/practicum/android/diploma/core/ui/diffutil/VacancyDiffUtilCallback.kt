package ru.practicum.android.diploma.core.ui.diffutil

import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.search.domain.models.SearchedVacancy

class VacancyDiffUtilCallback : DiffUtil.ItemCallback<SearchedVacancy>() {
    override fun areItemsTheSame(oldItem: SearchedVacancy, newItem: SearchedVacancy): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchedVacancy, newItem: SearchedVacancy): Boolean {
        return oldItem == newItem
    }
}
