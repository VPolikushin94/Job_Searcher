package ru.practicum.android.diploma.search.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.core.ui.adapter.VacancyListAdapter
import ru.practicum.android.diploma.search.ui.models.SearchPlaceholderType
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.search.ui.viewmodel.SearchViewModel
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()

    private var vacancyListAdapter: VacancyListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setEditText()
        setListTouchListeners()
        setEditorActionListener()
        setClickListeners()

        setRecyclerViewAdapter()

        viewModel.screenState.observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.btnFilterState.observe(viewLifecycleOwner) {
            setFilterState(it)
        }
    }

    override fun onStart() {
        super.onStart()
        if (binding.etSearch.text.isNotEmpty()) {
            viewModel.getCachedVacancySearchResult()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvVacancy.adapter = null
        vacancyListAdapter = null
        _binding = null
    }

    private fun render(state: SearchScreenState) {
        when (state) {
            is SearchScreenState.Loading -> {
                showPlaceholder(SearchPlaceholderType.PLACEHOLDER_EMPTY)
                binding.tvFound.isVisible = false
                setProgressBarVisibility(true)
            }

            is SearchScreenState.Content -> {
                setProgressBarVisibility(false)
                showPlaceholder(SearchPlaceholderType.PLACEHOLDER_EMPTY)
                binding.tvFound.text = getString(R.string.vacancy_found, state.found)
                binding.tvFound.isVisible = true
                vacancyListAdapter?.submitList(state.vacancyList)
            }

            is SearchScreenState.Placeholder -> {
                setProgressBarVisibility(false)
                binding.tvFound.isVisible = false
                showPlaceholder(state.placeholderType)
            }
        }
    }

    private fun showPlaceholder(type: SearchPlaceholderType) {
        when (type) {
            SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET -> {
                vacancyListAdapter?.submitList(null)
                binding.placeholderServerError.isVisible = false
                binding.placeholderGotEmptyList.isVisible = false
                binding.placeholderNoInternet.isVisible = false
                binding.placeholderNotSearchedYet.isVisible = true
            }

            SearchPlaceholderType.PLACEHOLDER_NO_INTERNET -> {
                vacancyListAdapter?.submitList(null)
                binding.placeholderNotSearchedYet.isVisible = false
                binding.placeholderServerError.isVisible = false
                binding.placeholderGotEmptyList.isVisible = false
                binding.placeholderNoInternet.isVisible = true
            }

            SearchPlaceholderType.PLACEHOLDER_GOT_EMPTY_LIST -> {
                vacancyListAdapter?.submitList(null)
                binding.placeholderNotSearchedYet.isVisible = false
                binding.placeholderNoInternet.isVisible = false
                binding.placeholderServerError.isVisible = false
                binding.placeholderGotEmptyList.isVisible = true
            }

            SearchPlaceholderType.PLACEHOLDER_SERVER_ERROR -> {
                vacancyListAdapter?.submitList(null)
                binding.placeholderNotSearchedYet.isVisible = false
                binding.placeholderNoInternet.isVisible = false
                binding.placeholderGotEmptyList.isVisible = false
                binding.placeholderServerError.isVisible = true
            }

            SearchPlaceholderType.PLACEHOLDER_EMPTY -> {
                binding.placeholderNotSearchedYet.isVisible = false
                binding.placeholderNoInternet.isVisible = false
                binding.placeholderGotEmptyList.isVisible = false
                binding.placeholderServerError.isVisible = false
            }
        }
    }

    private fun setProgressBarVisibility(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
        binding.flContent.isVisible = !isVisible
    }

    private fun setRecyclerViewAdapter() {
        vacancyListAdapter = VacancyListAdapter()
        binding.rvVacancy.itemAnimator = null
        binding.rvVacancy.adapter = vacancyListAdapter
    }

    private fun setEditText() {
        binding.etSearch.doOnTextChanged { text, _, _, _ ->
            setClearButtonIcon(text)

            if (text.isNullOrEmpty()) {
                viewModel.blockSearch(true)
            } else {
                viewModel.blockSearch(false)
                viewModel.searchVacancyDebounce(text.toString())
            }
        }
        binding.etSearch.requestFocus()
    }

    private fun setClickListeners() {
        binding.btnClear.setOnClickListener {
            if (binding.etSearch.text.isNullOrEmpty()) {
                binding.etSearch.requestFocus()
                changeKeyboardVisibility(true)
            } else {
                changeKeyboardVisibility(false)
                binding.etSearch.setText("")
                binding.etSearch.requestFocus()
            }
        }
//        binding.btnFilter.setOnClickListener {
//
//        }
        vacancyListAdapter?.onVacancyClickListener = {
            if (viewModel.clickDebounce()) {
                val bundle = bundleOf(VacancyViewModel.BUNDLE_KEY to it.id)
                view?.findNavController()?.navigate(R.id.action_searchFragment_to_vacancyFragment, bundle)
            }
        }
    }

    private fun setFilterState(isActivated: Boolean) {
        if (isActivated) {
            binding.btnFilter.setImageResource(R.drawable.icon_filter_on)
        } else {
            binding.btnFilter.setImageResource(R.drawable.icon_filter_off)
        }
    }

    private fun setClearButtonIcon(s: CharSequence?) {
        if (s.isNullOrEmpty()) {
            binding.btnClear.setImageResource(R.drawable.icon_search)
        } else {
            binding.btnClear.setImageResource(R.drawable.icon_cross)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListTouchListeners() {
        binding.rvVacancy.setOnTouchListener { _, _ ->
            changeKeyboardVisibility(false)
            binding.etSearch.clearFocus()
            false
        }
    }

    private fun setEditorActionListener() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.blockSearch(false)
                viewModel.searchVacancy(binding.etSearch.text.toString())
                binding.etSearch.clearFocus()
            }
            false
        }
    }

    private fun changeKeyboardVisibility(isVisible: Boolean) {
        val keyboard =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (isVisible) {
            keyboard.showSoftInput(binding.etSearch, 0)
        } else {
            keyboard.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        }
    }
}
