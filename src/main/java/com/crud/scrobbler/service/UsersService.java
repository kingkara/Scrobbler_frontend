package com.crud.scrobbler.service;

import com.crud.scrobbler.domain.User;
import com.crud.scrobbler.domain.UserDto;
import com.crud.scrobbler.mapper.UsersMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UsersService {
    private RestTemplate restTemplate;
    private UsersMapper mapper;

    public UsersService(RestTemplate restTemplate, UsersMapper mapper) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
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
