package com.ajay.medicaluberuser;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String UID;

    DatabaseReference myRef;

    HashMap<String,Marker> markers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        UID=getIntent().getStringExtra("UID");
        markers = new HashMap<String,Marker>();



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef=database.getReference().child("db1").child("Online");


        myRef.addChildEventListener(new ChildEventListener() {
            public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
                Map<String, String> obj = (Map<String, String>) dataSnapshot.getValue();
                LatLng ln=new LatLng(Double.parseDouble(obj.get("latitude")),Double.parseDouble(obj.get("longitude")));
                MarkerOptions options=new MarkerOptions().title("Ambulance")
                        .position(ln);

                markers.put(dataSnapshot.getKey(),mMap.addMarker(options));
            }
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                markers.get(dataSnapshot.getKey()).remove();
                markers.remove(dataSnapshot.getKey());
                Map<String, String> obj = (Map<String, String>) dataSnapshot.getValue();
                LatLng ln=new LatLng(Double.parseDouble(obj.get("latitude")),Double.parseDouble(obj.get("longitude")));
                MarkerOptions options=new MarkerOptions().title("Ambulance")
                        .position(ln);

                markers.put(dataSnapshot.getKey(),mMap.addMarker(options));

            }
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                markers.get(dataSnapshot.getKey()).remove();
                markers.remove(dataSnapshot.getKey());
            }
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            public void onCancelled(DatabaseError firebaseError) { }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
