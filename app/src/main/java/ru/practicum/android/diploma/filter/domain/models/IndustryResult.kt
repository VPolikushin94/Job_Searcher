package ru.practicum.android.diploma.filter.domain.models

data class IndustryResult<T>(val data: List<T>? = null, val isError: Boolean = false)
