package com.idexx.asgn.search.model.external.api.itunes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ItunesApiResponse {
    List<AlbumInfo> results;
}
