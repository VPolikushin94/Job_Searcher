package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFiltrationIndustryBinding
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationIndustryViewModel

class FiltrationIndustryFragment : Fragment() {

    private var _binding: FragmentFiltrationIndustryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltrationIndustryViewModel by viewModel()

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

    }

    private fun setClickListeners() {
        binding.filtrationIndustryArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.applyButtonIndustry.setOnClickListener {
            findNavController().navigateUp()
        }

    }

}
