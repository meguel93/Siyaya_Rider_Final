package com.example.valtron.siyaya_rider;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.valtron.siyaya_rider.Common.Common.current_user;


public class RouteInfoFragment extends Fragment {
    TextView title_textView, details_textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_info, container, false);
        title_textView = view.findViewById(R.id.rout_info_title);
        title_textView.setText(current_user.getRoute());
        details_textView = view.findViewById(R.id.route_info_body);
        switch (current_user.getRoute()) {
            case "Town":
                details_textView.setText(getString(R.string.townDes));
                break;
            case "Central":
                details_textView.setText(getString(R.string.centralDes));
                break;
            case "Summerstrand":
                details_textView.setText(getString(R.string.summerDes));
                break;
            case "Forrest Hill":
                details_textView.setText(getString(R.string.ForrestDes));
                break;
            case "Greenacres":
                details_textView.setText(getString(R.string.greenDes));
                break;
        }
        return view;
    }
}
