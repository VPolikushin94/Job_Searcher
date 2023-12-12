package ru.practicum.android.diploma.vacancy.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.R.string.salary_from
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.util.formatNumber
import ru.practicum.android.diploma.vacancy.domain.model.DetailsVacancy
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyState
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VacancyViewModel by viewModel()
    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenState.observe(viewLifecycleOwner) { result ->
            render(result)
        }

        viewModel.onClickDebounce.observe(viewLifecycleOwner) { isClick ->
            isClickAllowed = isClick
        }

        binding.ivVacancyBack.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Loading -> showLoading()
            is VacancyState.Error -> showError()
            is VacancyState.Success -> showVacancy(state.data)
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
        binding.screenDetailVacancy.isVisible = false
    }

    private fun showError() {
        binding.progressBar.isVisible = false
        binding.imPlaceholderServerErrorCat.isVisible = true
        binding.tvVacancyErrorServer.isVisible = true
    }

    private fun showVacancy(data: DetailsVacancy) {
        binding.progressBar.isVisible = false
        binding.imPlaceholderServerErrorCat.isVisible = false
        binding.tvVacancyErrorServer.isVisible = false
        binding.tvVacancyName.text = data.name

        showSalary(data)
        showEmployer(data)
        showDescription(data)
        showKeySkills(data)
        showContact(data)

        binding.tvEMailText.setOnClickListener {
            actionEmail(data.email)
        }

        binding.tvTelephoneText.setOnClickListener {
            actionTelephone(data.telephone)
        }

        binding.vacancyShare.setOnClickListener {
            viewModel.clickDebounce()
            if (isClickAllowed) actionShare(data.url)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showSalary(data: DetailsVacancy) {
        val from = resources.getString(salary_from)
        val to = resources.getString(R.string.salary_to)
        val noSalary = resources.getString(R.string.no_salary)
        if (data.salaryFrom == null && data.salaryTo == null) {
            binding.tvVacancySalary.text = noSalary
        } else if (data.salaryFrom != null && data.salaryTo == null) {
            binding.tvVacancySalary.text = "$from ${formatNumber(data.salaryFrom)} ${data.salaryCurrency}"
        } else if (data.salaryFrom == null && data.salaryTo != null) {
            binding.tvVacancySalary.text = "$to ${formatNumber(data.salaryTo)} ${data.salaryCurrency}"
        } else if (data.salaryFrom != null && data.salaryTo != null) {
            binding.tvVacancySalary.text =
                "$from ${formatNumber(data.salaryFrom)} $to ${formatNumber(data.salaryTo)} ${data.salaryCurrency}"
        }
    }

    private fun showEmployer(data: DetailsVacancy) {
        if (data.employerLogo != null) {
            Glide.with(requireActivity())
                .load(data.employerLogo)
                .into(binding.ivFrameLogo)
        }
        if (data.employerName == null) {
            binding.tvHeadlineCard.isVisible = false
        } else {
            binding.tvHeadlineCard.text = data.employerName
        }
        binding.tvHeadlineCity.text = data.employerCity
        if (data.experienceName == null) {
            binding.tvRequiredExperience.isVisible = false
        } else {
            binding.tvRequiredExperienceYear.text = data.experienceName
        }
    }

    private fun showDescription(data: DetailsVacancy) {
        if (data.description == null) {
            binding.tvJobDescription.isVisible = false
        } else {
            binding.tvResponsibilitiesText.text = Html.fromHtml(data.description, HtmlCompat.FROM_HTML_MODE_COMPACT)
        }
    }

    private fun showKeySkills(data: DetailsVacancy) {
        if (data.keySkills == null) {
            binding.tvKeySkills.isVisible = false
        } else {
            binding.tvKeySkillsText.text = data.keySkills
        }
    }

    private fun showContact(data: DetailsVacancy) {
        var showContact = 4

        if (data.contactPerson == null) {
            binding.tvContactPerson.isVisible = false
            showContact -= 1
        } else {
            binding.tvContactPersonText.text = data.contactPerson
        }
        if (data.email == null) {
            binding.tvEMail.isVisible = false
            showContact -= 1
        } else {
            binding.tvEMailText.text = data.email
        }
        if (data.telephone == null) {
            binding.tvTelephone.isVisible = false
            showContact -= 1
        } else {
            binding.tvTelephone.text = data.telephone
        }
        if (data.comment == null) {
            binding.tvComment.isVisible = false
            showContact -= 1
        } else {
            binding.tvCommentText.text = data.comment
        }
        if (showContact == 0) {
            binding.tvContacts.isVisible = false
        }
    }

    private fun actionEmail(email: String?) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$email")
        try {
            startActivity(intent)
        } catch (_: Exception) {
        }
    }

    private fun actionTelephone(phoneNumber: String?) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("telphone:$phoneNumber"))
        try {
            startActivity(intent)
        } catch (_: Exception) {
        }
    }

    private fun actionShare(url: String?) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, url)
        try {
            startActivity(intent)
        } catch (_: Exception) {
        }
    }
}
