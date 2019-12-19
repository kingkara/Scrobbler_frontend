package com.crud.scrobbler.view.usersLibrary;

import com.crud.scrobbler.domain.UsersArtistDto;
import com.crud.scrobbler.service.ArtistsService;
import com.crud.scrobbler.service.UsersArtistsService;
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
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Route(value = "usersArtists", layout = MainView.class)
public class UsersArtistsView extends Div {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersArtistsView.class);

    public UsersArtistsView(@Autowired UsersArtistsService usersArtistsService, @Autowired ArtistsService artistsService) {

        Grid<UsersArtistDto> usersArtistDtoGrid = new Grid<>();
        usersArtistDtoGrid.addColumn(UsersArtistDto::getName).setHeader("Artist Name").setTextAlign(ColumnTextAlign.CENTER);
        usersArtistDtoGrid.addColumn(UsersArtistDto::getCount).setHeader("Count").setTextAlign(ColumnTextAlign.CENTER).setSortProperty("count");
        usersArtistDtoGrid.addComponentColumn(artist -> {
            Button button = new Button("Remove");
            button.addClickListener(click -> {
                usersArtistsService.deleteUsersArtist(123123, artistsService.getArtistByName(artist.getName()).getId());
                List<UsersArtistDto> update = usersArtistsService.getUsersArtists(123123);
                usersArtistDtoGrid.setItems(update);
            });
            button.setIcon(VaadinIcon.TRASH.create());
            return button;
        });
        usersArtistDtoGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        try {
            usersArtistDtoGrid.setItems(usersArtistsService.getUsersArtists(123123));
        } catch (ResourceAccessException e) {
            LOGGER.warn("Couldn't get data from database. Check your backend connection.");
        }

        setSizeFull();
        add(usersArtistDtoGrid);
    }
}
