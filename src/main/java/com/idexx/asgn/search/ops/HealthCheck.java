package com.idexx.asgn.search.ops;

import com.idexx.asgn.search.service.SearchResultsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck implements HealthIndicator {

    @Autowired
    private SearchResultsService searchResultsService;

    @Override
    public Health health() {
        try {
            searchResultsService.getSearchResults("health");
            return Health.up().build();
        } catch (Exception e) {
            return Health.down().build();
        }
    }
}
