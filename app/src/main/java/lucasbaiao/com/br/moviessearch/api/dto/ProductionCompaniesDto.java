package lucasbaiao.com.br.moviessearch.api.dto;

import com.google.gson.annotations.SerializedName;

public class ProductionCompaniesDto {

    @SerializedName("name")
    public
    String name;

    public ProductionCompaniesDto(String name) {
        this.name = name;
    }
}
