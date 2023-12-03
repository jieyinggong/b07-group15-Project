package com.example.b07project.Admin_Event_Management;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.b07project.R;
import com.example.b07project.main.Feedback;
import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;


public class FeedbackAdapter extends ArrayAdapter<Feedback> {
    public FeedbackAdapter(Context context, List<Feedback> feedbacks) {
        super(context, R.layout.list_item_text_feedback, feedbacks);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Feedback feedback = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_text_feedback, parent, false);
        }

        TextView textViewContent = convertView.findViewById(R.id.content_text_view);
        TextView textViewRate = convertView.findViewById(R.id.rate_text_view);

        if (feedback != null) {
            textViewContent.setText(feedback.content);
            textViewRate.setText(String.valueOf(feedback.numericRating));
        }

        return convertView;
    }
}
