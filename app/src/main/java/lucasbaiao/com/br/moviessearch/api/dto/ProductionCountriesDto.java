package lucasbaiao.com.br.moviessearch.api.dto;

import com.google.gson.annotations.SerializedName;

public class ProductionCountriesDto {

    @SerializedName("name")
    public
    String name;

    public ProductionCountriesDto(String name) {
        this.name = name;
    }
}
