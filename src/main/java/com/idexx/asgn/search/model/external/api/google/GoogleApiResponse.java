package com.idexx.asgn.search.model.external.api.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GoogleApiResponse {
    private List<BookItem> items;
}
