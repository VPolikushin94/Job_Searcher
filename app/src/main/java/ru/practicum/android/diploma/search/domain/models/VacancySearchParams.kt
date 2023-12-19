package ru.practicum.android.diploma.search.domain.models

private const val AREA = "area"
private const val SALARY = "salary"
private const val IS_ONLY_WITH_SALARY = "only_with_salary"
private const val INDUSTRY = "industry"
private const val TEXT = "text"
private const val PAGE = "page"
private const val PER_PAGE = "per_page"

data class VacancySearchParams(
    val text: String,
    val areaId: String?,
    val salary: Int?,
    val isOnlyWithSalary: Boolean?,
    val industryId: String?,
    val page: Int,
    val perPage: Int
)

fun VacancySearchParams.toMap(): Map<String, String> {
    return mutableMapOf<String, String>().apply {
        this[TEXT] = text
        if (!areaId.isNullOrEmpty()) {
            this[AREA] = areaId
        }
        if (salary != null) {
            this[SALARY] = salary.toString()
        }
        if (isOnlyWithSalary != null) {
            this[IS_ONLY_WITH_SALARY] = isOnlyWithSalary.toString()
        }
        if (industryId != null) {
            this[INDUSTRY] = industryId
        }
        this[PAGE] = page.toString()
        this[PER_PAGE] = perPage.toString()
    }
}
