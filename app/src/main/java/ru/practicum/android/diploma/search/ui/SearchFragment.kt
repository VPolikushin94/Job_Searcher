package ru.practicum.android.diploma.search.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.core.ui.adapter.VacancyListAdapter
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.ui.models.SearchPlaceholderType
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.search.ui.viewmodel.SearchViewModel
import ru.practicum.android.diploma.util.changeKeyboardVisibility
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()
    private var vacancyListAdapter: VacancyListAdapter? = null
    private var onVacancyClickListener: ((Vacancy) -> Unit)? = null
    private var toast: Toast? = null

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
        setRvScrollListener()
        viewModel.screenState.observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.btnFilterState.observe(viewLifecycleOwner) {
            setFilterState(it)
        }
        viewModel.triggerClearAdapter.observe(viewLifecycleOwner) {
            if (it) {
                vacancyListAdapter?.submitList(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvVacancy.adapter = null
        vacancyListAdapter = null
        _binding = null
    }

    private fun setRvScrollListener() {
        val linearLayoutManager = binding.rvVacancy.layoutManager as LinearLayoutManager
        binding.rvVacancy.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val lastItemPos = linearLayoutManager.findLastVisibleItemPosition()
                    vacancyListAdapter?.itemCount?.let {
                        if (lastItemPos >= it - 1) {
                            viewModel.searchVacancy(
                                binding.etSearch.text.toString(),
                                true,
                                false
                            )
                        }
                    }
                }
            }
        })
    }

    private fun render(state: SearchScreenState) {
        when (state) {
            is SearchScreenState.Loading -> {
                showPlaceholder(SearchPlaceholderType.PLACEHOLDER_EMPTY, false)
                if (state.isPaging) {
                    binding.progressBarPaging.isVisible = true
                } else {
                    binding.tvFound.isVisible = false
                    setMiddleProgressBarVisibility(true)
                }
            }

            is SearchScreenState.Content -> {
                setMiddleProgressBarVisibility(false)
                binding.progressBarPaging.isVisible = false
                showPlaceholder(SearchPlaceholderType.PLACEHOLDER_EMPTY, false)
                binding.tvFound.text = getString(R.string.vacancy_found, state.found)
                binding.tvFound.isVisible = true
                vacancyListAdapter?.submitList(state.vacancyList)
            }

            is SearchScreenState.Placeholder -> {
                setMiddleProgressBarVisibility(false)
                binding.progressBarPaging.isVisible = false
                if (!state.isPaging) {
                    binding.tvFound.isVisible = false
                }
                showPlaceholder(state.placeholderType, state.isPaging)
            }
        }
    }

    private fun showToast(@StringRes res: Int) {
        toast?.cancel()
        toast = Toast.makeText(requireContext(), res, Toast.LENGTH_SHORT)
        toast?.show()
    }

    private fun showPlaceholder(type: SearchPlaceholderType, isPaging: Boolean) {
        when (type) {
            SearchPlaceholderType.PLACEHOLDER_NOT_SEARCHED_YET -> {
                setPlaceholderVisibility(false, true, false, false, false)
            }

            SearchPlaceholderType.PLACEHOLDER_NO_INTERNET -> {
                showToast(R.string.check_internet)
                if (!isPaging) {
                    setPlaceholderVisibility(false, false, true, false, false)
                }
            }

            SearchPlaceholderType.PLACEHOLDER_GOT_EMPTY_LIST -> {
                setPlaceholderVisibility(false, false, false, true, false)
            }

            SearchPlaceholderType.PLACEHOLDER_SERVER_ERROR -> {
                showToast(R.string.error_occured)
                if (!isPaging) {
                    setPlaceholderVisibility(false, false, false, false, true)
                }
            }

            SearchPlaceholderType.PLACEHOLDER_EMPTY -> {
                setPlaceholderVisibility(true, false, false, false, false)
            }
        }
    }

    private fun setPlaceholderVisibility(
        isRvVacancyVisible: Boolean,
        isNotSearchedYetVisible: Boolean,
        isNoInternetVisible: Boolean,
        isGotEmptyListVisible: Boolean,
        isServerErrorVisible: Boolean,
    ) {
        binding.rvVacancy.isVisible = isRvVacancyVisible
        binding.placeholderNotSearchedYet.isVisible = isNotSearchedYetVisible
        binding.placeholderNoInternet.isVisible = isNoInternetVisible
        binding.placeholderGotEmptyList.isVisible = isGotEmptyListVisible
        binding.placeholderServerError.isVisible = isServerErrorVisible
    }

    private fun setMiddleProgressBarVisibility(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
        binding.flContent.isVisible = !isVisible
    }

    private fun setRecyclerViewAdapter() {
        vacancyListAdapter = VacancyListAdapter(
            onVacancyClickListener ?: throw NullPointerException("onVacancyClickListener equals null")
        )
        binding.rvVacancy.itemAnimator = null
        binding.rvVacancy.adapter = vacancyListAdapter
        if (viewModel.getSearchedText().isNotEmpty()) {
            viewModel.getCachedVacancySearchResult()
        }
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
                changeKeyboardVisibility(true, requireContext(), binding.etSearch)
            } else {
                changeKeyboardVisibility(false, requireContext(), binding.etSearch)
                binding.etSearch.setText("")
                binding.etSearch.requestFocus()
            }
        }

        binding.btnFilter.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filtrationFragment)
        }

        onVacancyClickListener = {
            if (viewModel.clickDebounce()) {
                val bundle = bundleOf(VacancyViewModel.BUNDLE_KEY to it.id.toString())
                findNavController().navigate(R.id.action_searchFragment_to_vacancyFragment, bundle)
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
        binding.flContent.setOnTouchListener { _, _ ->
            changeKeyboardVisibility(false, requireContext(), binding.etSearch)
            binding.etSearch.clearFocus()
            false
        }
        binding.rvVacancy.setOnTouchListener { _, _ ->
            changeKeyboardVisibility(false, requireContext(), binding.etSearch)
            binding.etSearch.clearFocus()
            false
        }
    }

    private fun setEditorActionListener() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.blockSearch(false)
                viewModel.searchVacancy(binding.etSearch.text.toString(), false, false)
                binding.etSearch.clearFocus()
            }
            false
        }
    }
}
