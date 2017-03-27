package lucasbaiao.com.br.moviessearch.database.entity;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lucasbaiao.com.br.moviessearch.api.dto.FilmDetailDto;
import lucasbaiao.com.br.moviessearch.api.dto.GenreDto;
import lucasbaiao.com.br.moviessearch.api.dto.LanguagesDto;
import lucasbaiao.com.br.moviessearch.api.dto.ProductionCompaniesDto;
import lucasbaiao.com.br.moviessearch.api.dto.ProductionCountriesDto;

public class FilmDetail {

    private double popularity;
    private int voteCount;
    private double voteAverage;
    private String status;
    private String tagline;
    private int runtime;
    private String homepage;
    private String overview;
    private java.util.List<String > genres;
    private java.util.List<String> productionCompanies;
    private java.util.List<String> productionCountries;
    private java.util.List<String> spokenLanguages;


    public FilmDetail(double popularity, int voteCount, double voteAverage, String status, String tagline, int runtime, String homepage, String overview, List<String> genres, List<String> productionCompanies, List<String> productionCountries, List<String> spokenLanguages) {
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.status = status;
        this.tagline = tagline;
        this.runtime = runtime;
        this.homepage = homepage;
        this.overview = overview;
        this.genres = genres;
        this.productionCompanies = productionCompanies;
        this.productionCountries = productionCountries;
        this.spokenLanguages = spokenLanguages;
    }

    public FilmDetail(Builder builder) {
        this.popularity = builder.popularity;
        this.voteCount = builder.voteCount;
        this.voteAverage = builder.voteAverage;
        this.status = builder.status;
        this.tagline = builder.tagline;
        this.runtime = builder.runtime;
        this.homepage = builder.homepage;
        this.overview = builder.overview;
        this.genres = builder.genres;
        this.productionCompanies = builder.productionCompanies;
        this.productionCountries = builder.productionCountries;
        this.spokenLanguages = builder.spokenLanguages;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getOverview() {
        return overview;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getProductionCompanies() {
        return productionCompanies;
    }

    public List<String> getProductionCountries() {
        return productionCountries;
    }

    public List<String> getSpokenLanguages() {
        return spokenLanguages;
    }

    public String convertRuntime() {
        String result = "";
        try {
            double fraction = this.runtime / 60.0;
            long iPart = (long) fraction;
            double fPart = fraction - iPart;
            int minutes = (int)(fPart * 60.0);
            String sMinutes = minutes > 0 ? String.format(Locale.getDefault(), ", %d minutos", minutes) : "";
            result = String.format(Locale.getDefault(), "%s horas%s", iPart, sMinutes);
        } catch (Exception ignored){
        }
        return result;
    }

    public static class Builder {

        private double popularity;
        private int voteCount;
        private double voteAverage;
        private String status;
        private String tagline;
        private int runtime;
        private String homepage;
        private String overview;
        private java.util.List<String > genres;
        private java.util.List<String> productionCompanies;
        private java.util.List<String> productionCountries;
        private java.util.List<String> spokenLanguages;


        public FilmDetail build() {
            return new FilmDetail(this);
        }

        public Builder fromDto(FilmDetailDto detail) {
            this.popularity = detail.popularity;
            this.voteCount = detail.voteCount;
            this.voteAverage = detail.voteAverage;
            this.status = detail.status;
            this.tagline = detail.tagline;
            this.runtime = detail.runtime;
            this.homepage = detail.homepage;
            this.overview = detail.overview;

            this.genres = new ArrayList<>();
            for (GenreDto genre : detail.genres) {
                this.genres.add(genre.name);
            }

            this.productionCompanies = new ArrayList<>();
            for (ProductionCompaniesDto companies : detail.productionCompanies) {
                this.productionCompanies.add(companies.name);
            }

            this.productionCountries = new ArrayList<>();
            for (ProductionCountriesDto country : detail.productionCountries) {
                this.productionCountries.add(country.name);
            }

            this.spokenLanguages = new ArrayList<>();
            for (LanguagesDto language : detail.spokenLanguages) {
                this.spokenLanguages.add(language.name);
            }


            return this;
        }
    }
}
