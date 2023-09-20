package com.nekretnineferit.fragments.loginOrRegister

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nekretnineferit.R
import com.nekretnineferit.databinding.Fragment2LoginBinding
import com.nekretnineferit.firebase.firebaseFlowUser
import com.nekretnineferit.utils.Constants.TESTING
import com.nekretnineferit.utils.collectUserSigningFlow
import com.nekretnineferit.viewmodel.loginOrRegister.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment2_login) {
    private lateinit var binding: Fragment2LoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment2LoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            tvGotoRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString()
                viewModel.doUserLogin(email, password)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            firebaseFlowUser.collect {
                binding.apply {
                    collectUserSigningFlow(
                        this@LoginFragment, it, etEmail, etPassword, ::showLoading, ::hideLoading
                    )
                }
            }
        }

        showTesting()
    }

    private fun showLoading() {
        binding.apply {
            pbLogin.visibility = View.VISIBLE
            etEmail.isEnabled = false
            etPassword.isEnabled = false
            btnLogin.isEnabled = false
            tvGotoRegister.isEnabled = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            pbLogin.visibility = View.GONE
            etEmail.isEnabled = true
            etPassword.isEnabled = true
            btnLogin.isEnabled = true
            tvGotoRegister.isEnabled = true
        }
    }

    private fun showTesting() {
        if (!TESTING)
            return

        binding.apply {
            etEmail.setText(getString(R.string.et_email_init))
            etPassword.setText(getString(R.string.et_password_init))
        }
    }
}