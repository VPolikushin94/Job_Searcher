package ru.practicum.android.diploma.root

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Пример использования access token для HeadHunter API
        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

        setBottomNavigation()

        setSystemNavigationBarColor()
    }

    private fun setSystemNavigationBarColor() {
        val bgColor = TypedValue().let {
            theme.resolveAttribute(android.R.attr.colorPrimary, it, true)
            getColor(it.resourceId)
        }

        val isLight = !isDarkThemeDefault()

        window.navigationBarColor = bgColor

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.navigationBarDividerColor = bgColor
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setNavBarContentColorNewApi(window, isLight)
            setStatusBarContentColorNewApi(window, isLight)
        } else {
            setNavBarContentColorOldApi(window, isLight)
        }
    }

    private fun isDarkThemeDefault(): Boolean {
        return resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    private fun setNavBarContentColorOldApi(window: Window, isLight: Boolean) {
        val decorView = window.decorView
        decorView.systemUiVisibility =
            if (isLight) {
                decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun setNavBarContentColorNewApi(window: Window, isLight: Boolean) {
        val navBarFlag = if (isLight) {
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
        } else {
            0
        }
        window.insetsController?.setSystemBarsAppearance(
            navBarFlag,
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun setStatusBarContentColorNewApi(window: Window, isLight: Boolean) {
        val statusBarFlag = if (isLight) {
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        } else {
            0
        }
        window.insetsController?.setSystemBarsAppearance(
            statusBarFlag,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }

    private fun setBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.filtrationFragment,
                R.id.filtrationLocationFragment,
                R.id.filtrationLocationCountryFragment,
                R.id.filtrationIndustryFragment -> {
                    binding.bottomNav.isVisible = false
                    binding.separator.isVisible = false
                }

                R.id.vacancyFragment -> binding.bottomNav.isVisible = false

                else -> {
                    binding.bottomNav.isVisible = true
                    binding.separator.isVisible = true
                }
            }
        }
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }

}
