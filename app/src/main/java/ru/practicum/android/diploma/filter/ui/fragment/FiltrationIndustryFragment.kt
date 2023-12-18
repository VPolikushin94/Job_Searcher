package ru.practicum.android.diploma.filter.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationIndustryBinding
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.adapter.IndustryAdapter
import ru.practicum.android.diploma.filter.ui.models.FilterIndustryScreenState
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationIndustryViewModel
import ru.practicum.android.diploma.util.changeKeyboardVisibility

class FiltrationIndustryFragment : Fragment() {

    private var _binding: FragmentFiltrationIndustryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltrationIndustryViewModel by viewModel()

    private var adapter: IndustryAdapter? = null

    private var onIndustryClickListener: IndustryAdapter.IndustryClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltrationIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()

        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        viewModel.getFilterTrigger().observe(viewLifecycleOwner) { industry ->
            applyFilter(industry)
        }

        setRecyclerView()
        setEditText()
        setListTouchListeners()
    }

    private fun setEditText() {
        binding.etFiltrationIndustry.doOnTextChanged { text, _, _, _ ->
            setClearButtonIcon(text)
            viewModel.searchIndustry(text.toString())
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListTouchListeners() {
        binding.getListErrorPlaceholder.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        binding.incorrectErrorPlaceholder.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        binding.rvIndustry.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    private fun hideKeyboard() {
        changeKeyboardVisibility(false, requireContext(), binding.etFiltrationIndustry)
        binding.etFiltrationIndustry.clearFocus()
    }

    private fun setClearButtonIcon(s: CharSequence?) {
        if (s.isNullOrEmpty()) {
            binding.btnClear.setImageResource(R.drawable.icon_search)
        } else {
            binding.btnClear.setImageResource(R.drawable.icon_cross)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvIndustry.adapter = null
        adapter = null
        _binding = null
    }

    private fun setRecyclerView() {
        binding.rvIndustry.layoutManager = LinearLayoutManager(requireContext())
        adapter = IndustryAdapter(
            emptyList(),
            onIndustryClickListener ?: throw NullPointerException("onIndustryClickListener equals null")
        )
        binding.rvIndustry.adapter = adapter
    }

    private fun render(state: FilterIndustryScreenState) {
        binding.incorrectErrorPlaceholder.isVisible = state is FilterIndustryScreenState.Incorrect
        binding.getListErrorPlaceholder.isVisible = state is FilterIndustryScreenState.Error
        binding.progressBar.isVisible = state is FilterIndustryScreenState.Loading
        binding.rvIndustry.isVisible = if (state is FilterIndustryScreenState.Content) {
            adapter?.addItems(state.industryList)
            true
        } else {
            false
        }
    }


    private fun applyFilter(industry: Industry?) {
        setFragmentResult(
            INDUSTRY_RESULT_KEY, bundleOf(INDUSTRY_RESULT_VAL to industry)
        )
        findNavController().navigateUp()
    }

    private fun setClickListeners() {
        binding.filtrationIndustryArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.applyButtonIndustry.setOnClickListener {
            findNavController().navigateUp()
        }

        onIndustryClickListener = IndustryAdapter.IndustryClickListener {
            binding.applyButtonIndustry.isVisible = true
        }
        binding.btnClear.setOnClickListener {
            if (binding.etFiltrationIndustry.text.isNullOrEmpty()) {
                binding.etFiltrationIndustry.requestFocus()
                changeKeyboardVisibility(true, requireContext(), binding.etFiltrationIndustry)
            } else {
                changeKeyboardVisibility(false, requireContext(), binding.etFiltrationIndustry)
                binding.etFiltrationIndustry.setText("")
                binding.etFiltrationIndustry.requestFocus()
            }
        }
    }

    companion object {
        const val INDUSTRY_RESULT_KEY = "industry_key"
        const val INDUSTRY_RESULT_VAL = "industry_val"
    }
}
