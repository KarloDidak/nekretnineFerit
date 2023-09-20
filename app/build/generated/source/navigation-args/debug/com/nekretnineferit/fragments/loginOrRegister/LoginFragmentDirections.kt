package com.nekretnineferit.fragments.loginOrRegister

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.nekretnineferit.R

public class LoginFragmentDirections private constructor() {
  public companion object {
    public fun actionLoginFragmentToRegisterFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_loginFragment_to_registerFragment)
  }
}
