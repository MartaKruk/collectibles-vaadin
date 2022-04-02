package com.collectibles.front.views.search;

public enum SearchType {
    TITLE("title"),
    AUTHOR("author");

    private String text;

    SearchType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
