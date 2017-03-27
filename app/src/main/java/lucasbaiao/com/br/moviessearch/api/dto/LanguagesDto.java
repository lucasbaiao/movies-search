package lucasbaiao.com.br.moviessearch.api.dto;

import com.google.gson.annotations.SerializedName;

public class LanguagesDto {

    @SerializedName("name")
    public
    String name;

    public LanguagesDto(String name) {
        this.name = name;
    }
}
