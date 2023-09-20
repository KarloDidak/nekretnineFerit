package com.nekretnineferit.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.nekretnineferit.R
import com.nekretnineferit.databinding.ActivityBrowsingBinding
import com.nekretnineferit.firebase.userGetCurrent

class BrowsingActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityBrowsingBinding.inflate(layoutInflater)
    }
    companion object {
        var tabIdx = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navController = findNavController(R.id.browsingHostFragment)

        val currUser = userGetCurrent()

        if (currUser == null) {
            binding.bottomNavigation.visibility = View.GONE
        } else {
            binding.bottomNavigation.visibility = View.VISIBLE
            binding.bottomNavigation.setupWithNavController(navController)
        }
    }
}