package com.collectibles.front.data.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CollectiblesConfig {

    @Value("${collectibles.app.endpoint}")
    private String endpoint;

    @Value("${collectibles.app.openlibrary.path}")
    private String openLibraryPath;

    @Value("${collectibles.app.quotelib.path}")
    private String quoteLibPath;

    @Value("${collectibles.app.quotes.path}")
    private String quotesPath;

    @Value("${collectibles.app.collections.path}")
    private String collectionsPath;

    @Value("${collectibles.app.users.path}")
    private String usersPath;

    @Value("${collectibles.app.books.path}")
    private String booksPath;
}
