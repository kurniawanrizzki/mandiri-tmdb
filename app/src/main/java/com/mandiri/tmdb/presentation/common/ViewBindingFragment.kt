package com.mandiri.tmdb.presentation.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.mandiri.tmdb.R

abstract class ViewBindingFragment<VB: ViewBinding> : Fragment() {
    protected abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    abstract fun setup()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(
            inflater,
            container,
            false
        )
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindNavigationAction()
        setup()
    }

    private fun bindNavigationAction() {
        view?.findViewById<MaterialToolbar>(R.id.toolbar)?.run {
            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    fun showMessage(message: String) =
        Snackbar.make(
            requireView(),
            message,
            Snackbar.LENGTH_LONG
        ).show()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}