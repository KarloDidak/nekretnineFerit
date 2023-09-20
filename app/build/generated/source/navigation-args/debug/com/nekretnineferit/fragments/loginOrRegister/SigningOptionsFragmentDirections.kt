package com.nekretnineferit.fragments.loginOrRegister

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.nekretnineferit.R

public class SigningOptionsFragmentDirections private constructor() {
  public companion object {
    public fun actionSigninOptionsFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_signinOptionsFragment_to_loginFragment)

    public fun actionSigninOptionsFragmentToRegisterFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_signinOptionsFragment_to_registerFragment)
  }
}
