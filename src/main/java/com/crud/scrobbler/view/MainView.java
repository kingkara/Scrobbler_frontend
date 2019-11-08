package com.crud.scrobbler.view;

import com.crud.scrobbler.domain.lyricsApi.LyricsDto;
import com.crud.scrobbler.domain.spotify.SpotifyCurrentlyPlayedDto;
import com.crud.scrobbler.service.LyricsApiService;
import com.crud.scrobbler.service.SpotifyService;
import com.crud.scrobbler.view.artists.ArtistsSearch;
import com.crud.scrobbler.view.tracks.TracksSearch;
import com.crud.scrobbler.view.user.CreateUserView;
import com.crud.scrobbler.view.usersLibrary.UsersLibraryView;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@HtmlImport("styles/shared-styles.html")
@Route(value = "home")
@Theme(value = Lumo.class)
public class MainView extends VerticalLayout implements RouterLayout {

    public MainView(@Autowired LyricsApiService lyricsApiService, @Autowired SpotifyService spotifyService) throws NullPointerException {
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
        VerticalLayout currentTrack = new VerticalLayout(createTestDisplay(new Text("Sorry you're not listening to anything right now.")));
        currentTrack.setSizeFull();
        SpotifyCurrentlyPlayedDto currentlyPlayedDto = spotifyService.getCurrentPlaying();
        if (currentlyPlayedDto != null) {
            String name = currentlyPlayedDto.getArtistDtos().get(0).getName();
            Label formatedName = new Label(name);
            formatedName.getStyle().set("font-size", "15px")
                    .set("font-weight", "500");
            String songTitle = currentlyPlayedDto.getTitle();
            Label formatedTitle = new Label(songTitle);
            formatedTitle.getStyle().set("font-size", "15px")
                    .set("font-weight", "500");
            Label formatedlyrics = new Label("Lyrics not found... Sorry");
            LyricsDto lyrics = lyricsApiService.getLyrics(name, songTitle);
            if (lyrics.getLyrics() != null) {
                formatedlyrics = new Label(lyrics.getLyrics());
                formatedlyrics.getStyle().set("font-size", "15px")
                        .set("font-weight", "300")
                        .set("white-space", "pre-wrap");
            }
            currentTrack = new VerticalLayout(createTestDisplay(title2), createTestDisplay(formatedName),
                    createTestDisplay(formatedTitle), createTestDisplay(formatedlyrics));
        }

        // MENU
        Tab actionButton1 = new Tab(VaadinIcon.HOME.create(), new RouterLink("Home", MainView.class));
        Tab actionButton2 = new Tab(VaadinIcon.PLUS.create(), new RouterLink("Create User", CreateUserView.class));
        Tab actionButton3 = new Tab(VaadinIcon.USERS.create(), new RouterLink("Users Library", UsersLibraryView.class));
        Tab actionButton4 = new Tab(VaadinIcon.SEARCH.create(), new RouterLink("Search Tracks", TracksSearch.class));
        Tab actionButton5 = new Tab(VaadinIcon.SEARCH.create(), new RouterLink("Search Artist", ArtistsSearch.class));
        Tabs buttonBar = new Tabs(actionButton1, actionButton2, actionButton3, actionButton4, actionButton5);

        HorizontalLayout menu = new HorizontalLayout(buttonBar);
        menu.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        menu.setWidth("100%");

        Map<Tab, String> map = new HashMap<>();
        map.put(actionButton1, "home");
        map.put(actionButton2, "createUser");
        map.put(actionButton3, "usersLibrary");
        map.put(actionButton4, "tracksSearch");
        map.put(actionButton5, "artistsSearch");
        VerticalLayout finalCurrentTrack = currentTrack;
        buttonBar.addSelectedChangeListener(e ->

        {
            removeAll();
            add(header, menu);
            UI.getCurrent().navigate(map.get(e.getSource().getSelectedTab()));
            if (map.get(e.getSource().getSelectedTab()).equals("home")) {
                add(finalCurrentTrack);
            }
        });

        setSizeFull();
        setMargin(false);
        setSpacing(false);
        setPadding(false);
        add(header, menu, currentTrack);

    }

    private Component createTestDisplay(Component element) {
        Span component = new Span();
        FlexLayout flexLayout = new FlexLayout(component);
        flexLayout.add(element);
        flexLayout.setAlignItems(Alignment.CENTER);
        flexLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        flexLayout.setWidth("100%");
        flexLayout.getStyle().set("box-shadow", "var(--app-layout-notification-shadow)");
        return flexLayout;
    }
}

