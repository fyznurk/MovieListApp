package com.fyznurk.movielistapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String imagePath = "https://image.tmdb.org/t/p/w500";
    public static final String apiKey="8a4a3388f38db9abc8fc676c9c8c48ca";
    public static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static DateFormat format2 = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);

}
