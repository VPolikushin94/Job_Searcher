package ru.practicum.android.diploma.filter.domain.models

data class FiltrationSettings(
    val country: Area?,
    val region: Area?,
    val industry: Industry?,
    val salary: Int,
    val fSalaryRequired: Boolean = false
) {

    fun isEmpty(): Boolean {
        return this.country == null &&
            this.region == null &&
            this.industry == null &&
            this.salary == null &&
            !this.fSalaryRequired //что это? оно всегда будет false
    }
}
