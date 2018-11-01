package com.idexx.asgn.search.model.external.api.itunes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AlbumInfo {
    String artistName;
    String collectionName;
}
