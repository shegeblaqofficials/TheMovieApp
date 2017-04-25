package com.example.themovieapp;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetails extends AppCompatActivity {

    private String Backdrop;
    private String Title;
    private String ReleaseDate;
    private double Rating;
    private String Synopsis;

    @BindView(R.id.movie_title)TextView Title_tv;
    @BindView(R.id.releaseDate)TextView Release_tv;
    @BindView(R.id.rating)TextView Rating_tv;
    @BindView(R.id.synopsis)TextView Synopsis_tv;
    @BindView(R.id.backdrop)ImageView Backdrop_img;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout), Backdrop);
        supportPostponeEnterTransition();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(Title);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

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
        SetData(Title,ReleaseDate,Rating,Synopsis);



    }

    private void SetData(String Title, String ReleaseDate, double Rating, String Synopsis)
    {
        //set the back drop image with picasso
        Picasso.with(this)
                .load(Backdrop)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)
                .into(Backdrop_img);

        ///set other text view
        Title_tv.setText(Title);
        Release_tv.setText(ReleaseDate);
        Rating_tv.setText(""+Rating);
        Synopsis_tv.setText(Synopsis);

    }

}
