package com.idexx.asgn.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchResultResponse {
    private final List<SearchResult> resultList;
}
