package ru.practicum.android.diploma.filter.ui.models

import ru.practicum.android.diploma.filter.domain.models.Industry

sealed interface FilterIndustryScreenState {
    object Incorrect : FilterIndustryScreenState

    object Error : FilterIndustryScreenState

    data class Content(val industryList: List<Industry>, val checkedIndustry: Industry?) :
        FilterIndustryScreenState
}
