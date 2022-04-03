package com.collectibles.front.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("author")
    private String author;

    @JsonProperty("content")
    private String content;

    public QuoteDto(String author, String content) {
        this.author = author;
        this.content = content;
    }
}
