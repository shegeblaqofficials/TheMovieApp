package com.example.themovieapp;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {

    TextView Title_tv, Release_tv, Rating_tv, Synopsis_tv, Backdrop_tv;
    ImageView Backdrop_img;
    private String Backdrop;
    private String Title;
    private String ReleaseDate;
    private double Rating;
    private String Synopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ///find the view
        Title_tv = (TextView) findViewById(R.id.title);
        Release_tv = (TextView) findViewById(R.id.releaseDate);
        Rating_tv = (TextView) findViewById(R.id.rating);
        Synopsis_tv = (TextView) findViewById(R.id.synopsis);
        Backdrop_tv = (TextView) findViewById(R.id.backdrop_title);

        Backdrop_img = (ImageView) findViewById(R.id.backdrop);

        initCollapsingToolbar();

        ///get the sent in data
        Intent SentData = getIntent();

        if(SentData.hasExtra("Backdrop")) {
            Backdrop = SentData.getStringExtra("Backdrop");
        }
        if(SentData.hasExtra("Title")) {
           Title = SentData.getStringExtra("Title");
        }
        if(SentData.hasExtra("ReleaseDate")) {
            ReleaseDate = SentData.getStringExtra("ReleaseDate");
        }
        if(SentData.hasExtra("Rating")){
            Rating = SentData.getDoubleExtra("Rating",0);
        }
        if(SentData.hasExtra("Synopsis")) {
            Synopsis = SentData.getStringExtra("Synopsis");
        }

        ///Pass the data into view
        SetData(Backdrop,Title,ReleaseDate,Rating,Synopsis);

    }

    private void SetData(String Backdrop,String Title, String ReleaseDate, double Rating, String Synopsis)
    {
        //set the back drop image with picasso
        Picasso.with(this)
                .load(Backdrop)
                .placeholder(R.drawable.placeholder)
                .into(Backdrop_img);

        ///set other text view
        Backdrop_tv.setText(Title);
        Title_tv.setText(Title);
        Release_tv.setText(ReleaseDate);
        Rating_tv.setText(""+Rating);
        Synopsis_tv.setText(Synopsis);

    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
}
