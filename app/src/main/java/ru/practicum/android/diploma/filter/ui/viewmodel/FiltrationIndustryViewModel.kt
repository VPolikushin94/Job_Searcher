package ru.practicum.android.diploma.filter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.IndustryResult
import ru.practicum.android.diploma.filter.ui.models.FilterIndustryScreenState
import ru.practicum.android.diploma.filter.ui.utils.SingleLiveEvent

class FiltrationIndustryViewModel(industry: Industry?, private val filterInteractor: FilterInteractor) : ViewModel() {

    private var checkedIndustry: Industry?

    private val industryList: MutableList<Industry> = arrayListOf()
    private val screenState = MutableLiveData<FilterIndustryScreenState>()
    fun observeState(): LiveData<FilterIndustryScreenState> = screenState

    private val filterTrigger = SingleLiveEvent<Industry?>()
    fun getFilterTrigger(): LiveData<Industry?> = filterTrigger

    init {
        checkedIndustry = industry
        getIndustries()
    }

    private fun getIndustries() {
        viewModelScope.launch {
            filterInteractor.getIndustries().collect { result ->
                if (result.isError) {
                    setState(FilterIndustryScreenState.Error)
                } else {
                    if (result.data != null) {
                        industryList.addAll(result.data)
                        setState(FilterIndustryScreenState.Content(industryList, checkedIndustry))
                    } else {
                        setState(FilterIndustryScreenState.Incorrect)
                    }
                }
            }
        }
    }


    private fun setState(state: FilterIndustryScreenState) {
        screenState.value = state
    }

    fun onIndustryChecked(industry: Industry) {
        if (industry != checkedIndustry) {
            checkedIndustry = industry
            setState(
                FilterIndustryScreenState.Content(
                    (screenState.value as FilterIndustryScreenState.Content).industryList,
                    checkedIndustry
                )
            )
        }
    }
}
