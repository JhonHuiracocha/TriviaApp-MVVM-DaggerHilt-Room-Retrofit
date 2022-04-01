package com.multi.trivia.ui.auth.login

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
import com.multi.trivia.databinding.FragmentLoginBinding
import com.multi.trivia.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    private val codia: Codia by lazy { Codia(BuildConfig.ENCRYPTION_KEY) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnLogin.setOnClickListener { login(it) }

        binding.tvSignUp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun login(view: View) {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(view.context, "You must enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val encrytedPassword = codia.encode(password)

        viewModel.login(email, encrytedPassword).observe(viewLifecycleOwner) { user ->
            if (user == null) {
                Toast.makeText(view.context, "Email or password are incorrect", Toast.LENGTH_SHORT)
                    .show()
                return@observe
            }

            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment(user)
            findNavController().navigate(action)
        }
    }
}