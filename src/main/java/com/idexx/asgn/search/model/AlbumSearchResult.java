package com.idexx.asgn.search.model;

public class AlbumSearchResult extends SearchResult {
    public AlbumSearchResult(String title, String author) {
        super(title, author, SearchResultType.ALBUM);
    }
}
