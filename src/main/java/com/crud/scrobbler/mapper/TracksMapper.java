package com.crud.scrobbler.mapper;

import com.crud.scrobbler.domain.Track;
import com.crud.scrobbler.domain.TrackDto;
import org.springframework.stereotype.Component;

@Component
public class TracksMapper {

    public TrackDto mapToTrackDto(final Track track) {
        return new TrackDto(track.getId(), track.getTitle(), track.getArtist(), track.getUsersTracks(), track.getComments());
    }
}
