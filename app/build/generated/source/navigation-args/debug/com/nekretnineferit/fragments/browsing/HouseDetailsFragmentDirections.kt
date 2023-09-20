package com.nekretnineferit.fragments.browsing

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.nekretnineferit.R
import com.nekretnineferit.`data`.House
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class HouseDetailsFragmentDirections private constructor() {
  private data class ActionHouseDetailsFragmentToHouseAddFragment(
    public val house: House
  ) : NavDirections {
    public override val actionId: Int = R.id.action_houseDetailsFragment_to_houseAddFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
        if (Parcelable::class.java.isAssignableFrom(House::class.java)) {
          result.putParcelable("house", this.house as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(House::class.java)) {
          result.putSerializable("house", this.house as Serializable)
        } else {
          throw UnsupportedOperationException(House::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        return result
      }
  }

  public companion object {
    public fun actionHouseDetailsFragmentToHomeFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_houseDetailsFragment_to_homeFragment)

    public fun actionHouseDetailsFragmentToHouseAddFragment(house: House): NavDirections =
        ActionHouseDetailsFragmentToHouseAddFragment(house)
  }
}
