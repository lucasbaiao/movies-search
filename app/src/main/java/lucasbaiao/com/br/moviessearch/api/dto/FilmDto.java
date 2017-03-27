package lucasbaiao.com.br.moviessearch.api.dto;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FilmDto {

    @SerializedName("id")
    public long id;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("backdrop_path")
    public String backdropPath;
    @SerializedName("adult")
    public boolean adult;
    @SerializedName("overview")
    public String overview;
    /**
     * Date format yyyy-MM-dd
     */
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("original_language")
    public String originalLanguage;
    @SerializedName("title")
    public String title;
    @SerializedName("popularity")
    public String popularity;
    @SerializedName("vote_count")
    public String voteCount;
    @SerializedName("voteAverage")
    public String voteAverage;

    public FilmDto(long id, String posterPath, String backdropPath, boolean adult, String overview, String releaseDate, String originalTitle, String originalLanguage, String title, String popularity, String voteCount, String voteAverage) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
    }

    public String toDateString() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            java.util.Date date = sdf.parse(this.releaseDate);

            sdf = new SimpleDateFormat("MM/yyyy", Locale.getDefault());
            return sdf.format(date);
        } catch (Exception e) {
            return this.releaseDate;
        }
    }
}