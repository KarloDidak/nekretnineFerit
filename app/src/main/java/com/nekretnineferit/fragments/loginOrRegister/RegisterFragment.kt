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
import com.nekretnineferit.databinding.Fragment3RegisterBinding
import com.nekretnineferit.firebase.firebaseFlowUser
import com.nekretnineferit.utils.Constants
import com.nekretnineferit.utils.collectUserSigningFlow
import com.nekretnineferit.viewmodel.loginOrRegister.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var binding: Fragment3RegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment3RegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnRegister.setOnClickListener {
                val name = etUserName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString()
                viewModel.doUserRegister(name, email, password)
            }

            tvGotoLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            firebaseFlowUser.collect {
                binding.apply {
                    collectUserSigningFlow(
                        this@RegisterFragment, it, etEmail, etPassword, ::showLoading, ::hideLoading
                    )
                }
            }
        }

        showTesting()
    }

    private fun showLoading() {
        binding.apply {
            pbRegister.visibility = View.VISIBLE
            etUserName.isEnabled = false
            etEmail.isEnabled = false
            etPassword.isEnabled = false
            btnRegister.isEnabled = false
            tvGotoLogin.isEnabled = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            pbRegister.visibility = View.GONE
            etUserName.isEnabled = true
            etEmail.isEnabled = true
            etPassword.isEnabled = true
            btnRegister.isEnabled = true
            tvGotoLogin.isEnabled = true
        }
    }

    private fun showTesting() {
        if (!Constants.TESTING)
            return

        binding.apply {
            etUserName.setText(getString(R.string.et_user_name_init))
            etEmail.setText(getString(R.string.et_email_init))
            etPassword.setText(getString(R.string.et_password_init))
        }
    }
}