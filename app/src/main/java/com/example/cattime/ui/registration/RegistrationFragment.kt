package com.example.cattime.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cattime.R
import com.example.cattime.data.DataSource
import com.example.cattime.domain.Repository
import com.example.cattime.ui.factory.VMFactory
import com.example.cattime.ui.sharedViewModel.SharedUserViewModel

class RegistrationFragment : Fragment() {

    private val viewModel by activityViewModels<SharedUserViewModel> {
        VMFactory(
                Repository(
                        DataSource()
                )
        )
    }
    private lateinit var userEmail: TextView
    private lateinit var userPassword: TextView
    private lateinit var userConfirmPassword: TextView
    private lateinit var signUp: Button

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userEmail = view.findViewById(R.id.editTextYourEmail)
        userPassword = view.findViewById(R.id.editTextYourPassword)
        userConfirmPassword = view.findViewById(R.id.editTextConfirmPassword)
        signUp = view.findViewById(R.id.register_btn)

        setupSignUpUser()
    }

    private fun setupSignUpUser() {
        signUp.setOnClickListener {
            if (viewModel.verifyEmail(userEmail.text.toString())) {
                if (userPassword.text.toString() == userConfirmPassword.text.toString()) {
                    if (viewModel.verifyPassword(userPassword.text.toString())) {
                        viewModel.insertUser(
                                requireActivity().applicationContext,
                                userEmail.text.toString(),
                                userPassword.text.toString()
                        )
                        findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                    } else {
                        userPassword.error = "Invalid password"
                        userPassword.text = ""
                    }
                } else {
                    userConfirmPassword.error = "Invalid confirmation password"
                    userConfirmPassword.text = ""
                }
            } else {
                userEmail.error = "Invalid Email"
                userEmail.text = ""
            }

        }
    }
}