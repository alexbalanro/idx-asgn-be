package com.idexx.asgn.search.model.external.api.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BookItem {
    private VolumeInfo volumeInfo;
}
