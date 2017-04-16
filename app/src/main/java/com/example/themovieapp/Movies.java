package com.example.themovieapp;

/**
 * Created by BLAQ on 4/15/2017.
 */

public class Movies {

    private String title,synopsis,releaseDate,poster,backdrop;
    private int movieID;
    private double rating;

    public Movies() {

    }

    public Movies(String title, String synopsis, String poster, String releaseDate, double rating, int movieID, String backdrop) {
        this.title = title;
        this.synopsis = synopsis;
        this.poster=poster;
        this.releaseDate = releaseDate;
        this.rating=rating;
        this.movieID=movieID;
        this.backdrop=backdrop;
    }

    public Movies(String poster, int movieID) {
        this.poster = poster;
        this.movieID=movieID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }
}
