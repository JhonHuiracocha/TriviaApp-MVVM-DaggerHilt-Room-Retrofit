package com.multi.trivia.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alirezaashrafi.library.Codia
import com.multi.trivia.BuildConfig
import com.multi.trivia.data.model.User
import com.multi.trivia.databinding.FragmentRegisterBinding
import com.multi.trivia.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    private val codia: Codia by lazy { Codia(BuildConfig.ENCRYPTION_KEY) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnRegister.setOnClickListener { register(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun register(view: View) {
        val username = binding.edtUsername.text.toString().trim()
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val confirmPassword = binding.edtConfirmPassword.text.toString().trim()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(view.context, "You must enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(view.context, "Passwords are not the same", Toast.LENGTH_SHORT).show()
            return
        }

        val encrytedPassword = codia.encode(password)
        val user = User(0, username, email, encrytedPassword, true)

        viewModel.register(user)

        viewModel.statusRegister.observe(viewLifecycleOwner) { userId ->
            println("USER ID: $userId")

            if (userId == null) {
                Toast.makeText(
                    view.context,
                    "An error occurred while registering the user",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }

            Toast.makeText(
                view.context,
                "User registered successfully",
                Toast.LENGTH_SHORT
            ).show()

            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            findNavController().navigate(action)
        }
    }
}