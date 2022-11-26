package com.example.diplomnmedia.fragment.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import com.example.diplomnmedia.R
import com.example.diplomnmedia.databinding.FragmentAuthenticationBinding
import com.example.diplomnmedia.dto.UserResponse
import com.example.diplomnmedia.util.AndroidUtils
import com.example.diplomnmedia.viewmodel.AuthViewModel

@AndroidEntryPoint
class AuthenticationFragment : DialogFragment() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentAuthenticationBinding.inflate(inflater, container, false)

        val enter = binding.enter

        viewModel.errors.observeForever {
            if (it.error) {
                Snackbar.make(binding.root, R.string.not_registered, Snackbar.LENGTH_SHORT).show()
            }
        }

        enter.setOnClickListener {
            val usernameEditText = binding.username.text.toString()
            val passwordEditText = binding.password.text.toString()
            if (usernameEditText == "" || passwordEditText == "") {
                Snackbar.make(binding.root, R.string.All_fields, Snackbar.LENGTH_SHORT).show()
            } else {
                val userResponse = UserResponse(usernameEditText, passwordEditText)
                viewModel.onSignIn(userResponse)
                AndroidUtils.hideKeyboard(requireView())

            }
        }
        viewModel.data.observe(viewLifecycleOwner) {
            if (it.token != null) {
                findNavController().navigateUp()
            }
        }
        return binding.root
    }
}