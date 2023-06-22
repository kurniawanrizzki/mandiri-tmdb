package com.mandiri.tmdb.presentation.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mandiri.tmdb.databinding.FragmentSplashBinding
import com.mandiri.tmdb.presentation.common.ViewBindingFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : ViewBindingFragment<FragmentSplashBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSplashBinding
        get() = FragmentSplashBinding::inflate

    override fun setup() {
        viewLifecycleOwner.lifecycleScope.launch {
            navigateToMovie()
        }
    }

    private suspend fun navigateToMovie() {
        delay(SPLASH_INTERVAL)

        val action = SplashFragmentDirections.navigateToMovie()
        findNavController().navigate(action)
    }
}

private const val SPLASH_INTERVAL = 3000L