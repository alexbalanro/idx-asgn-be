package com.idexx.asgn.search.service.provider;

import com.idexx.asgn.search.model.SearchResultResponse;

import java.util.concurrent.CompletableFuture;

public interface ExternalSearchResultProvider {
    CompletableFuture<SearchResultResponse> getExternalResults(String searchTerm);
}
