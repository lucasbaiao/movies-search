package lucasbaiao.com.br.moviessearch.api.dto;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FilmDetailDto {

    @SerializedName("adult")
    public boolean adult;
    @SerializedName("backdrop_path")
    public String backdropPath;
    @SerializedName("genres")
    public java.util.List<GenreDto> genres;
    @SerializedName("homepage")
    public String homepage;
    @SerializedName("id")
    public int id;
    @SerializedName("original_language")
    public String originalLanguage;
    @SerializedName("original_title")
    public String originalTitle;
    @SerializedName("overview")
    public String overview;
    @SerializedName("popularity")
    public double popularity;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("production_companies")
    public java.util.List<ProductionCompaniesDto> productionCompanies;
    @SerializedName("production_countries")
    public java.util.List<ProductionCountriesDto> productionCountries;
    @SerializedName("release_date")
    public String releaseDate;
    @SerializedName("spoken_languages")
    public java.util.List<LanguagesDto> spokenLanguages;
    @SerializedName("status")
    public String status;
    @SerializedName("tagline")
    public String tagline;
    @SerializedName("title")
    public String title;
    @SerializedName("vote_average")
    public double voteAverage;
    @SerializedName("vote_count")
    public int voteCount;
    @SerializedName("runtime")
    public int runtime;

    public FilmDetailDto(boolean adult, String backdropPath, List<GenreDto> genres, String homepage, int id, String originalLanguage, String originalTitle, String overview, double popularity, String posterPath, List<ProductionCompaniesDto> productionCompanies, List<ProductionCountriesDto> productionCountries, String releaseDate, List<LanguagesDto> spokenLanguages, String status, String tagline, String title, double voteAverage, int voteCount) {
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.productionCompanies = productionCompanies;
        this.productionCountries = productionCountries;
        this.releaseDate = releaseDate;
        this.spokenLanguages = spokenLanguages;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }
}
