package com.example.valtron.siyaya_rider;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.valtron.siyaya_rider.Model.Rider;
import com.firebase.ui.database.FirebaseListAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListDriversViewHolder extends RecyclerView.ViewHolder {

    public TextView vehicle_reg, distance;
    ImageView imgStatus;
    CircleImageView dp;

    public ListDriversViewHolder(View itemView) {
        super(itemView);

        vehicle_reg = itemView.findViewById(R.id.text_regnum_element);
        distance = itemView.findViewById(R.id.text_distance);
        imgStatus = itemView.findViewById(R.id.img_status);
        dp = itemView.findViewById(R.id.profile_pic);
    }
}
