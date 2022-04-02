package com.collectibles.front.data.service;

import com.collectibles.front.data.client.OpenLibraryClient;
import com.collectibles.front.data.domain.ResultBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenLibraryService {

    private final OpenLibraryClient openLibraryClient;

    public List<ResultBookDto> fetchBooksByAuthor(String keyword) {
        return openLibraryClient.getBooksByAuthor(keyword);
    }

    public List<ResultBookDto> fetchBooksByTitle(String keyword) {
        return openLibraryClient.getBooksByTitle(keyword);
    }
}
