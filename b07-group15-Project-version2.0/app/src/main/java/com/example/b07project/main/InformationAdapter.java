package com.example.b07project.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.b07project.R;
import java.util.List;

public class InformationAdapter extends ArrayAdapter<Information> {

    public InformationAdapter(Context context, int resource, List<Information> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_text, parent, false);
        }

        Information info = getItem(position);

        if (info != null) {
            TextView subjectText = view.findViewById(R.id.item_text);
            if (subjectText != null && info.subject != null) {
//                    if (info.subject.equals("")){ info.subject = "Empty Subject";}
                    subjectText.setText(info.subject);
                }
            }
        return view;
    }
}
