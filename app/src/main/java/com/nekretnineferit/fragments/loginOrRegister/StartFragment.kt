package com.nekretnineferit.fragments.loginOrRegister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nekretnineferit.R
import com.nekretnineferit.activities.BrowsingActivity
import com.nekretnineferit.databinding.Fragment0StartBinding
import com.nekretnineferit.viewmodel.loginOrRegister.StartViewModel
import com.nekretnineferit.viewmodel.loginOrRegister.StartViewModel.Companion.GO_TO_BROWSING_ACTIVITY
import com.nekretnineferit.viewmodel.loginOrRegister.StartViewModel.Companion.SHOW_START_BUTTON
import kotlinx.coroutines.launch

class StartFragment : Fragment(R.layout.fragment0_start) {
    private lateinit var binding: Fragment0StartBinding
    private val viewModel by viewModels<StartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment0StartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            btnStart.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_signinOptionsFragment)
            }
            btnStart.visibility = View.INVISIBLE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigate.collect {
                when (it) {
                    SHOW_START_BUTTON -> {
                        binding.btnStart.visibility = View.VISIBLE
                    }
                    GO_TO_BROWSING_ACTIVITY -> {
                        Intent(requireActivity(), BrowsingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }
                    else -> Unit
                }
            }
        }
    }
}