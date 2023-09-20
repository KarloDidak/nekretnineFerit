package com.nekretnineferit.fragments.browsing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nekretnineferit.databinding.Fragment4UserAccountBinding
import com.nekretnineferit.firebase.firebaseFlowUser
import com.nekretnineferit.firebase.MESSAGE_TYPE.*
import com.nekretnineferit.utils.Constants.EMPTY
import com.nekretnineferit.utils.Constants.IMAGE_MIME
import com.nekretnineferit.utils.loadImages
import com.nekretnineferit.utils.showErrorDialog
import com.nekretnineferit.utils.showUser
import com.nekretnineferit.viewmodel.browsing.UserAccountViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserAccountFragment : Fragment() {
    private lateinit var binding: Fragment4UserAccountBinding
    private val viewModel by viewModels<UserAccountViewModel>()

    private var selectedImage: String = EMPTY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment4UserAccountBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                val loadResult = loadImages(it)
                if (loadResult.isNotEmpty()) {
                    selectedImage = loadResult[0]
                    if (selectedImage != EMPTY)
                        Glide.with(this@UserAccountFragment).load(selectedImage)
                            .into(binding.ivUser)
                }
            }

        binding.apply {
            imEdit.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = IMAGE_MIME
                imageActivityResultLauncher.launch(intent)
            }

            btnSave.setOnClickListener {
                val name = etUserName.text.toString().trim()
                val email = etEmail.text.toString().trim()
                viewModel.doUserUpdate(name, email, selectedImage)
            }

            showUser(this@UserAccountFragment, etUserName, etEmail, ivUser)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            firebaseFlowUser.collectLatest {
                if (it.type == FIREBASE_LOADING)
                    showLoading()
                else {
                    hideLoading()
                    if (it.type == FIREBASE_SUCCESS)
                        binding.apply {
                            showUser(this@UserAccountFragment, etUserName, etEmail, ivUser)
                        }
                    else
                        showErrorDialog(requireContext(), it.message!!)
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            pbUserAccount.visibility = View.VISIBLE
            ivUser.isEnabled = false
            imEdit.isEnabled = false
            etUserName.isEnabled = false
            etEmail.isEnabled = false
            btnSave.isEnabled = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            pbUserAccount.visibility = View.GONE
            ivUser.isEnabled = true
            imEdit.isEnabled = true
            etUserName.isEnabled = true
            etEmail.isEnabled = true
            btnSave.isEnabled = true
        }
    }
}










