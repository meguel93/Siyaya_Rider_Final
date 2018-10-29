package com.example.valtron.siyaya_rider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.valtron.siyaya_rider.Common.Common;
import com.example.valtron.siyaya_rider.Model.Rider;
import com.example.valtron.siyaya_rider.Model.User;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;


public class DriverListFragment extends Fragment {

    DatabaseReference onlineRef, currentUserRef, counterRef;
    DataSnapshot dataSnapshot;
    FirebaseRecyclerAdapter<User, ListDriversViewHolder> adapter;

    private static final int MY_PERMISSION_REQUEST_CODE = 7192;
    private static final int PLAY_SERVICE_RES_REQUEST = 300193;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    RecyclerView listDrivers;
    RecyclerView.LayoutManager layoutManager;
    double lat, lng, value;

    private BroadcastReceiver mCancelBroadCast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Common.driverId = "";
            Common.isDriverFound = false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_list, container, false);

        listDrivers = view.findViewById(R.id.driver_recyclerView);
        listDrivers.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        listDrivers.setLayoutManager(layoutManager);

        currentUserRef = FirebaseDatabase.getInstance().getReference(Common.user_driver_tbl);

        counterRef = FirebaseDatabase.getInstance().getReference(Common.status_tbl);

        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User siyayaDriver = postSnapshot.getValue(User.class);
                    Log.d("LOG","" + siyayaDriver.getVehicle_reg() + " is " + siyayaDriver.getStatus());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateList();
        adapter.notifyDataSetChanged();

        return view;
    }

    private void updateList() {
        adapter = new FirebaseRecyclerAdapter<User, ListDriversViewHolder>(
                User.class,
                R.layout.adapter_view_layout,
                ListDriversViewHolder.class,
                counterRef
        ) {
            @Override
            protected void populateViewHolder(ListDriversViewHolder viewHolder, User model, int position) {
                viewHolder.vehicle_reg.setText(model.getVehicle_reg());
                if (model.getAvatarUrl() != null && !TextUtils.isEmpty(model.getAvatarUrl()))
                    Picasso.with(getContext())
                            .load(model.getAvatarUrl())
                            .into(viewHolder.dp);
                if (model.getStatus().equals("Online")) {
                    viewHolder.imgStatus.setImageResource(R.drawable.ic_online_24dp);
                    viewHolder.distance.setText("Distance: " + new DecimalFormat("#.#").format(value) + " km");
                }
            }
        };

        adapter.notifyDataSetChanged();
        listDrivers.setAdapter(adapter);
    }

    private void loadLocationForUser(String reg) {
        Query user_location = counterRef.orderByChild("vehicle_reg").equalTo(reg);

        user_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);

                    LatLng driver_location = new LatLng(Double.parseDouble(user.getLat()),
                                                            Double.parseDouble(user.getLng()));

                    Location current_user = new Location("");
                    current_user.setLatitude(lat);
                    current_user.setLongitude(lng);

                    Location driver = new Location("");
                    driver.setLatitude(lat);
                    driver.setLongitude(lng);

                    distance(current_user, driver);
                    value = current_user.distanceTo(driver);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private double distance(Location current_user, Location driver) {
        double eta = current_user.getLongitude() - driver.getLongitude();
        double dist = Math.sin(deg2rad(current_user.getLatitude()))
                        * Math.sin(deg2rad(driver.getLatitude()))
                        * Math.cos(deg2rad(current_user.getLatitude()))
                        * Math.cos(deg2rad(driver.getLatitude()))
                        * Math.cos(deg2rad(eta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return dist;
    }

    private double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

}
