package com.idexx.asgn.search.service.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idexx.asgn.search.model.SearchResultResponse;
import com.idexx.asgn.search.model.external.api.itunes.ItunesApiResponse;
import com.idexx.asgn.search.service.transformer.ItunesApiResponseTransformer;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


@Component
public class AlbumSearchResultsProvider implements ExternalSearchResultProvider {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${search.results.album.url}")
    private String albumUrl;

    @Autowired
    private ItunesApiResponseTransformer transformer;

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    public AlbumSearchResultsProvider() {
    }

    /**
     * ITunes API returns content-type text/javascript; charset=utf-8 which is not handled correctly by
     * Spring's HTTP message converter
     * Need to manually translate the response to JSON
     */
    @Override
    @Async
    @Timed("external.search.itunes")
    public CompletableFuture<SearchResultResponse> getExternalResults(String searchTerm) {
        ResponseEntity<byte[]> responseAsByte =
                restTemplate.exchange(albumUrl, HttpMethod.GET, null, byte[].class, searchTerm);

        ItunesApiResponse response;
        try {
            response = jacksonObjectMapper.readValue(responseAsByte.getBody(), ItunesApiResponse.class);
        } catch (IOException io) {
            throw new RuntimeException(io);
        }

        return CompletableFuture.completedFuture(new SearchResultResponse(transformer.transformSearchResults(response)));
    }
}
