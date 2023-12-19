package ru.practicum.android.diploma.filter.ui.models

import ru.practicum.android.diploma.filter.domain.models.Industry

sealed interface FilterIndustryScreenState {

    data object Loading : FilterIndustryScreenState

    data object Incorrect : FilterIndustryScreenState

    data object Error : FilterIndustryScreenState

    data class Content(val industryList: List<Industry>) :
        FilterIndustryScreenState
}
