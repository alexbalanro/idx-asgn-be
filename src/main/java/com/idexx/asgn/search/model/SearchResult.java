package com.idexx.asgn.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class SearchResult {
    private String title;
    private String author;
    private SearchResultType searchResultType;
}
