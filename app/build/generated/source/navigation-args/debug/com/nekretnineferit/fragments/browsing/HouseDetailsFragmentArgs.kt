package com.nekretnineferit.fragments.browsing

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.nekretnineferit.`data`.House
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class HouseDetailsFragmentArgs(
  public val house: House
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
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

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    if (Parcelable::class.java.isAssignableFrom(House::class.java)) {
      result.set("house", this.house as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(House::class.java)) {
      result.set("house", this.house as Serializable)
    } else {
      throw UnsupportedOperationException(House::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): HouseDetailsFragmentArgs {
      bundle.setClassLoader(HouseDetailsFragmentArgs::class.java.classLoader)
      val __house : House?
      if (bundle.containsKey("house")) {
        if (Parcelable::class.java.isAssignableFrom(House::class.java) ||
            Serializable::class.java.isAssignableFrom(House::class.java)) {
          __house = bundle.get("house") as House?
        } else {
          throw UnsupportedOperationException(House::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__house == null) {
          throw IllegalArgumentException("Argument \"house\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"house\" is missing and does not have an android:defaultValue")
      }
      return HouseDetailsFragmentArgs(__house)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): HouseDetailsFragmentArgs {
      val __house : House?
      if (savedStateHandle.contains("house")) {
        if (Parcelable::class.java.isAssignableFrom(House::class.java) ||
            Serializable::class.java.isAssignableFrom(House::class.java)) {
          __house = savedStateHandle.get<House?>("house")
        } else {
          throw UnsupportedOperationException(House::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__house == null) {
          throw IllegalArgumentException("Argument \"house\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"house\" is missing and does not have an android:defaultValue")
      }
      return HouseDetailsFragmentArgs(__house)
    }
  }
}
