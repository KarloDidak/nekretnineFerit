package com.nekretnineferit.fragments.browsing

import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nekretnineferit.databinding.Fragment7HouseAddBinding
import com.nekretnineferit.R
import com.nekretnineferit.adapters.HouseImagesAdapter
import com.nekretnineferit.data.House
import com.nekretnineferit.firebase.firebaseFlowHouses
import com.nekretnineferit.firebase.MESSAGE_TYPE.*
import com.nekretnineferit.firebase.userGetCurrent
import com.nekretnineferit.utils.Constants
import com.nekretnineferit.utils.Constants.EMPTY
import com.nekretnineferit.utils.Constants.IMAGE_MIME
import com.nekretnineferit.utils.loadImages
import com.nekretnineferit.utils.showErrorDialog
import com.nekretnineferit.utils.validateHouseInputs
import com.nekretnineferit.viewmodel.browsing.HouseAddViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID

class HouseAddFragment : Fragment(R.layout.fragment7_house_add) {
    private lateinit var binding: Fragment7HouseAddBinding
    private val viewModel by viewModels<HouseAddViewModel>()

    private val args by navArgs<HouseDetailsFragmentArgs>()
    private val viewPagerAdapter by lazy { HouseImagesAdapter() }

    var house = House()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment7HouseAddBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        house = args.house

        val imageActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                house.images = loadImages(it)
                if (house.images.isNotEmpty())
                    viewPagerAdapter.differ.submitList(house.images)
            }

        binding.apply {
            vpImages.adapter = viewPagerAdapter

            edPrice.setText(house.price.toString())
            edLocation.setText(house.location)
            edArea.setText(house.area.toString())
            edCategory.setText(house.category)
            edDescription.setText(house.description)

            btnImagePicker.setOnClickListener {
                val intent = Intent(ACTION_GET_CONTENT)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                intent.type = IMAGE_MIME
                imageActivityResultLauncher.launch(intent)
            }

            btnSave.setOnClickListener {
                val checkInputs = validateHouseInputs(
                    edPrice.text.toString().trim(),
                    edLocation.text.toString().trim(),
                    edArea.text.toString().trim(),
                    edCategory.text.toString().trim(),
                    edDescription.text.toString().trim(),
                    house.images
                )

                if (checkInputs.type != VALIDATION_OK) {
                    showErrorDialog(requireContext(), checkInputs.message)
                } else {
                    house.apply {
                        id = if (id == EMPTY) UUID.randomUUID().toString() else id
                        userId = if (userId == EMPTY) userGetCurrent()?.uid!! else userId
                        price = edPrice.text.toString().trim().toFloat()
                        location = edLocation.text.toString().trim()
                        area = edArea.text.toString().trim().toFloat()
                        category = edCategory.text.toString().trim()
                        description = edDescription.text.toString().trim()
                    }

                    viewModel.doAddHouse(house)
                }
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
                        findNavController().navigate(R.id.action_houseAddFragment_to_homeFragment)
                    else
                        showErrorDialog(requireContext(), it.message!!)
                }
            }
        }

        showTesting()
    }

    private fun showLoading() {
        binding.apply {
            pbHouseAdd.visibility = View.VISIBLE
            cvImages.visibility = View.GONE
            edPrice.isEnabled = false
            edLocation.isEnabled = false
            edArea.isEnabled = false
            edCategory.isEnabled = false
            edDescription.isEnabled = false
            btnImagePicker.isEnabled = false
            btnSave.isEnabled = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            pbHouseAdd.visibility = View.GONE
            cvImages.visibility = View.VISIBLE
            edPrice.isEnabled = true
            edLocation.isEnabled = true
            edArea.isEnabled = true
            edCategory.isEnabled = true
            edDescription.isEnabled = true
            btnImagePicker.isEnabled = true
            btnSave.isEnabled = true
        }
    }

    private fun showTesting() {
        if (!Constants.TESTING)
            return

        binding.apply {
            edPrice.setText(requireContext().getString(R.string.et_price_init))
            edLocation.setText(requireContext().getString(R.string.et_location_init))
            edArea.setText(requireContext().getString(R.string.et_area_init))
            edCategory.setText(requireContext().getString(R.string.et_category_init))
            edDescription.setText(requireContext().getString(R.string.et_description_init))
        }
    }
}