package com.example.cattime.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cattime.R
import com.example.cattime.data.DataSource
import com.example.cattime.domain.Repository
import com.example.cattime.ui.factory.VMFactory
import com.example.cattime.ui.sharedViewModel.SharedUserViewModel
import com.example.cattime.valueObject.Resource

class LoginFragment : Fragment() {

    private val viewModel by activityViewModels<SharedUserViewModel> {
        VMFactory(
                Repository(
                        DataSource()
                )
        )
    }

    private lateinit var userEmail: TextView
    private lateinit var userPassword: TextView
    private lateinit var progressBar: RelativeLayout
    private lateinit var signUp: Button
    private lateinit var logIn: Button

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userEmail = view.findViewById(R.id.editTextEmail)
        userPassword = view.findViewById(R.id.editTextPassword)
        progressBar = view.findViewById(R.id.progressBarLogIn)
        signUp = view.findViewById(R.id.signUp_btn)
        logIn = view.findViewById(R.id.login_btn)
        setupSignUpButton()
        setupLogInButton()
    }

    private fun setupSignUpButton() {
        signUp.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
    }

    private fun setupLogInButton() {
        logIn.setOnClickListener {
            viewModel.getUser(requireActivity().applicationContext).observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        progressBar.visibility = View.GONE
                        if (result.data.email == userEmail.text.toString()) {
                            if (result.data.password == userPassword.text.toString()) {
                                Toast.makeText(requireContext(), "Welcome! ${result.data.email}", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_catBreedsList)
                            } else {
                                userPassword.error = "Incorrect Password"
                                userPassword.text = ""
                            }
                        } else {
                            userEmail.error = "Email Not Found"
                            userEmail.text = ""
                            userPassword.text = ""
                        }
                    }
                    is Resource.Failure -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(
                                requireContext(),
                                "Something went wrong: ${result.exception}",
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }
}