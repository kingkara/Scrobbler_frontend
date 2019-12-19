package com.crud.scrobbler.view.usersLibrary;

import com.crud.scrobbler.domain.UsersTrackDto;
import com.crud.scrobbler.service.TracksService;
import com.crud.scrobbler.service.UsersTracksService;
import com.crud.scrobbler.view.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Route(value = "usersTracks", layout = MainView.class)
public class UsersTracksView extends Div {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersTracksView.class);

    public UsersTracksView(@Autowired UsersTracksService usersTracksService, @Autowired TracksService tracksService) {
        setSizeFull();

        Grid<UsersTrackDto> usersTrackDtoGrid = new Grid<>();
        usersTrackDtoGrid.addColumn(UsersTrackDto::getTitle).setHeader("Title").setTextAlign(ColumnTextAlign.CENTER);
        usersTrackDtoGrid.addColumn(UsersTrackDto::getArtistName).setHeader("Artist").setTextAlign(ColumnTextAlign.CENTER);
        usersTrackDtoGrid.addColumn(UsersTrackDto::getCount).setHeader("Count").setTextAlign(ColumnTextAlign.CENTER).setSortProperty("count");
        usersTrackDtoGrid.addColumn(UsersTrackDto::isFavouriteStatus).setHeader("Favourite status");
        usersTrackDtoGrid.addComponentColumn(item -> {
            Button button = new Button("Change status");
            button.addClickListener(click -> {
                usersTracksService.changeFavouriteStatus(123123, tracksService.getTrack(item.getTitle()).getId());
                List<UsersTrackDto> updateFav = usersTracksService.getUsersTracks(123123);
                usersTrackDtoGrid.setItems(updateFav);
            });
            button.setIcon(VaadinIcon.HEART.create());
            return button;
        });
        usersTrackDtoGrid.addComponentColumn(track -> {
            Button button = new Button("Remove");
            button.addClickListener(click -> {
                try {
                    usersTracksService.deleteTrack(123123, tracksService.getTrack(track.getTitle()).getId());
                } catch (HttpClientErrorException e) {
                    LOGGER.warn("Couldn't delete track.");
                }
                List<UsersTrackDto> update = usersTracksService.getUsersTracks(123123);
                usersTrackDtoGrid.setItems(update);
            });
            button.setIcon(VaadinIcon.TRASH.create());
            return button;
        });

        try {
            List<UsersTrackDto> usersTrackDtos = usersTracksService.getUsersTracks(123123);
            usersTrackDtoGrid.setItems(usersTrackDtos);
        } catch (ResourceAccessException e) {
            LOGGER.warn("Couldn't get data from database. Check your backend connection.");
        }

        usersTrackDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        usersTrackDtoGrid.getDataProvider();
        add(usersTrackDtoGrid);
    }
}
