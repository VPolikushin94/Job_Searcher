package ru.practicum.android.diploma.util

data class DataResult<T>(val data: List<T>? = null, val isError: Boolean = false)
