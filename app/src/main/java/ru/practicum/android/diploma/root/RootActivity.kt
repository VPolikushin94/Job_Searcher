package ru.practicum.android.diploma.root

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

        val isLight = bgColor == getColor(R.color.white_universal)

        window.navigationBarColor = bgColor

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.navigationBarDividerColor = bgColor
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setNavBarContentColorNewApi(window, !isLight)
        } else {
            setNavBarContentColorOldApi(window, !isLight)
        }
    }

    private fun setNavBarContentColorOldApi(window: Window, isLight: Boolean) {
        val decorView = window.decorView
        decorView.systemUiVisibility =
            if (isLight) {
                decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            } else {
                decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun setNavBarContentColorNewApi(window: Window, isLight: Boolean) {
        window.insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS * isLight.toString().toInt(),
            WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
        )
    }

    private fun setBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.filterSettingsFragment -> {
                    binding.bottomNav.isVisible = false
                    binding.separator.isVisible = false
                }

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
