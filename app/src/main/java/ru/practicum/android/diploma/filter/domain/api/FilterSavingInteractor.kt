package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings

interface FilterSavingInteractor {
    fun getFiltrationSettings(): FiltrationSettings?

    fun saveFiltrationSettings(filtrationSettings: FiltrationSettings?)
}
