package com.collectibles.front.data.client;

import com.collectibles.front.data.domain.UserDto;
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
public class UserClient {

    @Value("${collectibles.app.users.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate;

    public List<UserDto> getUsers() {
        UserDto[] response = restTemplate.getForObject(
                endpoint,
                UserDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public UserDto getUser(Long id) {
        UserDto response = restTemplate.getForObject(
                endpoint + "/" + id,
                UserDto.class
        );

        return Optional.ofNullable(response)
                .orElse(new UserDto());
    }

    public void deleteUser(Long id) {
        restTemplate.delete(endpoint + "/" + id);
    }

    public void updateUser(UserDto userDto) {
        restTemplate.put(
                endpoint,
                userDto
        );
    }

    public void createUser(UserDto userDto) {
        restTemplate.postForObject(
                endpoint,
                userDto,
                UserDto.class
        );
    }
}
