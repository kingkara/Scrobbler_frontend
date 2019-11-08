package com.crud.scrobbler.mapper;

import com.crud.scrobbler.domain.UsersArtist;
import com.crud.scrobbler.domain.UsersArtistDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersArtistsMapper {

    public UsersArtist mapToUsersArtist(final UsersArtistDto usersArtist) {
        return new UsersArtist(usersArtist.getUser(), usersArtist.getArtist());
    }

    public List<UsersArtistDto> mapToUsersArtistDtoList(final List<UsersArtist> usersArtists) {
        return usersArtists.stream()
                .map(usersArtist -> new UsersArtistDto(usersArtist.getId(), usersArtist.getCount(), usersArtist.getLastPlayedTime(),
                        usersArtist.getUser(), usersArtist.getArtist()))
                .collect(Collectors.toList());
    }
}
