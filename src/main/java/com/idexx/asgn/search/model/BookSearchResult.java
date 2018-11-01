package com.idexx.asgn.search.model;

public class BookSearchResult extends SearchResult {
    public BookSearchResult(String title, String author) {
        super(title, author, SearchResultType.BOOK);
    }
}
