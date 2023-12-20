package ru.practicum.android.diploma.filter.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltrationBinding
import ru.practicum.android.diploma.filter.domain.models.FiltrationSettings
import ru.practicum.android.diploma.filter.ui.viewmodel.FiltrationViewModel
import ru.practicum.android.diploma.util.changeKeyboardVisibility

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
                binding.salaryFiltration.isEndIconVisible = true
                buttonVisibility()
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }

        binding.workIndustry.setEndIconOnClickListener {
            if (binding.workIndustryEditText.text.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_filtrationFragment_to_filtrationIndustryFragment)
            } else {
                binding.workIndustryEditText.setText("")
                viewModel.removeIndustries()
            }
        }

        binding.salaryFiltrationEditText.addTextChangedListener(simpleTextWatcher)

        binding.salaryFiltration.setEndIconOnClickListener {
            binding.salaryFiltrationEditText.setText("")
            binding.salaryFiltration.isEndIconVisible = false
        }

        viewModel.observeFiltrationSettings().observe(viewLifecycleOwner) {
            showSettings(it)
        }

        viewModel.observeData().observe(viewLifecycleOwner) {
        }

        binding.filtrationCheckBox.setOnClickListener {
            viewModel.saveSalaryOnlyItem(binding.filtrationCheckBox.isChecked)
            buttonVisibility()
        }

        viewModel.getFiltrationSettings()
        setClickListeners()
        setEditorActionListener()
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
            findNavController().popBackStack()
        }

        binding.cancelButton.setOnClickListener {
            viewModel.deleteAllFilters()
            binding.salaryFiltration.isEndIconVisible = false
        }

    }

    private fun showSettings(settings: FiltrationSettings) {
        binding.workIndustryEditText.setText(settings.industry?.name ?: "")
        binding.salaryFiltrationEditText.setText(settings.salary)
        binding.filtrationCheckBox.isChecked = settings.salaryOnly
        buttonVisibility()
        iconVisibility()
        changeIcon()
    }

    private fun setEditorActionListener() {
        binding.salaryFiltrationEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                binding.salaryFiltrationEditText.clearFocus()
                changeKeyboardVisibility(
                    false,
                    requireContext(),
                    binding.salaryFiltrationEditText
                )
                viewModel.setSalary(inputSalary)
            }
            false
        }
    }

    private fun buttonVisibility() {
        binding.bottomButtonGroup.isVisible = !(binding.workIndustryEditText.text.isNullOrEmpty() &&
            binding.salaryFiltrationEditText.text.isNullOrEmpty() && !binding.filtrationCheckBox.isChecked)
    }

    private fun iconVisibility() {
        binding.salaryFiltration.isEndIconVisible = !binding.salaryFiltrationEditText.text.isNullOrEmpty()
    }

    private fun changeIcon() {
        if (binding.workIndustryEditText.text.isNullOrEmpty()) {
            binding.workIndustry.setEndIconDrawable(R.drawable.icon_arrow_forward)
        } else {
            binding.workIndustry.setEndIconDrawable(R.drawable.icon_cross)
        }
    }
}
