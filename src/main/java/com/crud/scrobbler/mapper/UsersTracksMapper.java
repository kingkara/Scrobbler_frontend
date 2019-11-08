package com.crud.scrobbler.mapper;

import com.crud.scrobbler.domain.UsersTrack;
import com.crud.scrobbler.domain.UsersTrackDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersTracksMapper {

    public UsersTrack mapToUsersTrack(final UsersTrackDto usersTrackDto) {
        return new UsersTrack(usersTrackDto.getUser(), usersTrackDto.getTrack());
    }

    public UsersTrackDto mapToUsersTrackDto(final UsersTrack usersTrack) {
        return new UsersTrackDto(usersTrack.getId(), usersTrack.getLastPlayedTime(), usersTrack.getCount(), usersTrack.isFavouriteStatus(),
                usersTrack.getUser(), (usersTrack.getTrack()));
    }

    public List<UsersTrackDto> mapToUsersTrackDtoList(final List<UsersTrack> usersTracks) {
        return usersTracks.stream()
                .map(usersTrack -> new UsersTrackDto(usersTrack.getId(),
                        usersTrack.getLastPlayedTime(), usersTrack.getCount(), usersTrack.isFavouriteStatus(),
                        usersTrack.getUser(), usersTrack.getTrack()))
                .collect(Collectors.toList());
    }
}
