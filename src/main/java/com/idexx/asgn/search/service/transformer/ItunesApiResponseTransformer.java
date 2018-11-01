package com.idexx.asgn.search.service.transformer;

import com.idexx.asgn.search.model.AlbumSearchResult;
import com.idexx.asgn.search.model.SearchResult;
import com.idexx.asgn.search.model.external.api.itunes.ItunesApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ItunesApiResponseTransformer {
    @Value("${search.results.album.maxSize}")
    private int resultsMaxSize;

    public List<SearchResult> transformSearchResults(ItunesApiResponse response) {
        return response.getResults()
                .stream()
                .limit(resultsMaxSize)
                .map(item ->
                        new AlbumSearchResult(
                                Optional.ofNullable(item.getCollectionName()).orElse(""),
                                Optional.ofNullable(item.getArtistName()).orElse("")))
                .collect(Collectors.toList());
    }
}
