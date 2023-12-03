// Generated by view binder compiler. Do not edit!
package com.example.b07project.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.b07project.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class StudentPostCheckerCsMajorBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Toolbar backBar;

  @NonNull
  public final Button noButton;

  @NonNull
  public final TextView textView3;

  @NonNull
  public final Button yesButton;

  private StudentPostCheckerCsMajorBinding(@NonNull ConstraintLayout rootView,
      @NonNull Toolbar backBar, @NonNull Button noButton, @NonNull TextView textView3,
      @NonNull Button yesButton) {
    this.rootView = rootView;
    this.backBar = backBar;
    this.noButton = noButton;
    this.textView3 = textView3;
    this.yesButton = yesButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static StudentPostCheckerCsMajorBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static StudentPostCheckerCsMajorBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.student_post_checker_cs_major, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static StudentPostCheckerCsMajorBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back_bar;
      Toolbar backBar = ViewBindings.findChildViewById(rootView, id);
      if (backBar == null) {
        break missingId;
      }

      id = R.id.noButton;
      Button noButton = ViewBindings.findChildViewById(rootView, id);
      if (noButton == null) {
        break missingId;
      }

      id = R.id.textView3;
      TextView textView3 = ViewBindings.findChildViewById(rootView, id);
      if (textView3 == null) {
        break missingId;
      }

      id = R.id.yesButton;
      Button yesButton = ViewBindings.findChildViewById(rootView, id);
      if (yesButton == null) {
        break missingId;
      }

      return new StudentPostCheckerCsMajorBinding((ConstraintLayout) rootView, backBar, noButton,
          textView3, yesButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
