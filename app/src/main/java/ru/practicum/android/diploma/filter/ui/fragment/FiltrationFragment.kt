package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationViewModel

class FiltrationFragment : Fragment() {

    private var inputSalary: String = ""

    private var _binding: FragmentFiltrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FiltrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFiltrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputSalary = s?.toString() ?: ""
                viewModel.updateSalary(inputSalary)
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }

        binding.salaryFiltrationEditText.addTextChangedListener(simpleTextWatcher)

        viewModel.observeFiltrationSettings().observe(viewLifecycleOwner) {
            showSettings(it)
        }

        binding.filtrationCheckBox.setOnClickListener {
            viewModel.saveSalaryOnlyItem(binding.filtrationCheckBox.isChecked)
        }

        viewModel.getFiltrationSettings()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.filtrationArrowBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.workLocationEditText.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_filtrationLocationFragment)
        }

        binding.workIndustryEditText.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_filtrationIndustryFragment)
        }

        binding.applyButton.setOnClickListener {
            viewModel.setSalary(inputSalary)
            findNavController().popBackStack()
        }

    }

    private fun showSettings(settings: FiltrationSettings) {
        binding.workIndustryEditText.setText(settings.industry?.name ?: "")
        binding.salaryFiltrationEditText.setText(settings.salary)
        binding.filtrationCheckBox.isChecked = settings.salaryOnly
    }
}
