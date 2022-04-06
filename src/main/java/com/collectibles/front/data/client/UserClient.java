package com.collectibles.front.data.client;

import com.collectibles.front.data.config.CollectiblesConfig;
import com.collectibles.front.data.domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final CollectiblesConfig config;
    private final RestTemplate restTemplate;

    public List<UserDto> getUsers() {
        UserDto[] response = restTemplate.getForObject(
                config.getEndpoint() + config.getUsersPath(),
                UserDto[].class
        );

        return Optional.ofNullable(response)
                .map(Arrays::asList)
                .orElse(Collections.emptyList());
    }

    public UserDto getUser(Long id) {
        UserDto response = restTemplate.getForObject(
                config.getEndpoint() + config.getUsersPath() + "/" + id,
                UserDto.class
        );

        return Optional.ofNullable(response)
                .orElse(new UserDto());
    }

    public void deleteUser(Long id) {
        restTemplate.delete(config.getEndpoint() + config.getUsersPath() + "/" + id);
    }

    public void updateUser(UserDto userDto) {
        restTemplate.put(
                config.getEndpoint() + config.getUsersPath(),
                userDto
        );
    }

    public void createUser(UserDto userDto) {
        restTemplate.postForObject(
                config.getEndpoint() + config.getUsersPath(),
                userDto,
                UserDto.class
        );
    }
}
