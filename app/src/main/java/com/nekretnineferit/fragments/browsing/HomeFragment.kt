package com.nekretnineferit.fragments.browsing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.nekretnineferit.R
import com.nekretnineferit.activities.BrowsingActivity
import com.nekretnineferit.activities.SigningActivity
import com.nekretnineferit.adapters.HouseAdapter
import com.nekretnineferit.data.House
import com.nekretnineferit.databinding.Fragment5HomeBinding
import com.nekretnineferit.firebase.userLogout
import com.nekretnineferit.firebase.firebaseFlowHouses
import com.nekretnineferit.firebase.userGetCurrent
import com.nekretnineferit.utils.Constants.PARCELABLE_HOUSE
import com.nekretnineferit.firebase.MESSAGE_TYPE.*
import com.nekretnineferit.utils.showConfirmationDialog
import com.nekretnineferit.utils.showErrorDialog
import com.nekretnineferit.utils.showUser
import com.nekretnineferit.viewmodel.browsing.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment: Fragment(R.layout.fragment5_home) {
    private lateinit var binding: Fragment5HomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    private var housesList = emptyList<House>()
    private lateinit var houseAdapter: HouseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment5HomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        houseAdapter = HouseAdapter()
        houseAdapter.onClick = {
            val b = Bundle().apply { putParcelable(PARCELABLE_HOUSE, it) }
            findNavController().navigate(R.id.action_homeFragment_to_houseDetailsFragment, b)
        }

        binding.apply {
            rvHouses.layoutManager =
                LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
            rvHouses.adapter = houseAdapter

            btnRefresh.setOnClickListener {
                viewModel.doGetHouses()
            }

            tlHouses.getTabAt(BrowsingActivity.tabIdx)?.select()
            tlHouses.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    BrowsingActivity.tabIdx = tab?.position!!
                    houseAdapter.differ.submitList(filterHouses())
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })

            btnHouseAdd.setOnClickListener {
                val b = Bundle().apply { putParcelable(PARCELABLE_HOUSE, House()) }
                findNavController().navigate(R.id.action_homeFragment_to_houseAddFragment, b)
            }

            btnLogout.setOnClickListener {
                showConfirmationDialog(requireContext(),
                    getString(R.string.btn_logout),
                    getString(R.string.logout_question),
                    ::doUserLogout)
            }

            val currUser = userGetCurrent()
            if (currUser == null) {
                llUser1.visibility = View.GONE
                llUser2.visibility = View.GONE
            } else {
                llUser1.visibility = View.VISIBLE
                llUser2.visibility = View.VISIBLE
                showUser(this@HomeFragment, tvUser, null, ivUser)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            firebaseFlowHouses.collectLatest {
                if (it.type == FIREBASE_LOADING)
                    showLoading()
                else {
                    hideLoading()
                    if (it.type == FIREBASE_SUCCESS)
                        houseAdapter.differ.submitList(filterHouses(it.data!!))
                    else
                        showErrorDialog(requireContext(), it.message!!)
                }
            }
        }
    }

    private fun doUserLogout() {
        userLogout()
        val intent = Intent(requireActivity(), SigningActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    fun filterHouses(houses: List<House> = emptyList()): List<House> {
        val currUser = userGetCurrent()
        if (currUser == null) {
            housesList = houses.toList()
            return housesList
        }

        if (houses.isNotEmpty())
            housesList = houses.toList()

        return when(BrowsingActivity.tabIdx) {
            0 -> housesList
            1 -> housesList.filter { it.userId == currUser.uid }
            2 -> housesList.filter { it.userId != currUser.uid }
            else -> housesList
        }
    }

    private fun hideLoading() {
        binding.apply {
            pbHome.visibility = View.GONE
            rvHouses.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.apply {
            pbHome.visibility = View.VISIBLE
            rvHouses.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.doGetHouses()

        val nav = (activity as BrowsingActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val currUser = userGetCurrent()
        if (currUser == null)
            nav.visibility = View.GONE
        else
            nav.visibility = View.VISIBLE
    }
}