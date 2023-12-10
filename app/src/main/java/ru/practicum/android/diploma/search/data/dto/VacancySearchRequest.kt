package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.core.dto.Request

data class VacancySearchRequest(val vacancySearchParams: Map<String, String>) : Request
