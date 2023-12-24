package ru.practicum.android.diploma.filter.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.FilterSavingInteractor
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.models.FilterIndustryScreenState

class FiltrationIndustryViewModel(
    private val filterInteractor: FilterInteractor,
    private val filterSavingInteractor: FilterSavingInteractor
) : ViewModel() {

    private val industryList: MutableList<Industry> = arrayListOf()
    private val screenState = MutableLiveData<FilterIndustryScreenState>()
    private val filteredIndustryList = mutableListOf<Industry>()
    var checkedIndustry: Industry? = null
    fun observeState(): LiveData<FilterIndustryScreenState> = screenState

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
            if (it.name.contains(text, ignoreCase = true)) {
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

    fun onIndustryClicked(industry: Industry) {
        filterSavingInteractor.setIndustries(industry)
    }

}
