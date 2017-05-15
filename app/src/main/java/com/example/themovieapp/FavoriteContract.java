package com.example.themovieapp;

import android.provider.BaseColumns;

/**
 * Created by BLAQ on 5/13/2017.
 */

public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns {

        public static final String MOVIE_TABLE_NAME="MovieTable";
        public static final String MOVIE_ID="MovieId";
        public  static final String MOVIE_TITLE="MovieTitle";
        public  static final String MOVIE_POSTER="MoviePoster";
        public  static final String MOVIE_RELEASE_DATE="MovieReleaseDate";
        public  static final String MOVIE_SYNOPOSIS="MovieSynoposis";
        public  static final String MOVIE_RATING="MovieRating";
        public  static final String MOVIE_BACKDROP="MovieBackdrop";

        public static final String TRAILER_TABLE_NAME="TrailerTable";
        public static final String TRAILER_MOVIE_ID="TrailerMovieId";
        public  static final String TRAILER_KEY="TrailerKey";

        public static final String REVIEW_TABLE_NAME="ReviewTable";
        public static final String REVIEW_MOVIE_ID="ReviewMovieId";
        public static final String RREVIEW_AUTHOR="ReviewAuthor";
        public  static final String REVIEW_CONTENT="ReviewContent";

    }
}
