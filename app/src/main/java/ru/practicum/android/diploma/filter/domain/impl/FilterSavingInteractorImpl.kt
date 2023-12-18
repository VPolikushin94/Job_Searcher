package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterSavingInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterSavingRepository
import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings

class FilterSavingInteractorImpl(private val filterLocalRepository: FilterSavingRepository) :
    FilterSavingInteractor {
    override fun getFiltrationSettings(): FiltrationSettings? {
        return filterLocalRepository.getFiltrationSettings()
    }

    override fun saveFiltrationSettings(filtrationSettings: FiltrationSettings?) {
        if (filtrationSettings == null || filtrationSettings.isEmpty()) {
            filterLocalRepository.removeFiltrationSettings()
        } else {
            filterLocalRepository.saveFiltrationSettings(filtrationSettings)
        }
    }
}
