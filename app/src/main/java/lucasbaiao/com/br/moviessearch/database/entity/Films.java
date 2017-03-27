package lucasbaiao.com.br.moviessearch.database.entity;

import java.text.SimpleDateFormat;
import java.util.Locale;

import lucasbaiao.com.br.moviessearch.api.dto.FilmDto;

public class Films extends BaseEntity {

    private long id;
    private String posterPath;
    private String backdropPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private FilmDetail filmDetail;
    private boolean favorite;

    public Films(long id, String posterPath, String backdropPath, boolean adult, String overview, String releaseDate, String originalTitle, String originalLanguage, String title, FilmDetail filmDetail, boolean favorite) {
        super(String.valueOf(id));
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.filmDetail = filmDetail;
        this.favorite = favorite;
    }

    public Films(Builder builder) {
        super(String.valueOf(builder.id));
        this.id = builder.id;
        this.posterPath = builder.posterPath;
        this.backdropPath = builder.backdropPath;
        this.adult = builder.adult;
        this.overview = builder.overview;
        this.releaseDate = builder.releaseDate;
        this.originalTitle = builder.originalTitle;
        this.originalLanguage = builder.originalLanguage;
        this.title = builder.title;
        this.favorite = builder.favorite;
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

    public void setFilmDetail(FilmDetail filmDetail) {
        this.filmDetail = filmDetail;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public long getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public FilmDetail getFilmDetail() {
        return filmDetail;
    }

    public static class Builder {
        private long id;
        private String posterPath;
        private String backdropPath;
        private boolean adult;
        private String overview;
        private String releaseDate;
        private String originalTitle;
        private String originalLanguage;
        private String title;
        private boolean favorite;

        public Films build() {
            return new Films(this);
        }

        public Builder favorite(boolean favorite) {
            this.favorite = favorite;
            return this;
        }

        public Builder fromDto(FilmDto dto) {
            this.id = dto.id;
            this.posterPath = dto.posterPath;
            this.backdropPath = dto.backdropPath;
            this.adult = dto.adult;
            this.overview = dto.overview;
            this.releaseDate = dto.releaseDate;
            this.originalTitle = dto.originalTitle;
            this.originalLanguage = dto.originalLanguage;
            this.title = dto.title;

            return this;
        }
    }
}
