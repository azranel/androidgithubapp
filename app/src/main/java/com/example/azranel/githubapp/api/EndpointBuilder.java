package com.example.azranel.githubapp.api;

import android.util.Base64;

import com.example.azranel.githubapp.models.User;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by azranel on 09.05.15.
 */
public class EndpointBuilder {
    private final static String GENERAL_URL = "https://api.github.com";
    private final RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(GENERAL_URL)
            .setLogLevel(RestAdapter.LogLevel.NONE);
    private final static Class<GithubEndpoint> CLASS = GithubEndpoint.class;

    public GithubEndpoint createService() {
        RestAdapter adapter = builder.build();

        return adapter.create(CLASS);
    }

    public GithubEndpoint createServiceWithAuth(String username, String password) {
        if (username != null && password != null) {
            // concatenate username and password with colon for authentication
            final String credentials = username + ":" + password;

            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    // create Base64 encodet string
                    final String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", string);
                }
            });
        }

        RestAdapter adapter = builder.build();

        return adapter.create(CLASS);
    }

    public GithubEndpoint createServiceWithAuth(User user) {
        return createServiceWithAuth(user.getLogin(), user.getPassword());
    }
}
