// Generated by view binder compiler. Do not edit!
package com.nekretnineferit.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.nekretnineferit.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class Fragment0StartBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton btnStart;

  @NonNull
  public final Guideline gdlLeft;

  @NonNull
  public final Guideline gdlRight;

  @NonNull
  public final ImageView ivIcHouse;

  @NonNull
  public final TextView tvInfo1;

  @NonNull
  public final TextView tvInfo2;

  private Fragment0StartBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton btnStart, @NonNull Guideline gdlLeft, @NonNull Guideline gdlRight,
      @NonNull ImageView ivIcHouse, @NonNull TextView tvInfo1, @NonNull TextView tvInfo2) {
    this.rootView = rootView;
    this.btnStart = btnStart;
    this.gdlLeft = gdlLeft;
    this.gdlRight = gdlRight;
    this.ivIcHouse = ivIcHouse;
    this.tvInfo1 = tvInfo1;
    this.tvInfo2 = tvInfo2;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static Fragment0StartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static Fragment0StartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment0_start, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static Fragment0StartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnStart;
      AppCompatButton btnStart = ViewBindings.findChildViewById(rootView, id);
      if (btnStart == null) {
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

      id = R.id.iv_ic_house;
      ImageView ivIcHouse = ViewBindings.findChildViewById(rootView, id);
      if (ivIcHouse == null) {
        break missingId;
      }

      id = R.id.tv_info1;
      TextView tvInfo1 = ViewBindings.findChildViewById(rootView, id);
      if (tvInfo1 == null) {
        break missingId;
      }

      id = R.id.tv_info2;
      TextView tvInfo2 = ViewBindings.findChildViewById(rootView, id);
      if (tvInfo2 == null) {
        break missingId;
      }

      return new Fragment0StartBinding((ConstraintLayout) rootView, btnStart, gdlLeft, gdlRight,
          ivIcHouse, tvInfo1, tvInfo2);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
