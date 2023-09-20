package com.nekretnineferit.fragments.browsing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nekretnineferit.R
import com.nekretnineferit.activities.BrowsingActivity
import com.nekretnineferit.adapters.HouseImagesAdapter
import com.nekretnineferit.data.House
import com.nekretnineferit.databinding.Fragment6HouseDetailsBinding
import com.nekretnineferit.firebase.firebaseFlowHouses
import com.nekretnineferit.firebase.userGetCurrent
import com.nekretnineferit.utils.Constants.PARCELABLE_HOUSE
import com.nekretnineferit.firebase.MESSAGE_TYPE.*
import com.nekretnineferit.utils.Constants.ADMIN
import com.nekretnineferit.utils.showConfirmationDialog
import com.nekretnineferit.utils.showErrorDialog
import com.nekretnineferit.viewmodel.browsing.HouseDetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HouseDetailsFragment : Fragment() {
    private lateinit var binding: Fragment6HouseDetailsBinding
    private val viewModel by viewModels<HouseDetailsViewModel>()

    private val args by navArgs<HouseDetailsFragmentArgs>()
    private val viewPagerAdapter by lazy { HouseImagesAdapter() }

    var house = House()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BrowsingActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation).
        visibility = View.GONE
        
        binding = Fragment6HouseDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        house = args.house

        binding.apply {
            vpImages.adapter = viewPagerAdapter

            tvPrice.text = getString(R.string.tv_price_format, house.price.toString())
            tvLocation.text = house.location
            tvArea.text = getString(R.string.tv_area_format, house.area.toString())
            tvCategory.text = house.category
            tvDescription.text = house.description

            val currUser = userGetCurrent()
            if (currUser == null) {
                llHouseChange.visibility = View.GONE
            } else {
                if (currUser.email.toString() == ADMIN
                    || house.userId == currUser.uid
                ) {
                    llHouseChange.visibility = View.VISIBLE
                } else {
                    llHouseChange.visibility = View.GONE
                }
            }

            btnHouseUpdate.setOnClickListener {
                val b = Bundle().apply { putParcelable(PARCELABLE_HOUSE, house) }
                findNavController().navigate(R.id.action_houseDetailsFragment_to_houseAddFragment, b)
            }

            btnHouseDelete.setOnClickListener {
                showConfirmationDialog(requireContext(), getString(R.string.btn_delete),
                    getString(R.string.msg_delete_confirm)) { viewModel.doDeleteHouse(house) }
            }
        }

        viewPagerAdapter.differ.submitList(house.images)

        viewLifecycleOwner.lifecycleScope.launch {
            firebaseFlowHouses.collectLatest {
                if (it.type == FIREBASE_LOADING)
                    showLoading()
                else {
                    hideLoading()
                    if (it.type == FIREBASE_SUCCESS)
                        findNavController().navigate(R.id.action_houseDetailsFragment_to_homeFragment)
                    else
                        showErrorDialog(requireContext(), it.message!!)
                }
            }
        }
    }

    private fun hideLoading() {
        binding.apply {
            pbHouseDetails.visibility = View.GONE
            cvImages.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.apply {
            pbHouseDetails.visibility = View.VISIBLE
            cvImages.visibility = View.GONE
        }
    }
}