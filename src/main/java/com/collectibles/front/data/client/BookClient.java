package com.collectibles.front.data.client;

import com.collectibles.front.data.domain.BookDto;
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
public class BookClient {

    @Value("${collectibles.app.books.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public List<BookDto> getBooks() {
        BookDto[] response = restTemplate.getForObject(
                endpoint,
                BookDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public BookDto getBook(Long id) {
        BookDto response = restTemplate.getForObject(
                endpoint + "/" + id,
                BookDto.class
        );

        return Optional.ofNullable(response)
                .orElse(new BookDto());
    }

    public void deleteBook(Long id) {
        restTemplate.delete(endpoint + "/" + id);
    }

    public void updateBook(BookDto bookDto) {
        restTemplate.put(
                endpoint,
                bookDto
        );
    }

    public void createBook(BookDto bookDto) {
        restTemplate.postForObject(
                endpoint,
                bookDto,
                BookDto.class
        );
    }
}
