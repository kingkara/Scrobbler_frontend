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
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "artistsSearch", layout = MainView.class)
@PageTitle("Find Artist")
public class ArtistsSearchView extends Div {
    private Grid<ArtistDto> artistGrid = new Grid<>();
    private TextField filter = new TextField();

    public ArtistsSearchView(@Autowired ArtistsService artistsService, @Autowired TracksService tracksService) {
        SplitLayout layout = new SplitLayout(
                new Label("Artist List"),
                new Label("Artists track - click artist to see its tracks"));

        try {
            //SEARCH
            filter.setPlaceholder("Filter by title");
            filter.setClearButtonVisible(true);
            filter.setValueChangeMode(ValueChangeMode.EAGER);
            filter.addValueChangeListener(e -> artistGrid.setItems(artistsService.findByName(filter.getValue())));

            //ARTISTS LIST
            artistGrid.addColumn(ArtistDto::getName).setHeader("Artist name").setTextAlign(ColumnTextAlign.CENTER);
            artistGrid.setItems(artistsService.getArtists());
            artistGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

            layout.setSplitterPosition(50);
            layout.addToPrimary(filter, artistGrid);

            //TRACKS DISPLAY
            artistGrid.asSingleSelect().addValueChangeListener(event -> {
                Grid<TrackDto> trackDtoGrid = new TreeGrid<>();
                trackDtoGrid.addColumn(TrackDto::getTitle).setHeader("Title").setTextAlign(ColumnTextAlign.CENTER);
                System.out.println(tracksService.findByArtistName(event.getValue().getName()).size());
                trackDtoGrid.setItems(tracksService.findByArtistName(event.getValue().getName()));
                layout.addToSecondary(trackDtoGrid);
            });

            setSizeFull();
            add(layout);
        } catch (Exception ignore) {
        }
    }
}
