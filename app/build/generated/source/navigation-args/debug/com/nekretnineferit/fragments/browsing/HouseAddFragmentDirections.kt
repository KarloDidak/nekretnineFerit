package com.nekretnineferit.fragments.browsing

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.nekretnineferit.R

public class HouseAddFragmentDirections private constructor() {
  public companion object {
    public fun actionHouseAddFragmentToHomeFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_houseAddFragment_to_homeFragment)
  }
}
