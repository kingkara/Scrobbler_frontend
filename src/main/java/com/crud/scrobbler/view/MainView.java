package com.crud.scrobbler.view;

import com.crud.scrobbler.domain.lyricsApi.LyricsDto;
import com.crud.scrobbler.domain.spotify.SpotifyCurrentlyPlayedDto;
import com.crud.scrobbler.service.LyricsApiService;
import com.crud.scrobbler.service.SpotifyService;
import com.crud.scrobbler.view.artists.ArtistsSearchView;
import com.crud.scrobbler.view.tracks.TracksSearchView;
import com.crud.scrobbler.view.user.CreateUserView;
import com.crud.scrobbler.view.user.DeleteUserView;
import com.crud.scrobbler.view.user.EditUserView;
import com.crud.scrobbler.view.usersLibrary.UsersArtistsView;
import com.crud.scrobbler.view.usersLibrary.UsersLibraryView;
import com.crud.scrobbler.view.usersLibrary.UsersTracksView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@HtmlImport("styles/shared-styles.html")
@Route(value = "")
@Theme(value = Lumo.class)
public class MainView extends VerticalLayout implements RouterLayout {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainView.class);

    public MainView(@Autowired LyricsApiService lyricsApiService, @Autowired SpotifyService spotifyService) {
        // HEADER
        Label title = new Label("Scrobbler demo application");
        title.getStyle().set("font-size", "30px")
                .set("font-weight", "600");
        HorizontalLayout header = new HorizontalLayout(createTestDisplay(title));
        header.setPadding(true);
        header.setWidth("100%");

        // CURRENTLY PLAYED
        Label title2 = new Label("Currently played track");
        title2.getStyle().set("font-size", "20px")
                .set("font-weight", "600");
        AtomicReference<VerticalLayout> currentTrack = new AtomicReference<>(new VerticalLayout(createTestDisplay(new Text("Sorry you're not listening to anything right now."))));
        currentTrack.get().setSizeFull();
        displayLyrics(spotifyService, lyricsApiService, currentTrack, title2);

        // MENU
        Tab actionButton1 = new Tab(VaadinIcon.HOME.create(), new RouterLink("Home", MainView.class));
        Tab actionButton2 = new Tab(VaadinIcon.PLUS.create(), new RouterLink("Create User", CreateUserView.class));
        Tab actionButton3 = new Tab(VaadinIcon.TOOLS.create(), new RouterLink("Edit User", EditUserView.class));
        Tab actionButton4 = new Tab(VaadinIcon.TRASH.create(), new RouterLink("Delete User", DeleteUserView.class));
        Tab actionButton5 = new Tab(VaadinIcon.USERS.create(), new RouterLink("Users Library", UsersLibraryView.class));
        Tab actionButton6 = new Tab(VaadinIcon.MUSIC.create(), new RouterLink("Your Tracks", UsersTracksView.class));
        Tab actionButton7 = new Tab(VaadinIcon.MICROPHONE.create(), new RouterLink("Your Artists", UsersArtistsView.class));
        Tab actionButton8 = new Tab(VaadinIcon.SEARCH.create(), new RouterLink("Search Tracks", TracksSearchView.class));
        Tab actionButton9 = new Tab(VaadinIcon.SEARCH.create(), new RouterLink("Search Artist", ArtistsSearchView.class));
        Tabs buttonBar = new Tabs(actionButton1, actionButton2, actionButton3, actionButton4, actionButton5, actionButton6,
                actionButton7, actionButton8, actionButton9);

        HorizontalLayout menu = new HorizontalLayout(buttonBar);
        menu.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        menu.setWidth("100%");

        Map<Tab, String> map = new HashMap<>();
        map.put(actionButton1, "");
        map.put(actionButton2, "createUser");
        map.put(actionButton3, "editUser");
        map.put(actionButton4, "deleteUser");
        map.put(actionButton5, "usersLibrary");
        map.put(actionButton6, "usersTracks");
        map.put(actionButton7, "usersArtists");
        map.put(actionButton8, "tracksSearch");
        map.put(actionButton9, "artistsSearch");
        buttonBar.addSelectedChangeListener(e ->
        {
            removeAll();
            add(header, menu);
            UI.getCurrent().navigate(map.get(e.getSource().getSelectedTab()));
            if (map.get(e.getSource().getSelectedTab()).equals("")) {
                displayLyrics(spotifyService, lyricsApiService, currentTrack, title2);
                VerticalLayout finalCurrentTrack = currentTrack.get();
                add(finalCurrentTrack);
            }
        });

        setSizeFull();
        setMargin(false);
        setSpacing(false);
        setPadding(false);
        add(header, menu, currentTrack.get());
    }

    public static Component createTestDisplay(Component element) {
        Span component = new Span();
        FlexLayout flexLayout = new FlexLayout(component);
        flexLayout.add(element);
        flexLayout.setAlignItems(Alignment.CENTER);
        flexLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        flexLayout.setWidth("100%");
        flexLayout.getStyle().set("box-shadow", "var(--app-layout-notification-shadow)");
        return flexLayout;
    }

    private void displayLyrics(SpotifyService spotifyService, LyricsApiService lyricsApiService, AtomicReference<VerticalLayout> currentTrack, Label title2) {
        try {
            SpotifyCurrentlyPlayedDto currentlyPlayedDto = spotifyService.getCurrentPlaying();
            if (currentlyPlayedDto.getTitle() != null) {
                String name = currentlyPlayedDto.getArtistDtos().get(0).getName();
                Label formattedName = new Label(name);
                formattedName.getStyle().set("font-size", "15px")
                        .set("font-weight", "500");
                String songTitle = currentlyPlayedDto.getTitle();
                Label formattedTitle = new Label(songTitle);
                formattedTitle.getStyle().set("font-size", "15px")
                        .set("font-weight", "500");
                Label formattedLyrics = new Label("Lyrics not found... Sorry");
                try {
                    LyricsDto lyrics = lyricsApiService.getLyrics(name, songTitle);
                    if (lyrics.getLyrics() != null) {
                        formattedLyrics = new Label(lyrics.getLyrics());
                        formattedLyrics.getStyle().set("font-size", "15px")
                                .set("font-weight", "300")
                                .set("white-space", "pre-wrap");
                    }
                } catch (HttpClientErrorException ex2) {
                    LOGGER.warn("Lyrics not found.");
                }
                currentTrack.set(new VerticalLayout(createTestDisplay(title2), createTestDisplay(formattedName),
                        createTestDisplay(formattedTitle), createTestDisplay(formattedLyrics)));
            }
        } catch (ResourceAccessException ex) {
            LOGGER.warn("Backend isn't working, please check connection");
        }
    }
}

