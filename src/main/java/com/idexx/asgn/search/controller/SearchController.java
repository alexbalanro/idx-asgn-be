package com.idexx.asgn.search.controller;

import com.idexx.asgn.search.model.SearchResult;
import com.idexx.asgn.search.service.SearchResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {
    @Autowired
    private SearchResultsService searchResultsService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<SearchResult> getSearch(@RequestParam String searchTerm) {
        return searchResultsService.getSearchResults(searchTerm);
    }

}
