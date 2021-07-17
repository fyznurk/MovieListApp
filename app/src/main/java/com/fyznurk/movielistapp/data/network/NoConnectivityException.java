package com.fyznurk.movielistapp.data.network;

import com.fyznurk.movielistapp.MyApp;
import com.fyznurk.movielistapp.R;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return MyApp.getmContext().getString(R.string.check_internet);
    }
}
