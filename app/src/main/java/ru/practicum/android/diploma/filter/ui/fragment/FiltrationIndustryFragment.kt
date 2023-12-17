package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFiltrationIndustryBinding
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.adapter.IndustryAdapter
import ru.practicum.android.diploma.filter.ui.models.FilterIndustryScreenState
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationIndustryViewModel

class FiltrationIndustryFragment : Fragment() {

    private var _binding: FragmentFiltrationIndustryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltrationIndustryViewModel by viewModel()

    private val adapter = IndustryAdapter(listOf()).apply {
        clickListener = IndustryAdapter.IndustryClickListener { industry ->
            viewModel.onIndustryChecked(industry)
        }
    }

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

        binding.rvIndustry.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIndustry.adapter = adapter

    }

    private fun render(state: FilterIndustryScreenState) {
        binding.incorrectErrorPlaceholder.isVisible = state is FilterIndustryScreenState.Incorrect
        binding.getListErrorPlaceholder.isVisible = state is FilterIndustryScreenState.Error
        binding.rvIndustry.isVisible = state is FilterIndustryScreenState.Content


        if (state is FilterIndustryScreenState.Content) {
            adapter.addItems(state.industryList, state.checkedIndustry)
            binding.applyButtonIndustry.isVisible = state.checkedIndustry != null
        } else binding.applyButtonIndustry.isVisible = false
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
    }

    companion object {
        const val INDUSTRY_RESULT_KEY = "industry_key"
        const val INDUSTRY_RESULT_VAL = "industry_val"
    }
}
