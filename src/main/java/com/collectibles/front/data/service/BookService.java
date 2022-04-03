package com.collectibles.front.data.service;

import com.collectibles.front.data.client.BookClient;
import com.collectibles.front.data.domain.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookClient bookClient;

    public List<BookDto> fetchBooks() {
        return bookClient.getBooks();
    }

    public BookDto fetchBook(Long id) {
        return bookClient.getBook(id);
    }

    public void deleteBook(Long id) {
        bookClient.deleteBook(id);
    }

    public void updateBook(BookDto BookDto) {
        bookClient.updateBook(BookDto);
    }

    public void createBook(BookDto BookDto) {
        bookClient.createBook(BookDto);
    }
}
