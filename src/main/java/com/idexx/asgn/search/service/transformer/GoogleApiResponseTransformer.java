package com.idexx.asgn.search.service.transformer;

import com.idexx.asgn.search.model.BookSearchResult;
import com.idexx.asgn.search.model.SearchResult;
import com.idexx.asgn.search.model.external.api.google.GoogleApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GoogleApiResponseTransformer {
    @Value("${search.results.book.maxSize}")
    private int resultsMaxSize;

    public List<SearchResult> transformSearchResults(GoogleApiResponse response) {
        return response.getItems()
                .stream()
                .limit(resultsMaxSize)
                .map(item ->
                        new BookSearchResult(
                                Optional.ofNullable(item.getVolumeInfo().getTitle()).orElse(""),
                                concatenateAuthors(item.getVolumeInfo().getAuthors())))
                .collect(Collectors.toList());
    }

    private String concatenateAuthors(List<String> authors) {
        if (authors == null)
            return "";

        return String.join(",", authors);
    }
}
