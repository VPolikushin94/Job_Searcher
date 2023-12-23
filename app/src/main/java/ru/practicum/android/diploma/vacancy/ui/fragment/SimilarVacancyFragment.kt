package ru.practicum.android.diploma.vacancy.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentSimilarVacancyBinding
import ru.practicum.android.diploma.vacancy.ui.viewmodel.SimilarVacancyViewModel

class SimilarVacancyFragment : Fragment() {

    private var _binding: FragmentSimilarVacancyBinding? = null
    private val binding get() = _binding!!

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

        setClickListeners()

    }

    private fun setClickListeners() {
        binding.filtrationChoiceCountryArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

    }

}
