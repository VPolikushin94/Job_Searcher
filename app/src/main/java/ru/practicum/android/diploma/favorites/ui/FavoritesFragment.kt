package ru.practicum.android.diploma.favorites.ui

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
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.ui.models.FavoritesPlaceholderType
import ru.practicum.android.diploma.favorites.ui.models.FavoritesScreenState
import ru.practicum.android.diploma.favorites.ui.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModel()

    private var vacancyListAdapter: VacancyListAdapter? = null
    private var onVacancyClickListener: ((Vacancy) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onVacancyClickListener = {
            it
        }
        setRecyclerViewAdapter()
        viewModel.getVacancyList()

        viewModel.screenState.observe(viewLifecycleOwner) {
            render(it)
        }

        vacancyListAdapter?.onVacancyClickListener = {
            if (viewModel.clickDebounce()) {
                val bundle = bundleOf(VacancyViewModel.BUNDLE_KEY to it.id.toString())
                findNavController().navigate(R.id.action_favoritesFragment_to_vacancyFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavorites.adapter = null
        vacancyListAdapter = null
        _binding = null
    }

    private fun setRecyclerViewAdapter() {
        vacancyListAdapter = VacancyListAdapter(
            onVacancyClickListener ?: throw NullPointerException("onVacancyClickListener equals null")
        )
        binding.rvFavorites.itemAnimator = null
        binding.rvFavorites.adapter = vacancyListAdapter
    }

    private fun render(state: FavoritesScreenState) {
        when (state) {
            is FavoritesScreenState.Loading -> {
                showPlaceholder(FavoritesPlaceholderType.PLACEHOLDER_EMPTY)
                setProgressBarVisibility(true)
            }

            is FavoritesScreenState.Content -> {
                setProgressBarVisibility(false)
                showPlaceholder(FavoritesPlaceholderType.PLACEHOLDER_EMPTY)
                vacancyListAdapter?.submitList(state.vacancyList)
            }

            is FavoritesScreenState.Placeholder -> {
                setProgressBarVisibility(false)
                showPlaceholder(state.placeholderType)
            }
        }
    }

    private fun showPlaceholder(type: FavoritesPlaceholderType) {
        when (type) {
            FavoritesPlaceholderType.PLACEHOLDER_EMPTY_LIST -> {
                vacancyListAdapter?.submitList(null)
                binding.placeholderGotEmptyListError.isVisible = false
                binding.placeholderEmptyList.isVisible = true
            }

            FavoritesPlaceholderType.PLACEHOLDER_GOT_EMPTY_LIST_ERROR -> {
                vacancyListAdapter?.submitList(null)
                binding.placeholderGotEmptyListError.isVisible = true
                binding.placeholderEmptyList.isVisible = false
            }

            FavoritesPlaceholderType.PLACEHOLDER_EMPTY -> {
                vacancyListAdapter?.submitList(null)
                binding.placeholderGotEmptyListError.isVisible = false
                binding.placeholderEmptyList.isVisible = false
            }
        }
    }

    private fun setProgressBarVisibility(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
        binding.flContent.isVisible = !isVisible
    }
}
