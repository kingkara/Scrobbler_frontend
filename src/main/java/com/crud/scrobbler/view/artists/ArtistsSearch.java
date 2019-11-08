package com.crud.scrobbler.view.artists;

import com.crud.scrobbler.view.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "artistsSearch", layout = MainView.class)
@PageTitle("Find Artist")
public class ArtistsSearch extends Div {
    public ArtistsSearch() {
    }
}
