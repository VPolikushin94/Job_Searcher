package ru.practicum.android.diploma.vacancy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.models.Vacancy
import ru.practicum.android.diploma.core.ui.adapter.VacancyListAdapter
import ru.practicum.android.diploma.databinding.FragmentSimilarVacancyBinding
import ru.practicum.android.diploma.vacancy.ui.viewmodel.SimilarState
import ru.practicum.android.diploma.vacancy.ui.viewmodel.SimilarVacancyViewModel
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

class SimilarVacancyFragment : Fragment() {

    private var _binding: FragmentSimilarVacancyBinding? = null
    private val binding get() = _binding!!
    private var onVacancyClickListener: ((Vacancy) -> Unit)? = null
    private var vacancyListAdapter: VacancyListAdapter? = null

    private val viewModel: SimilarVacancyViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSimilarVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onVacancyClickListener = {
            if (viewModel.clickDebounce()) {
                val bundle = bundleOf(VacancyViewModel.BUNDLE_KEY to it.id.toString())
                findNavController().navigate(R.id.action_similarVacancyFragment_to_vacancyFragment, bundle)
            }
        }

        setRecyclerViewAdapter()
        setClickListeners()

        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            render(state)
        }

    }

    private fun setClickListeners() {
        binding.filtrationChoiceCountryArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setRecyclerViewAdapter() {
        vacancyListAdapter = VacancyListAdapter(
            onVacancyClickListener ?: throw NullPointerException("onVacancyClickListener equals null")
        )
        binding.rvSimilar.itemAnimator = null
        binding.rvSimilar.adapter = vacancyListAdapter
    }

    private fun render(state: SimilarState) {
        when (state) {
            is SimilarState.Loading -> setLoading()
            is SimilarState.Error -> setError(state.message)
            is SimilarState.Success -> setDataSimilar(state.similarList)
        }
    }

    private fun setLoading() {
        binding.getListErrorPlaceholder.isVisible = false
        binding.rvSimilar.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun setError(message: String) {
        binding.getListErrorPlaceholder.isVisible = true
        binding.rvSimilar.isVisible = false
        binding.progressBar.isVisible = false
        if (message == SimilarVacancyViewModel.NO_INTERNET) {
            binding.getListErrorPlaceholderImage.setBackgroundResource(R.drawable.placeholder_no_internet_skull)
            binding.getListErrorPlaceholderText.setText(R.string.no_internet)
        } else {
            binding.getListErrorPlaceholderImage.setBackgroundResource(R.drawable.placeholder_server_error_towels)
            binding.getListErrorPlaceholderText.setText(R.string.server_error)
        }

    }

    private fun setDataSimilar(similarList: List<Vacancy>) {
        binding.getListErrorPlaceholder.isVisible = false
        binding.rvSimilar.isVisible = true
        binding.progressBar.isVisible = false
        vacancyListAdapter?.submitList(null)
        vacancyListAdapter?.submitList(similarList)
    }

}
