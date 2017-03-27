package lucasbaiao.com.br.moviessearch.api.dto;

import com.google.gson.annotations.SerializedName;

public class GenreDto {

    @SerializedName("name")
    public String name;

    public GenreDto(String name) {
        this.name = name;
    }
}
