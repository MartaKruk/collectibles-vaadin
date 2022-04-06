package com.collectibles.front.data.client;

import com.collectibles.front.data.config.CollectiblesConfig;
import com.collectibles.front.data.domain.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookClient {

    private final CollectiblesConfig config;
    private final RestTemplate restTemplate;

    public List<BookDto> getBooks() {
        BookDto[] response = restTemplate.getForObject(
                config.getEndpoint() + config.getBooksPath(),
                BookDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public BookDto getBook(Long id) {
        BookDto response = restTemplate.getForObject(
                config.getEndpoint() + config.getBooksPath() + "/" + id,
                BookDto.class
        );

        return Optional.ofNullable(response)
                .orElse(new BookDto());
    }

    public void deleteBook(Long id) {
        restTemplate.delete(config.getEndpoint() + config.getBooksPath() + "/" + id);
    }

    public void updateBook(BookDto bookDto) {
        restTemplate.put(
                config.getEndpoint() + config.getBooksPath(),
                bookDto
        );
    }

    public void createBook(BookDto bookDto) {
        restTemplate.postForObject(
                config.getEndpoint() + config.getBooksPath(),
                bookDto,
                BookDto.class
        );
    }
}
