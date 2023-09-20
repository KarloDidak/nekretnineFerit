package com.nekretnineferit.fragments.browsing

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.nekretnineferit.R
import com.nekretnineferit.`data`.House
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class HomeFragmentDirections private constructor() {
  private data class ActionHomeFragmentToHouseDetailsFragment(
    public val house: House
  ) : NavDirections {
    public override val actionId: Int = R.id.action_homeFragment_to_houseDetailsFragment

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

  private data class ActionHomeFragmentToHouseAddFragment(
    public val house: House
  ) : NavDirections {
    public override val actionId: Int = R.id.action_homeFragment_to_houseAddFragment

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
    public fun actionHomeFragmentToHouseDetailsFragment(house: House): NavDirections =
        ActionHomeFragmentToHouseDetailsFragment(house)

    public fun actionHomeFragmentToHouseAddFragment(house: House): NavDirections =
        ActionHomeFragmentToHouseAddFragment(house)
  }
}
