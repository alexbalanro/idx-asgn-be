package com.idexx.asgn.search.service;

import com.idexx.asgn.search.exception.NoExternalResultsFoundException;
import com.idexx.asgn.search.model.SearchResult;
import com.idexx.asgn.search.model.SearchResultResponse;
import com.idexx.asgn.search.service.provider.ExternalSearchResultProvider;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class SearchResultsService {

    private static final Logger LOG = LoggerFactory.getLogger(SearchResultsService.class);

    @Autowired
    private List<ExternalSearchResultProvider> providers;

    @Timed("external.search.total.time")
    public List<SearchResult> getSearchResults(String searchTerm) {
        List<CompletableFuture<SearchResultResponse>> futures =
                providers.stream().map(p -> p.getExternalResults(searchTerm)).collect(Collectors.toList());

        List<SearchResult> allResults = new ArrayList<>();

        CompletableFuture<?>[] completedFutures = futures.stream().
                map(f -> f.whenComplete((result, throwable) -> {
                            if (result != null) {
                                allResults.addAll(result.getResultList());
                            }
                            if (throwable != null) {
                                LOG.error("Failure to get some results", throwable);
                            }
                        }
                )).toArray(CompletableFuture<?>[]::new);
        try {
            CompletableFuture.allOf(completedFutures).join();
        } catch (Exception e) {
            long errorCount = Arrays.stream(completedFutures)
                    .filter(CompletableFuture::isCompletedExceptionally)
                    .count();
            LOG.error("Search providers with errors {}", errorCount);
            if (errorCount == providers.size()) { //all providers threw exceptions
                throw new NoExternalResultsFoundException("No upstream services working");
            }
        }

        allResults.sort((l, r) -> l.getTitle().compareToIgnoreCase(r.getTitle()));

        return allResults;
    }
}
