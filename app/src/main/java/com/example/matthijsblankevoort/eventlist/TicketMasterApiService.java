package com.example.matthijsblankevoort.eventlist;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit userguide at
 * https://square.github.io/retrofit/
 * More usage information at
 * https://guides.codepath.com/android/consuming-apis-with-retrofit
 */

public interface TicketMasterApiService {

    String BASE_URL = "https://app.ticketmaster.com";

    /**
     * Create a retrofit client.
     */
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * The string in the GET annotation is added to the BASE_URL.
     * It simply represents the designed layout of the URLs of the numbersapi.com website,
     * refer to it in a browser and try. This request will deliver a json stream based on month and
     * day of month. It will be put in a DayQuoteTime object by Retrofit.
     */

        @GET("/discovery/v2/events.json?segmentId=KZFzniwnSyZfZ7v7nJ&radius=20&unit=km&countryCode=NL&size=50&sort=date,asc")
    /**
     * "DayQuoteTime" is the name of the helper class just defined, defining the datamodel, and given as argument.
     * "getTodaysQuote" is the name of the symbol get method. It can be chosen at wish, as long as it is invoked
     * with the same name.
     */
    Call<Example> getNearbyEvents(@Query(value = "latlong") String latlong, @Query(value = "apikey") String API_KEY);

    @GET("/discovery/v2/events/{id}")
    Call<Event> getEventById(@Path(value = "id") String id, @Query(value = "apikey") String API_KEY);
}