package com.nekretnineferit.fragments.loginOrRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nekretnineferit.R
import com.nekretnineferit.activities.BrowsingActivity
import com.nekretnineferit.databinding.Fragment1SigninOptionsBinding

class SigningOptionsFragment: Fragment(R.layout.fragment1_signin_options) {
    private lateinit var binding: Fragment1SigninOptionsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment1SigninOptionsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_signinOptionsFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                findNavController().navigate(R.id.action_signinOptionsFragment_to_loginFragment)
            }

            btnBrowse.setOnClickListener {
                Intent(requireActivity(), BrowsingActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }
}