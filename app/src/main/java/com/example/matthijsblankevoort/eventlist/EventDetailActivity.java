package com.example.matthijsblankevoort.eventlist;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView detailName;
    private TextView priceText;
    private TextView venueText;
    private FloatingActionButton saveButton;
    private Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        imageView = findViewById(R.id.detailImage);
        detailName = findViewById(R.id.detailName);
        priceText = findViewById(R.id.priceText);
        venueText = findViewById(R.id.venueText);
        saveButton = findViewById(R.id.floatingActionButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            EventEntity event = new EventEntity();

            event.setName(currentEvent.getName());
            event.setDate(LocalDate.parse(currentEvent.getDates().getStart().getLocalDate()).format(MainActivity.DATE_FORMAT));
            event.setImageUrl(currentEvent.getImages().get(0).getUrl());

            MainActivity.bucketListViewModel.insert(event);

            Context context = getApplicationContext();
            CharSequence text = currentEvent.getName() + " added to your list!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            finish();
            }
        });
        requestData();
    }

    private void requestData() {
        TicketMasterApiService service = TicketMasterApiService.retrofit.create(TicketMasterApiService.class);

        /**
         * Make an a-synchronous call by enqueing and definition of callbacks.
         */
        Call<Event> call = service.getEventById(getIntent().getStringExtra("id"), MainActivity.API_KEY);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call <Event> call, Response<Event> response) {
                Event event = response.body();
                currentEvent = event;
                Glide.with(EventDetailActivity.this).load(event.getImages().get(0).getUrl()).into(imageView);
                detailName.setText("EVENT " + event.getName());
                venueText.setText("VENUE " + event.getEmbedded().getVenues().get(0).getName());
                System.out.println(currentEvent.getPriceRanges().size());
                if (currentEvent.getPriceRanges() != null && currentEvent.getPriceRanges().get(0).getMin() != null) {
                    priceText.setText("Price range: €" + Double.toString(currentEvent.getPriceRanges().get(0).getMin()) + " - €" + Double.toString(currentEvent.getPriceRanges().get(currentEvent.getPriceRanges().size() - 1).getMax()));
                } else {
                    priceText.setText("Price range: UNKNOWN");
                }

            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.d("error",t.toString());
            }
        });
    }
}
