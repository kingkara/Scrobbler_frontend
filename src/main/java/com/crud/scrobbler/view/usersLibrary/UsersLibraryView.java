package com.crud.scrobbler.view.usersLibrary;

import com.crud.scrobbler.domain.UsersArtistDto;
import com.crud.scrobbler.domain.UsersTrackDto;
import com.crud.scrobbler.domain.spotify.SpotifyFullTrackDto;
import com.crud.scrobbler.service.SpotifyService;
import com.crud.scrobbler.service.UsersArtistsService;
import com.crud.scrobbler.service.UsersTracksService;
import com.crud.scrobbler.view.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static com.crud.scrobbler.view.MainView.createTestDisplay;


@Route(value = "usersLibrary", layout = MainView.class)
public class UsersLibraryView extends Div {

    public UsersLibraryView(@Autowired SpotifyService spotifyService, @Autowired UsersTracksService usersTracksService,
                            @Autowired UsersArtistsService usersArtistsService) {
        setSizeFull();

        //HEADER
        Label title = new Label("Your library");
        title.getStyle().set("font-size", "30px")
                .set("font-weight", "600");
        HorizontalLayout header = new HorizontalLayout();

        header.add(createTestDisplay(title));
        header.setWidth("100%");

        //LASTLY PLAYED
        Label title2 = new Label("Lastly played tracks");
        title2.getStyle().set("font-size", "20px")
                .set("font-weight", "400");
        Component titleComponent = createTestDisplay(title2);

        try {

            List<SpotifyFullTrackDto> spotifyFullTrackDtos = spotifyService.getPlayback();
            List<SpotifyFullTrackDto> subList = spotifyFullTrackDtos.subList(0, 10);
            Grid<SpotifyFullTrackDto> trackDtoGrid = new Grid<>();
            trackDtoGrid.setItems(subList);

            trackDtoGrid.addColumn(SpotifyFullTrackDto -> SpotifyFullTrackDto.getSpotifyTrackDto().getTitle()).setHeader("Title")
                    .setResizable(true).setTextAlign(ColumnTextAlign.CENTER);
            trackDtoGrid.addColumn(SpotifyFullTrackDto -> SpotifyFullTrackDto.getSpotifyTrackDto().getSpotifyArtistDto()
                    .get(0).getName()).setHeader("Artist").setResizable(true).setTextAlign(ColumnTextAlign.CENTER);
            trackDtoGrid.addColumn(SpotifyFullTrackDto -> Date.from(Instant.parse(SpotifyFullTrackDto.getPlayedAt())))
                    .setHeader("Played at").setResizable(true).setTextAlign(ColumnTextAlign.CENTER);

            trackDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

            VerticalLayout lastlyPLayed = new VerticalLayout();
            lastlyPLayed.add(titleComponent, trackDtoGrid);
            lastlyPLayed.setSpacing(true);


            //TOP TRACKS AND ARTISTS
            Label title3 = new Label("Top played tracks");
            title3.getStyle().set("font-size", "20px")
                    .set("font-weight", "400");
            Component title3Component = createTestDisplay(title3);

            Grid<UsersTrackDto> usersTrackDtoGrid = new Grid<>();
            usersTrackDtoGrid.addColumn(UsersTrackDto::getTitle).setHeader("Title").setTextAlign(ColumnTextAlign.CENTER);
            usersTrackDtoGrid.addColumn(UsersTrackDto::getArtistName).setHeader("Artist").setTextAlign(ColumnTextAlign.CENTER);
            usersTrackDtoGrid.addColumn(UsersTrackDto::getCount).setHeader("Count").setTextAlign(ColumnTextAlign.CENTER);

            usersTrackDtoGrid.setItems(usersTracksService.getTopUsersTracks(123123));
            usersTrackDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

            Label title4 = new Label("Top played artists");
            title4.getStyle().set("font-size", "20px")
                    .set("font-weight", "400");
            Component title4Component = createTestDisplay(title4);

            Grid<UsersArtistDto> usersArtistDtoGrid = new Grid<>();
            usersArtistDtoGrid.addColumn(UsersArtistDto::getName).setHeader("Artist Name").setTextAlign(ColumnTextAlign.CENTER);
            usersArtistDtoGrid.addColumn(UsersArtistDto::getCount).setHeader("Count").setTextAlign(ColumnTextAlign.CENTER);

            usersArtistDtoGrid.setItems(usersArtistsService.getTopArtists(123123));
            usersArtistDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

            VerticalLayout tops = new VerticalLayout();
            tops.add(title3Component, usersTrackDtoGrid, title4Component, usersArtistDtoGrid);

            //VIEW
            SplitLayout splitLayout = new SplitLayout();
            splitLayout.setSizeFull();
            splitLayout.addToPrimary(lastlyPLayed);
            splitLayout.addToSecondary(tops);
            add(header, splitLayout);
        } catch (Exception ignore) {
        }
    }
}
