package com.example.matthijsblankevoort.eventlist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Event> eventList = new ArrayList<>();
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new EventListAdapter(eventList, Glide.with(this), getContext());
        mRecyclerView.setAdapter(mAdapter);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(getActivity(),

                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,

                            Manifest.permission.ACCESS_COARSE_LOCATION}, 123);


        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                requestData(location.getLatitude(), location.getLongitude());
                            }
                        }
                    });
        }


        return rootView;
    }

    private void requestData(double latitude, double longitude)
    {
        TicketMasterApiService service = TicketMasterApiService.retrofit.create(TicketMasterApiService.class);

        /**
         * Make an a-synchronous call by enqueing and definition of callbacks.
         */
        String latlong = Double.toString(latitude) + ',' + Double.toString(longitude);
        Call<Example> call = service.getNearbyEvents(latlong,MainActivity.API_KEY);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call <Example> call, Response<Example> response) {
                if(response.body().getEmbedded() != null) {
                    List<Event> allNearbyEvents = response.body().getEmbedded().getEvents();
                    eventList.clear();
                    eventList.addAll(allNearbyEvents);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("error",t.toString());
            }
        });

    }
}
