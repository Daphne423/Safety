package com.daphne.safety;

import static android.net.wifi.WifiConfiguration.Status.strings;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class LocationActivity extends AppCompatActivity {
    //Initialize variable
    Spinner spin;
    Button bt;
    SupportMapFragment supportMapFragment;
    GoogleMap g_map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //Assign variable
        spin  = findViewById(R.id.spin);
        bt = findViewById(R.id.bt);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
//        //get the spinner from the xml.
//        Spinner dropdown = findViewById(R.id.spinner1);
////create a list of items for the spinner.
//        String[] items = new String[]{"1", "2", "three"};
////create an adapter to describe how the items are displayed, adapters are used in several places in android.
////There are multiple variations of this, but this is the basic variant.
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
////set the spinners adapter to the previously created one.
//        dropdown.setAdapter(adapter);

        //Initialize array of place type
        String[] placeTypeList = {"hospital","police station"};
        //Initialize array of place name
        //String[] placeNameList = {"Hospital,Police Station"};

        //Set Adapter on spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
//        spin.setAdapter(new ArrayAdapter<> (LocationActivity.this, R.array.dropdown, android.R.layout.simple_spinner_dropdown_item));

//        //Set the spinners adapter to the previously created one
//      dropdown.setAdapter();

        //Initialize fused location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Check permission
        if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When permission granted
            //Call Method
            getCurrentLocation();

        } else {
            //When permission denied
            //Request permission
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get selected position of spinner
                int i = spin.getSelectedItemPosition();
                //Initialize url
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + //url
                        "?location=" + currentLat + "," + currentLong + //Location latitude and longitude
                        "&radius=5000" + //Nearby radius
                        "&types" + placeTypeList[i] + //Place Type
                        "&key=" + getResources().getString(R.string.map_key); //Google map key

               // if (String[] placeTypeList = {"hospital","police station"}){
                    //placeTypeList.add();
                //}
//                //  if (shopCity.equals(myCity)){
//                shopsList.add(modelShop);
//                // }

                //Execute place tsk method to download json data
                new PlaceTask().execute(url);

            }
        });
    }

    private void getCurrentLocation() {
        //Initialize task location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                //When success
                if (location != null) {
                    //When location is not equal to null
                    //Get current Latitude
                    currentLat = location.getLatitude();
                    //Get current Longitude
                    currentLong = location.getLongitude();
                    //Sync Map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            //When map is ready
                            g_map = googleMap;
                            //Zoom current location on map
                            g_map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLat, currentLong), 10
                            ));
                        }

                    });
                }


            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //When permission granted
                //Call method
                getCurrentLocation();
            }
        }
    }

    private class PlaceTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                //Initialize data
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;

        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            //Execute parser task
            new ParserTask().execute(s);
        }
    }


        //        @Override
//        protected String doInBackground(String... strings) {
//            return null;
//        }
//    }
        private String downloadUrl(String string) throws IOException {
            //Initialize url
            URL url = new URL(string);
            //Initialize connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //Connect connection
            connection.connect();
            //Initialize input stream
            InputStream stream = connection.getInputStream();
            //Initialize buffer reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            //Initialize string builder
            StringBuilder builder = new StringBuilder();
            //Initialize string variable
            String line = "";
            //Use while loop
            while ((line = reader.readLine()) != null) {
                //Append line
                builder.append(line);
            }
            //Get append data
            String data = builder.toString();
            //Close reader
            reader.close();
            //Return data
            return data;


        }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //Create json parser class
            JsonParser jsonParser = new JsonParser();
            //Initialize hash map List
            List<HashMap<String,String>> mapList = null;
            JSONObject object = null;
            try {
                //Initialize json Object
                 object = new JSONObject(strings[0]);
                 //Parse json object
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Return map list
            return mapList;
        }
        @Override
        protected void onPostExecute(List<HashMap<String,String>>hashmaps){
            //Clear map
            g_map.clear();
            //Use for loop
            for(int i =0; i<hashmaps.size(); i++){
                //Initialize hash map
                HashMap<String,String> hashMapList = hashmaps.get(i);
                //Get Latitude
                double lat = Double.parseDouble(hashMapList.get("lat"));
                //Get longitude
                double lng = Double.parseDouble(hashMapList.get("lng"));
                //Get name
                String name = hashMapList.get("name");
                //Concat Latitude and Longitude
                LatLng latLng = new LatLng(lat,lng);
                //Initialize marker options
                MarkerOptions options = new MarkerOptions();
                //Set Position
                options.position(latLng);
                //Set title
                options.title(name);
                //Add marker on map
                g_map.addMarker(options);



            }
        }
    }
}
//    public void saveLocation(View view) {
//        if (lastKnownLocation != null) {
//            double lng = lastKnownLocation.getLongitude();
//            double lat = lastKnownLocation.getLatitude();
//
//            Map<String, Object> loc = new HashMap<>();
//            loc.put("Long", lng);
//            loc.put("Lat", lat);
//
//            String UserID = fAuth.getCurrentUser().getUid();
//            store.collection("Profile Data").document(UserID).update(loc);
//
//            Toast.makeText(BakerMapActivity.this, "Your location has been saved successfully", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(BakerMapActivity.this, BakerProfile.class);
//            startActivity(intent);
//        }else{
//            Toast.makeText(BakerMapActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
//

//        }


