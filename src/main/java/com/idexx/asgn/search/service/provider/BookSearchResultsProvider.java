package com.idexx.asgn.search.service.provider;

import com.idexx.asgn.search.model.SearchResultResponse;
import com.idexx.asgn.search.model.external.api.google.GoogleApiResponse;
import com.idexx.asgn.search.service.transformer.GoogleApiResponseTransformer;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
public class BookSearchResultsProvider implements ExternalSearchResultProvider {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${search.results.book.url}")
    private String bookUrl;

    @Autowired
    private GoogleApiResponseTransformer transformer;


    @Override
    @Async
    @Timed("external.search.googleApi")
    public CompletableFuture<SearchResultResponse> getExternalResults(String searchTerm) {
        GoogleApiResponse response = restTemplate.getForObject(bookUrl, GoogleApiResponse.class, searchTerm);

        return CompletableFuture.completedFuture(new SearchResultResponse(transformer.transformSearchResults(response)));
    }
}
