package com.example.movieandtvshowcatalogue.widget.movie;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class MovieStackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MovieStackRemoteViewsFactory(this.getApplicationContext());
    }
}
