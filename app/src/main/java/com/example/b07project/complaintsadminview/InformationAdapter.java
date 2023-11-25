package com.example.b07project.complaintsadminview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.b07project.main.Information;
import com.example.b07project.R;
import java.util.List;

public class InformationAdapter extends ArrayAdapter<Information> {

    public InformationAdapter(Context context, int resource, List<Information> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_text, null);
        }

        Information info = getItem(position);

        if (info != null) {
            TextView subjectText = view.findViewById(R.id.item_text);
            if (subjectText != null) {
                if (info.subject != null) {
                    subjectText.setText(info.subject);
                } else if (info.content != null) {
                    subjectText.setText("Empty Subject");
                }
            }
        }
        return view;
    }
}
