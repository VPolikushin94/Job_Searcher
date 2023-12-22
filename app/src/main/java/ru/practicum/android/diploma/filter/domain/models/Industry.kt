package ru.practicum.android.diploma.filter.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Industry(
    val id: String,
    val name: String,
    val isChecked: Boolean = false
) : Parcelable
