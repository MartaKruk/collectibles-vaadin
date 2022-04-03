package com.collectibles.front.data.service;

import com.collectibles.front.data.client.UserClient;
import com.collectibles.front.data.domain.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient userClient;

    public List<UserDto> fetchUsers() {
        return userClient.getUsers();
    }

    public UserDto fetchUser(Long id) {
        return userClient.getUser(id);
    }

    public void deleteUser(Long id) {
        userClient.deleteUser(id);
    }

    public void updateUser(UserDto UserDto) {
        userClient.updateUser(UserDto);
    }

    public void createUser(UserDto UserDto) {
        userClient.createUser(UserDto);
    }
}
