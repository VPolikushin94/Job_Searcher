package ru.practicum.android.diploma.filter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.models.FilterIndustryScreenState
import ru.practicum.android.diploma.filter.ui.utils.SingleLiveEvent

class FiltrationIndustryViewModel(private val filterInteractor: FilterInteractor) : ViewModel() {

    private val industryList: MutableList<Industry> = arrayListOf()
    private val screenState = MutableLiveData<FilterIndustryScreenState>()
    private val filteredIndustryList = mutableListOf<Industry>()
    fun observeState(): LiveData<FilterIndustryScreenState> = screenState

    private val filterTrigger = SingleLiveEvent<Industry?>()
    fun getFilterTrigger(): LiveData<Industry?> = filterTrigger

    init {
        getIndustries()
    }

    private fun getIndustries() {
        screenState.value = FilterIndustryScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.getIndustries().collect { result ->
                if (result.isError) {
                    setState(FilterIndustryScreenState.Error)
                } else {
                    if (result.data != null) {
                        industryList.addAll(result.data)
                        setState(FilterIndustryScreenState.Content(industryList))
                    } else {
                        setState(FilterIndustryScreenState.Incorrect)
                    }
                }
            }
        }
    }

    fun searchIndustry(text: String) {
        if (text.isEmpty()) {
            setState(FilterIndustryScreenState.Content(industryList))
            return
        }
        filteredIndustryList.clear()

        industryList.forEach {
            if (it.name.lowercase().contains(text.lowercase())) {
                filteredIndustryList.add(it)
            }
        }

        if (filteredIndustryList.isEmpty()) {
            setState(FilterIndustryScreenState.Error)
        } else {
            setState(FilterIndustryScreenState.Content(filteredIndustryList))
        }
    }

    private fun setState(state: FilterIndustryScreenState) {
        screenState.postValue(state)
    }
}
