package ru.practicum.android.diploma.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.core.ui.diffutil.VacancyDiffUtilCallback
import ru.practicum.android.diploma.core.ui.viewholder.VacancyViewHolder
import ru.practicum.android.diploma.databinding.VacancyItemBinding

class VacancyListAdapter(
    private var onVacancyClickListener: ((Vacancy) -> Unit),
) : ListAdapter<Vacancy, VacancyViewHolder>(VacancyDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val view = VacancyItemBinding.inflate(layoutInspector, parent, false)
        return VacancyViewHolder(view)
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onVacancyClickListener.invoke(getItem(position))
        }
    }
}
