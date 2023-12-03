// Generated by view binder compiler. Do not edit!
package com.example.b07project.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.b07project.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class AdminEventManagementListviewBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Toolbar backBar;

  @NonNull
  public final ListView myListView;

  private AdminEventManagementListviewBinding(@NonNull LinearLayout rootView,
      @NonNull Toolbar backBar, @NonNull ListView myListView) {
    this.rootView = rootView;
    this.backBar = backBar;
    this.myListView = myListView;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static AdminEventManagementListviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static AdminEventManagementListviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.admin_event_management_listview, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static AdminEventManagementListviewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.back_bar;
      Toolbar backBar = ViewBindings.findChildViewById(rootView, id);
      if (backBar == null) {
        break missingId;
      }

      id = R.id.my_list_view;
      ListView myListView = ViewBindings.findChildViewById(rootView, id);
      if (myListView == null) {
        break missingId;
      }

      return new AdminEventManagementListviewBinding((LinearLayout) rootView, backBar, myListView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
