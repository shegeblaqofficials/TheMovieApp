package com.example.themovieapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

import Utilities.AppConfig;
import butterknife.BindView;

public class Review extends AppCompatActivity {

    @BindView(R.id.movie_title)TextView Review_tv;

    private static String SAVE_INSTANCE_KEY="key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        //check our onsavestance
        if(savedInstanceState != null)
        {
            if(savedInstanceState.containsKey(SAVE_INSTANCE_KEY)){
                String Review = savedInstanceState.getString(SAVE_INSTANCE_KEY);
                ///set our text
                Review_tv.setText(Review);
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        ///build url
        Uri builtUri = Uri.parse(AppConfig.BASE_URL).buildUpon()
                .appendPath(AppConfig.REVIEW_QUERY)
                .appendPath(AppConfig.API_KEY)
                .build();

        ///convert to string
        URL url =null;
        try {
            url= new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        outState.putString(SAVE_INSTANCE_KEY, FetchReviw(url));
    }

    private String FetchReviw(URL url)
    {
        String Review=url.toString();

        return Review;
    }
}
