package ru.practicum.android.diploma.favorites.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.core.ui.adapter.VacancyListAdapter
import ru.practicum.android.diploma.databinding.FragmentFavoritesBinding
import ru.practicum.android.diploma.favorites.ui.models.FavoritesPlaceholderType
import ru.practicum.android.diploma.favorites.ui.models.FavoritesScreenState
import ru.practicum.android.diploma.favorites.ui.viewmodel.FavoritesViewModel


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    private var vacancyListAdapter: VacancyListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerViewAdapter()

        viewModel.screenState.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavorites.adapter = null
        vacancyListAdapter = null
        _binding = null
    }

    private fun setRecyclerViewAdapter() {
        vacancyListAdapter = VacancyListAdapter()
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
