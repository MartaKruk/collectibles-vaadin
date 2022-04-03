package com.collectibles.front.data.service;

import com.collectibles.front.data.client.CollectionClient;
import com.collectibles.front.data.domain.BookDto;
import com.collectibles.front.data.domain.CollectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionClient collectionClient;

    public List<CollectionDto> fetchCollections() {
        return collectionClient.getCollections();
    }

    public CollectionDto fetchCollection(Long id) {
        return collectionClient.getCollection(id);
    }

    public void deleteCollection(Long id) {
        collectionClient.deleteCollection(id);
    }

    public void updateCollection(CollectionDto CollectionDto) {
        collectionClient.updateCollection(CollectionDto);
    }

    public void createCollection(CollectionDto CollectionDto) {
        collectionClient.createCollection(CollectionDto);
    }

    public List<BookDto> fetchBooksInCollection(Long id) {
        return collectionClient.getBooksInCollection(id);
    }

    public void addBookToCollection(Long id, BookDto bookDto) {
        collectionClient.addBookToCollection(id, bookDto);
    }

    public void deleteBookFromCollection(Long collectionId, Long bookId) {
        collectionClient.deleteBookFromCollection(collectionId, bookId);
    }
}
