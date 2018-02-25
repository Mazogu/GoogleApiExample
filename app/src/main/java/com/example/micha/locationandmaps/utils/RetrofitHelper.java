package com.example.micha.locationandmaps.utils;

import com.example.micha.locationandmaps.model.geocode.GeoCode;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by micha on 2/25/2018.
 */

public class RetrofitHelper {
    public static class Factory{
        public static Retrofit getRetrofit(String url){
            return new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
        }
    }

    public static Observable<GeoCode> getReverseResults(String url, String latLong, String key){
        RetrofitService service = Factory.getRetrofit(url).create(RetrofitService.class);
        return service.getReverseResults(latLong, key);
    }

    public static Observable<GeoCode> getResults(String url, String address, String key){
        RetrofitService service = Factory.getRetrofit(url).create(RetrofitService.class);
        return service.getResults(address, key);
    }



    public interface RetrofitService{
        @GET("json")
        Observable<GeoCode> getReverseResults(@Query("latlng") String latLng, @Query("key") String key);

        @GET("json")
        Observable<GeoCode> getResults(@Query("address") String address, @Query("key") String key);
    }
}
