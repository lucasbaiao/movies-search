package lucasbaiao.com.br.moviessearch.api.dto;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagerDto<T> {

    @SerializedName("total_results")
    public int totalResults;
    @SerializedName("total_pages")
    public int totalPages;
    @SerializedName("page")
    public int page;
    @SerializedName("results")
    public java.util.List<T> results;

    public PagerDto(int totalResults, int totalPages, int page, List<T> results) {
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.page = page;
        this.results = results;
    }
}
