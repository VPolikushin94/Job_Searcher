package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationLocationBinding
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationLocationViewModel

class FiltrationLocationFragment : Fragment() {

    private var _binding: FragmentFiltrationLocationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltrationLocationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltrationLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()

    }

    private fun setClickListeners() {
        binding.filtrationLocationArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.workLocationCountryEditText.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationLocationFragment_to_filtrationLocationCountryFragment)
        }

    }

}
