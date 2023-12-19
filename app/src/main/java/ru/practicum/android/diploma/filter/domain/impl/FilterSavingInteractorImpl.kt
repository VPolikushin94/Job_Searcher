package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FilterSavingInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterSavingRepository
import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings
import ru.practicum.android.diploma.filter.domain.models.Industry

class FilterSavingInteractorImpl(private val filterSavingRepository: FilterSavingRepository) :
    FilterSavingInteractor {
    override fun setIndustries(industry: Industry?) {
        filterSavingRepository.setIndustries(industry)
    }

    override fun getSavedIndustry(): Industry? {
        return filterSavingRepository.getSavedIndustries()
    }

    override fun setSalary(salary: String) {
        filterSavingRepository.setSalary(salary)
    }

    override fun getSalary(): String {
        return filterSavingRepository.getSalary()
    }

    override fun setSalaryOnly(isChecked: Boolean) {
        filterSavingRepository.setSalaryOnly(isChecked)
    }

    override fun getSalaryOnly(): Boolean {
        return filterSavingRepository.getSalaryOnly()
    }

    override fun getFilters(): FiltrationSettings {
        val industry = getSavedIndustry()
        val salary = getSalary()
        val salaryOnly = getSalaryOnly()
        return FiltrationSettings(
            industry, salary, salaryOnly
        )
    }
}
