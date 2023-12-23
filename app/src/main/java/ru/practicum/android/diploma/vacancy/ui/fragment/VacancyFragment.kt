package ru.practicum.android.diploma.vacancy.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
    private var isFavourites = false

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
            is VacancyState.Loading -> {
                showLoading()
            }

            is VacancyState.Error -> {
                showError()
            }

            is VacancyState.Success -> {
                showVacancy(state.data)
            }
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
        binding.screenDetailVacancy.isVisible = true
        binding.tvVacancyName.text = data.name

        binding.btSimilar.setOnClickListener {
            val bundle = bundleOf(VacancyViewModel.BUNDLE_KEY to data.id.toString())
            findNavController().navigate(
                R.id.action_vacancyFragment_to_similarVacancyFragment, bundle
            )
        }

        showSalary(data)
        showEmployer(data)
        showDescription(data)
        showKeySkills(data)
        showContact(data)

        viewModel.inFavourites(data.id.toString())
        viewModel.inFavouritesMutable.observe(viewLifecycleOwner) {
            isFavourites = it
            if (isFavourites) {
                binding.vacancyFavourite.setImageResource(R.drawable.icon_like_on)
            } else {
                binding.vacancyFavourite.setImageResource(R.drawable.icon_like_off)
            }
        }

        binding.vacancyFavourite.setOnClickListener {
            isFavourites = if (isFavourites) {
                binding.vacancyFavourite.setImageResource(R.drawable.icon_like_off)
                false
            } else {
                binding.vacancyFavourite.setImageResource(R.drawable.icon_like_on)
                true
            }
            viewModel.addFavourites(vacancy = data, isFavourites = isFavourites)
        }

        binding.vacancyShare.setOnClickListener {
            viewModel.clickDebounce()
            if (isClickAllowed) actionShare(data.url)
        }

        binding.tvEMailText.setOnClickListener {
            actionEmail(data.email)
        }

        binding.tvTelephoneText.setOnClickListener {
            actionTelephone(data.telephone)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showSalary(data: DetailsVacancy) {
        val salary = buildString {
            if (data.salaryFrom == null && data.salaryTo == null) {
                this.append(getString(R.string.no_salary))
            } else {
                data.salaryFrom?.let {
                    val salaryFrom = getString(salary_from, formatNumber(data.salaryFrom))
                    this.append("$salaryFrom ")
                }
                data.salaryTo?.let {
                    val salaryTo = getString(R.string.salary_to, formatNumber(data.salaryTo))
                    this.append("$salaryTo ")
                }
                val currency = when (data.salaryCurrency) {
                    getString(R.string.rur) -> getString(R.string.ruble)
                    getString(R.string.eur) -> getString(R.string.euro)
                    getString(R.string.usd) -> getString(R.string.dollar)
                    else -> data.salaryCurrency
                }
                this.append(currency)
            }
        }
        binding.tvVacancySalary.text = salary
    }

    private fun showEmployer(data: DetailsVacancy) {
        if (data.employerLogo != null) {
            Glide
                .with(requireActivity())
                .load(data.employerLogo)
                .placeholder(R.drawable.logo1)
                .transform(RoundedCorners(requireContext().resources.getDimensionPixelSize(R.dimen.margin_12)))
                .into(binding.ivFrameLogo)
        }
        if (data.employerName == null) {
            binding.tvHeadlineCard.isVisible = false
        } else {
            binding.tvHeadlineCard.text = data.employerName
        }
        binding.tvHeadlineCity.text = data.employerCity
        if (data.experienceName == null && data.employmentName == null) {
            binding.tvRequiredExperience.isVisible = false
        } else {
            binding.tvRequiredExperienceYear.text = data.experienceName
            binding.tvRequiredExperienceText.text = data.employmentName
        }
    }

    private fun showDescription(data: DetailsVacancy) {
        if (data.description == null) {
            binding.tvJobDescription.isVisible = false
        } else {
            binding.tvDescriptionText.setText(Html.fromHtml(data.description, Html.FROM_HTML_MODE_COMPACT))
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
        var contactHeader = CONTACT
        if (data.contactPerson == null) {
            binding.tvContactPerson.isVisible = false
            contactHeader -= 1
        } else {
            binding.tvContactPersonText.text = data.contactPerson
        }
        if (data.email == null) {
            binding.tvEMail.isVisible = false
            contactHeader -= 1
        } else {
            binding.tvEMailText.text = data.email
        }
        if (data.telephone == "") {
            binding.tvTelephone.isVisible = false
            contactHeader -= 1
        } else {
            binding.tvTelephoneText.text = data.telephone
        }
        if (data.comment == "") {
            binding.tvComment.isVisible = false
            contactHeader -= 1
        } else {
            binding.tvCommentText.text = data.comment
        }
        if (contactHeader == 0) {
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
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("telephone:$phoneNumber"))
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val CONTACT = 4
    }
}
