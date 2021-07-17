package com.fyznurk.movielistapp.data.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ApiInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request.Builder rqBuilder = chain.request().newBuilder();
        rqBuilder.addHeader("Accept", "application/json");
        return chain.proceed(rqBuilder.build());
    }
}
