package ru.practicum.android.diploma.search.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.ui.adapter.VacancyListAdapter
import ru.practicum.android.diploma.search.ui.viewmodel.SearchViewModel

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

        addTextWatcher()
        setListTouchListeners()
        setEditorActionListener()
        setClickListeners()

        setRecyclerViewAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvVacancy.adapter = null
        vacancyListAdapter = null
        _binding = null
    }

    private fun setRecyclerViewAdapter() {
        vacancyListAdapter = VacancyListAdapter()
        binding.rvVacancy.adapter = vacancyListAdapter
    }

    private fun addTextWatcher() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setClearButtonIcon(s)
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        binding.etSearch.addTextChangedListener(textWatcher)
    }

    private fun setClickListeners() {
        binding.btnClear.setOnClickListener {
            if (binding.etSearch.text.isNullOrEmpty()) {
                binding.etSearch.requestFocus()
                changeKeyboardVisibility(true)
            } else {
                binding.etSearch.setText("")
                binding.etSearch.requestFocus()
            }
        }
        binding.btnFilter.setOnClickListener {

        }
        vacancyListAdapter?.onVacancyClickListener = {

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
                viewModel.searchVacancy()
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
