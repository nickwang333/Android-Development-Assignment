package edu.northeastern.numad24sp_zhuofengwang;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class LocationActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private TextView latitudeValue;
    private TextView longitudeValue;
    private TextView distanceValue;
    private double prevLong = -1, prevLat = -1, totalDistance = -1;
    private List<String> enabledProviders;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Store the state of the check box
        outState.putDouble("total_distance", totalDistance);
        outState.putDouble("longitude", prevLong);
        outState.putDouble("latitude", prevLat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String provider = LocationManager.GPS_PROVIDER;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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
        locationManager.requestLocationUpdates(provider, 1000, 1, locationListener);

        latitudeValue = (TextView) findViewById(R.id.latitudeValue);
        longitudeValue = (TextView) findViewById(R.id.longitudeValue);
        distanceValue = (TextView) findViewById(R.id.totalDistanceTravelValue);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        if (savedInstanceState != null) {

            totalDistance = savedInstanceState.getDouble("total_distance", -1);
            prevLat = savedInstanceState.getDouble("latitude");
            prevLong = savedInstanceState.getDouble("longitude");
            if (totalDistance != -1) {
                distanceValue = (TextView) findViewById(R.id.totalDistanceTravelValue);
                distanceValue.setText(String.valueOf(totalDistance) + " Meters");
            }

        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.v("Permission Denied", "1");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    101);
            return;
        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new AlertDialog.Builder(LocationActivity.this)
                        .setMessage("The total distance travel will lost after the exit, are you sure you want to exit?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            locationManager.removeUpdates(locationListener);
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // Use the location object to get latitude and longitude
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            if(totalDistance == -1){
                prevLong = longitude;
                prevLat = latitude;
                totalDistance = 0;
            }
            else {
                double distance = getDistanceFromLatLonInMeter(prevLat, prevLong, latitude, longitude);
                totalDistance += distance;
                Log.v(String.valueOf(prevLat), String.valueOf(latitude));
                Log.v("location update", "1");
                distanceValue.setText(String.valueOf(totalDistance) + " Meters");
                prevLong = longitude;
                prevLat = latitude;
            }
            latitudeValue.setText(String.valueOf(location.getLatitude()));
            longitudeValue.setText(String.valueOf(location.getLongitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

    double getDistanceFromLatLonInMeter(double lat1, double lon1, double lat2,double lon2) {
        double R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c * 1000; // Distance in meter
        return d;
    }

    double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }

    public void resetDistanceClick(View view)
    {
        distanceValue.setText("0 Meters");
        totalDistance = 0;
    }
}
