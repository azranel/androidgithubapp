package com.example.azranel.githubapp.api;

import android.util.Base64;
import android.util.Log;

import com.example.azranel.githubapp.models.User;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by azranel on 01.06.15.
 */
public class GithubClient {
    private static final String ROOT_URL = "https://api.github.com";
    public static final String SINGLE_USER_PATH = "/users/";
    public static final String LOGIN_PATH = "/user";
    public static final String REPOS_PATH = "/user/repos";
    public static final int TIMEOUT_MILLIS = 10000;
    public static final int CONNECT_TIMEOUT_MILLIS = 15000;

    public enum HTTPMethods {
        GET,
        POST,
        DELETE,
        PUT
    }


    public InputStream getResource(String path) {
        URL url = buildURL(path);
        InputStream is = null;
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) url.openConnection();
            setupConnection(conn, HTTPMethods.GET);

            conn.connect();
            is = conn.getInputStream();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    private void setupConnection(HttpURLConnection conn, HTTPMethods method) throws ProtocolException {
        conn.setReadTimeout(TIMEOUT_MILLIS);
        conn.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
        conn.setRequestMethod(method.toString());
        if(User.getLoggedInUser() != null) {
            String credientials = credientials();
            conn.setRequestProperty("Authorization", credientials);
        }
    }

    private String credientials() {
        User user = User.getLoggedInUser();
        String credientials = user.getLogin() + ":" + user.getPassword();
        return "Basic " + Base64.encodeToString(credientials.getBytes(), Base64.NO_WRAP);
    }

    private String credientials(String username, String password) {
        String credientials = username + ":" + password;
        return "Basic " + Base64.encodeToString(credientials.getBytes(), Base64.NO_WRAP);
    }

    public InputStream login(String username, String password) {
        URL url = buildURL(LOGIN_PATH);
        InputStream is = null;
        HttpURLConnection conn;
        final String string = credientials(username, password);

        try {
            conn = (HttpURLConnection) url.openConnection();
            setupConnection(conn, HTTPMethods.GET);

            conn.setRequestProperty("Authorization", string);

            conn.connect();

            int status = conn.getResponseCode();
            Log.v("GITHUB", "Status code: " + String.valueOf(status));

            if(status < 400)
                is = conn.getInputStream();
            else
                is = conn.getErrorStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

    public InputStream getReadme(String owner, String repoName) {
        String path = "/repos/" + owner + "/" + repoName + "/contents/README.md";
        return getResource(path);
    }

    public InputStream getFollowers(String owner) {
        String path = "/users/" + owner + "/followers";
        return getResource(path);
    }

    public InputStream getFollowing(String owner) {
        String path = "/users/" + owner + "/following";
        return getResource(path);
    }


    private URL buildURL(String path) {
        URL url = null;
        try {
            url = new URL(ROOT_URL + path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("GITHUB", "Failed to build URL");
        }
        return url;
    }
}
