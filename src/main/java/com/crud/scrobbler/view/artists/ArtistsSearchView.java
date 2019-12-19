package com.crud.scrobbler.view.artists;

import com.crud.scrobbler.domain.ArtistDto;
import com.crud.scrobbler.domain.TrackDto;
import com.crud.scrobbler.service.ArtistsService;
import com.crud.scrobbler.service.TracksService;
import com.crud.scrobbler.view.MainView;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;


@Route(value = "artistsSearch", layout = MainView.class)
@PageTitle("Find Artist")
public class ArtistsSearchView extends Div {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistsSearchView.class);
    private Grid<ArtistDto> artistGrid = new Grid<>();
    private TextField filter = new TextField();

    public ArtistsSearchView(@Autowired ArtistsService artistsService, @Autowired TracksService tracksService) {
        SplitLayout layout = new SplitLayout(
                new Label("Artist List"),
                new Label("Artists track - click artist to see its tracks"));

        //SEARCH
        filter.setPlaceholder("Filter by artist name");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);

        //ARTISTS LIST
        artistGrid.addColumn(ArtistDto::getName).setHeader("Artist name").setTextAlign(ColumnTextAlign.CENTER);
        artistGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        try {
            artistGrid.setItems(artistsService.getArtists());
            filter.addValueChangeListener(e -> artistGrid.setItems(artistsService.findByName(filter.getValue())));
        } catch (ResourceAccessException e) {
            LOGGER.warn("Couldn't get data from database. Check your backend connection.");
        }

        layout.setSplitterPosition(50);
        layout.addToPrimary(filter, artistGrid);

        //TRACKS DISPLAY
        artistGrid.asSingleSelect().addValueChangeListener(event -> {
            Grid<TrackDto> trackDtoGrid = new TreeGrid<>();
            trackDtoGrid.addColumn(TrackDto::getTitle).setHeader("Title").setTextAlign(ColumnTextAlign.CENTER);
            try {
                trackDtoGrid.setItems(tracksService.findByArtistName(event.getValue().getName()));
            } catch (NullPointerException e) {
                LOGGER.warn("There's no such artist.");
            }
            layout.addToSecondary(trackDtoGrid);
        });

        setSizeFull();
        add(layout);
    }
}
