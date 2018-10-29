package com.example.valtron.siyaya_rider;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ExpandableListViewAdaptor extends BaseExpandableListAdapter {
    String[] routes = {"Town","Summerstrand","Forrest Hill","Central","Greenacres"};
    String [][] details = {
            {"Price: R12.50","Operation Times:06:00 - 19:00", ""},
            {"Price: R12.50","Operation Times:06:00 - 19:00", ""},
            {"Price: R12.50","Operation Times:06:00 - 19:00", ""},
            {"Price: R12.50","Operation Times:06:00 - 19:00", ""},
            {"Price: R12.50","Operation Times:06:00 - 19:00", ""},
};

    Context context;

    public ExpandableListViewAdaptor(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return routes.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return details[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return routes[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return details[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(routes[groupPosition]);
        textView.setPadding(100, 0, 0, 30);
        textView.setTextSize(30);
        return textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final TextView textView = new TextView(context);
        textView.setText(details[groupPosition][childPosition]);
        textView.setPadding(100, 0, 0, 10);
        textView.setTextSize(20);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, textView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
