package ru.practicum.android.diploma.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy

class VacancyInteractorImpl(private val vacancyRepository: VacancyRepository): VacancyInteractor {

    override suspend fun getSelectedVacancy(id: String?): Flow<Pair<DetailsVacancy?, String?>> {
        return vacancyRepository.getSelectedVacancy(id).map { result ->
            when(result){
                is Resource.Success -> Pair(result.data, "")
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }
}
