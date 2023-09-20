package com.nekretnineferit.fragments.loginOrRegister

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.nekretnineferit.R

public class RegisterFragmentDirections private constructor() {
  public companion object {
    public fun actionRegisterFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_registerFragment_to_loginFragment)
  }
}
