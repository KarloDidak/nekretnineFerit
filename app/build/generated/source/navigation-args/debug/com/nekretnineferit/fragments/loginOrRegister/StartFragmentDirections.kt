package com.nekretnineferit.fragments.loginOrRegister

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.nekretnineferit.R

public class StartFragmentDirections private constructor() {
  public companion object {
    public fun actionStartFragmentToSigninOptionsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_startFragment_to_signinOptionsFragment)
  }
}
