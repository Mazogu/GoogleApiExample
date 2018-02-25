package com.example.micha.locationandmaps;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.micha.locationandmaps.model.geocode.GeoCode;
import com.example.micha.locationandmaps.utils.RetrofitHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GeoActivity extends BaseActivity {

    Location currentLocation;
    TextView locationText, address, geoCodeText;
    public static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup view = findViewById(R.id.content);
        getLayoutInflater().inflate(R.layout.activity_geo, view);
        geoCodeText = findViewById(R.id.generatedLocation);
        locationText = findViewById(R.id.currentLocation);
        address = findViewById(R.id.addressView);
        getLocation();
    }


    @SuppressLint("MissingPermission")
    public void getLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentLocation = location;
                String latLong = location.getLatitude()+ "," + location.getLongitude();
                locationText.setText(latLong);
            }
        });
    }

    public void getAddress(View view) {
        if(currentLocation != null){
            String latLong = currentLocation.getLatitude()+ "," + currentLocation.getLongitude();
            RetrofitHelper.getReverseResults(BASE_URL,latLong,"AIzaSyBkQo2LBh8jGVIqtvT3e8JQke8nTvyKZbM")
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<GeoCode>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(GeoCode geoCode) {
                            address.setText(geoCode.getResults().get(0).getFormattedAddress());
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void getLocation(View view) {
        String street = address.getText().toString().replace(" ","+");
        if(!street.equals("")){
            RetrofitHelper.getResults(BASE_URL, street, "AIzaSyBkQo2LBh8jGVIqtvT3e8JQke8nTvyKZbM")
                    .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                    .subscribe(new Observer<GeoCode>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(GeoCode geoCode) {
                            com.example.micha.locationandmaps.model.geocode.Location local = geoCode.getResults()
                                    .get(0).getGeometry().getLocation();
                            String coordinates = local.getLat() + "," + local.getLng();
                            geoCodeText.setText(coordinates);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void geoCodeClass(View view) {
        String street = address.getText().toString();
        if(!street.equals("")){
            Geocoder geocoder = new Geocoder(this);
        }

    }

    public void reservseGeoCodeClass(View view) {
        if (currentLocation!=null){
            Geocoder coder = new Geocoder(this);
            try {
               List<Address> stuff = coder.getFromLocation(currentLocation.getLatitude(),currentLocation.getLongitude(),1);
               address.setText(stuff.get(0).getAddressLine(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
