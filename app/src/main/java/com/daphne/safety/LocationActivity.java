package com.daphne.safety;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class LocationActivity extends AppCompatActivity{
    Button hos,Police;
   String x;
   String y;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        hos = findViewById(R.id.Hos);
        Police = findViewById(R.id.Police);




        hos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = "https://maps.google.com/maps?saddr=" + "my location" +  "&daddr=" + "Nearest Hospital" ;
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse ( address ));
                startActivity ( intent );


            }
        });
                 Police.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String address = "https://maps.google.com/maps?saddr="+ "my location" +  "&daddr=" + "Nearest Police Station" ;
                    Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse ( address ));
                    startActivity ( intent );


                }
        });




}
}


//String address = "https://maps.google.com/maps?saddr="+ "my location" +  "&daddr=" + shopAddress ;
//        Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse ( address ));
//        startActivity ( intent );

//@SuppressWarnings("deprecation")
//public class LocationActivity extends FragmentActivity implements
//        OnMapReadyCallback,
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        LocationListener{
//    private GoogleMap mMap;
//     GoogleApiClient googleApiClient;
//    private Marker currentUserLocationMarker;
//    private static final int Request_User_Location_Code = 99;
//    private double latitude,longitude;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_location);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkUserLocationPermission();
//        }
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        assert mapFragment != null;
//        mapFragment.getMapAsync(this);
//    }
//
//    @SuppressLint("NonConstantResourceId")
//    public void onClick(View v) {
//
//        String hospital = "hospital", police = "Police Station";
//        Object[] transferData = new Object[2];
//        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
////        getNearbyPlaces.execute(transferData);
//
//
//        switch (v.getId()) {
//            case R.id.search_address:
//                EditText addressfield = findViewById(R.id.location_search);
//                String address = addressfield.getText().toString();
//
//                List<Address> addressList;
//
//                MarkerOptions userMarkerOptions = new MarkerOptions();
//
//                if (!TextUtils.isEmpty(address)) {
//                    Geocoder geocoder = new Geocoder(this);
//                    try {
//                        addressList = geocoder.getFromLocationName(address, 6);
//
//                        if (addressList != null) {
//                            for (int i = 0; i < addressList.size(); i++) {
//                                Address userAddress = addressList.get(i);
//                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());
////                                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.gps);
//
//                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userAddress.getLatitude(), userAddress.getLongitude()),15.0f));
//
//                                MarkerOptions markerOptions = new MarkerOptions();
//                                userMarkerOptions.position(latLng);
//                                userMarkerOptions.title(address);
////                                userMarkerOptions.icon(icon);
//                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
//                                mMap.addMarker(userMarkerOptions);
//
//
////                                userMarkerOptions.position(latLng);
////                                userMarkerOptions.title(address);
//
////                                mMap.addMarker(userMarkerOptions);
////
////                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
////                                mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
//                            }
//                        } else {
//                            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(this, "Please write location name...", Toast.LENGTH_SHORT).show();
//                }
//                break;
//
//            case R.id.hospital_nearby:
//                mMap.clear();
//                String url = getUrl(latitude,longitude,hospital);
//                transferData[0] = mMap;
//                transferData[1] = url;
//                getNearbyPlaces.execute(transferData);
//                Toast.makeText(this, "Searching for nearby Hospitals", Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "Showing nearby Hospitals", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.police_nearby:
//                mMap.clear();
//                url = getUrl(latitude,longitude,police);
//                transferData[0] = mMap;
//                transferData[1] = url;
//                getNearbyPlaces.execute(transferData);
//                Toast.makeText(this, "Searching for nearby Police Stations", Toast.LENGTH_SHORT).show();
//                Toast.makeText(this, "Showing nearby Police Stations", Toast.LENGTH_SHORT).show();
//                break;
//
//        }
//    }
//
//    private String getUrl(double latitude,double longitude,String nearbyPlace){
//        StringBuilder googleURL= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
//        googleURL.append("location=").append(latitude).append(",").append(longitude);
//        int proximityRadius = 10000;
//        googleURL.append("&radius=").append(proximityRadius);
//        googleURL.append("&type=").append(nearbyPlace);
//        googleURL.append("&sensor=true");
//        googleURL.append("&key=").append(getResources().getString(R.string.google_api_key));
//
//        Log.d("GoogleMapsActivity","url = "+ googleURL);
//
//
//
//        return googleURL.toString();
//    }
//
//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        mMap = googleMap;
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            buildGoogleApiClient();
//            mMap.setMyLocationEnabled(true);
//        }
//    }
//
//    public void checkUserLocationPermission() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == Request_User_Location_Code) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    if (googleApiClient == null) {
//                        buildGoogleApiClient();
//                    }
//                    mMap.setMyLocationEnabled(true);
//                }
//            } else {
//                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    protected synchronized void buildGoogleApiClient() {
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        googleApiClient.connect();
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
////        currentUserLocationMarker=location;
//
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();
//
//        if (currentUserLocationMarker != null) {
//            currentUserLocationMarker.remove();
//
//        }
//
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
////        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.navigation);
//
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),15.0f));
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("User current location");
////        markerOptions.icon(icon);
//        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//
//
//        currentUserLocationMarker = mMap.addMarker(markerOptions);
//
//
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
////        mMap.animateCamera(CameraUpdateFactory.zoomBy(12));
//
//        if (googleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
//        }
//
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        LocationRequest locationRequest = new LocationRequest();
//        locationRequest.setInterval(1100);
//        locationRequest.setFastestInterval(1100);
//        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
//        }
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//}
