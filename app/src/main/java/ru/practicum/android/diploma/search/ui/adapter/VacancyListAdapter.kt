package ru.practicum.android.diploma.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.databinding.VacancyItemBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.diffutil.VacancyDiffUtilCallback
import ru.practicum.android.diploma.search.ui.viewholder.VacancyViewHolder

class VacancyListAdapter : ListAdapter<Vacancy, VacancyViewHolder>(VacancyDiffUtilCallback()) {

    var onVacancyClickListener: ((Vacancy) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val view = VacancyItemBinding.inflate(layoutInspector, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onVacancyClickListener?.invoke(getItem(position))
        }
    }
}
