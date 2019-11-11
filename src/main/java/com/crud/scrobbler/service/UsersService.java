package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsersService {
    private RestTemplate restTemplate;

    public UsersService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getUser(final long id) {
        return restTemplate.getForObject("http://localhost:8088/v1/user/" + id, UserDto.class);
    }

    public void updateUser(final UserDto userDto) {
        restTemplate.put("http://localhost:8088/v1/user", userDto, UserDto.class);
    }

    public void saveUser(final UserDto user) {
        restTemplate.postForObject("http://localhost:8088/v1/user", user, UserDto.class);
    }

    public void deleteUser(final long id) {
        restTemplate.delete("http://localhost:8088/v1/user/" + id);
    }
}
