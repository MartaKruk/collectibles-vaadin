package com.collectibles.front.data.client;

import com.collectibles.front.data.domain.BookDto;
import com.collectibles.front.data.domain.CollectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CollectionClient {

    @Value("${collectibles.app.collections.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public List<CollectionDto> getCollections() {
        CollectionDto[] response = restTemplate.getForObject(
                endpoint,
                CollectionDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public CollectionDto getCollection(Long id) {
        CollectionDto response = restTemplate.getForObject(
                endpoint + "/" + id,
                CollectionDto.class
        );

        return Optional.ofNullable(response)
                .orElse(new CollectionDto());
    }

    public void deleteCollection(Long id) {
        restTemplate.delete(endpoint + "/" + id);
    }

    public void updateCollection(CollectionDto collectionDto) {
        restTemplate.put(
                endpoint,
                collectionDto
        );
    }

    public void createCollection(CollectionDto collectionDto) {
        restTemplate.postForObject(
                endpoint,
                collectionDto,
                CollectionDto.class
        );
    }

    public List<BookDto> getBooksInCollection(Long id) {
        BookDto[] response = restTemplate.getForObject(
                endpoint + "/" + id + "/books",
                BookDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public void addBookToCollection(Long id, BookDto bookDto) {
        restTemplate.postForObject(
                endpoint + "/" + id + "/books",
                bookDto,
                BookDto.class
        );
    }

    public void deleteBookFromCollection(Long collectionId, Long bookId) {
        restTemplate.delete(endpoint + "/" + collectionId + "/books/" + bookId);
    }
}
