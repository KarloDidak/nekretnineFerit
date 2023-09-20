// Generated by view binder compiler. Do not edit!
package com.nekretnineferit.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.nekretnineferit.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class Fragment4UserAccountBinding implements ViewBinding {
  @NonNull
  private final NestedScrollView rootView;

  @NonNull
  public final AppCompatButton btnSave;

  @NonNull
  public final EditText etEmail;

  @NonNull
  public final EditText etUserName;

  @NonNull
  public final Guideline gdlLeft;

  @NonNull
  public final Guideline gdlRight;

  @NonNull
  public final ImageView imEdit;

  @NonNull
  public final ImageView ivUser;

  @NonNull
  public final ProgressBar pbUserAccount;

  @NonNull
  public final TextView tvProfile;

  private Fragment4UserAccountBinding(@NonNull NestedScrollView rootView,
      @NonNull AppCompatButton btnSave, @NonNull EditText etEmail, @NonNull EditText etUserName,
      @NonNull Guideline gdlLeft, @NonNull Guideline gdlRight, @NonNull ImageView imEdit,
      @NonNull ImageView ivUser, @NonNull ProgressBar pbUserAccount, @NonNull TextView tvProfile) {
    this.rootView = rootView;
    this.btnSave = btnSave;
    this.etEmail = etEmail;
    this.etUserName = etUserName;
    this.gdlLeft = gdlLeft;
    this.gdlRight = gdlRight;
    this.imEdit = imEdit;
    this.ivUser = ivUser;
    this.pbUserAccount = pbUserAccount;
    this.tvProfile = tvProfile;
  }

  @Override
  @NonNull
  public NestedScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static Fragment4UserAccountBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static Fragment4UserAccountBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment4_user_account, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static Fragment4UserAccountBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnSave;
      AppCompatButton btnSave = ViewBindings.findChildViewById(rootView, id);
      if (btnSave == null) {
        break missingId;
      }

      id = R.id.etEmail;
      EditText etEmail = ViewBindings.findChildViewById(rootView, id);
      if (etEmail == null) {
        break missingId;
      }

      id = R.id.etUserName;
      EditText etUserName = ViewBindings.findChildViewById(rootView, id);
      if (etUserName == null) {
        break missingId;
      }

      id = R.id.gdl_left;
      Guideline gdlLeft = ViewBindings.findChildViewById(rootView, id);
      if (gdlLeft == null) {
        break missingId;
      }

      id = R.id.gdl_right;
      Guideline gdlRight = ViewBindings.findChildViewById(rootView, id);
      if (gdlRight == null) {
        break missingId;
      }

      id = R.id.imEdit;
      ImageView imEdit = ViewBindings.findChildViewById(rootView, id);
      if (imEdit == null) {
        break missingId;
      }

      id = R.id.ivUser;
      ImageView ivUser = ViewBindings.findChildViewById(rootView, id);
      if (ivUser == null) {
        break missingId;
      }

      id = R.id.pbUserAccount;
      ProgressBar pbUserAccount = ViewBindings.findChildViewById(rootView, id);
      if (pbUserAccount == null) {
        break missingId;
      }

      id = R.id.tv_profile;
      TextView tvProfile = ViewBindings.findChildViewById(rootView, id);
      if (tvProfile == null) {
        break missingId;
      }

      return new Fragment4UserAccountBinding((NestedScrollView) rootView, btnSave, etEmail,
          etUserName, gdlLeft, gdlRight, imEdit, ivUser, pbUserAccount, tvProfile);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
