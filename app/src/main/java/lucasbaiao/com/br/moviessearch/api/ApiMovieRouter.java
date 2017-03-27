package lucasbaiao.com.br.moviessearch.api;

import lucasbaiao.com.br.moviessearch.api.dto.FilmDetailDto;
import lucasbaiao.com.br.moviessearch.api.dto.FilmDto;
import lucasbaiao.com.br.moviessearch.api.dto.PagerDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiMovieRouter {

    @GET("/3/movie/popular?language=pt-BR")
    Call<PagerDto<FilmDto>> populars(@Query("page") int page);

    @GET("/3/movie/top_rated?language=pt-BR")
    Call<PagerDto<FilmDto>> topRated(@Query("page") int page);

    @GET("/3/movie/{movie_id}?language=pt-BR")
    Call<FilmDetailDto> movie(@Path("movie_id") int movieId);
}
