// Generated by view binder compiler. Do not edit!
package com.example.b07project.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.b07project.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ListItemTextFeedbackBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView contentTextView;

  @NonNull
  public final TextView rateTextView;

  private ListItemTextFeedbackBinding(@NonNull LinearLayout rootView,
      @NonNull TextView contentTextView, @NonNull TextView rateTextView) {
    this.rootView = rootView;
    this.contentTextView = contentTextView;
    this.rateTextView = rateTextView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListItemTextFeedbackBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListItemTextFeedbackBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.list_item_text_feedback, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListItemTextFeedbackBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.content_text_view;
      TextView contentTextView = ViewBindings.findChildViewById(rootView, id);
      if (contentTextView == null) {
        break missingId;
      }

      id = R.id.rate_text_view;
      TextView rateTextView = ViewBindings.findChildViewById(rootView, id);
      if (rateTextView == null) {
        break missingId;
      }

      return new ListItemTextFeedbackBinding((LinearLayout) rootView, contentTextView,
          rateTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
