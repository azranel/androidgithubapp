package com.example.azranel.githubapp.api;

import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.models.User;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by azranel on 04.05.15.
 */
public interface GithubEndpoint {
    @GET("/user")
    public void login(Callback<User> userCallback);

    @GET("/users/{username}")
    public void getUser(@Path("username") String username, Callback<User> callback);

    @GET("/user/repos")
    public void getRepos(Callback<List<Repo>> callback);

    @Headers({"Accept: application/vnd.github.v3.html"})
    @GET("/repos/{owner}/{repo}/contents/README.md")
    public void getReadmeForOwnerRepo(@Path("owner") String owner,
                                      @Path("repo") String repo,
                                      Callback<Response> callback);

    @GET("/users/{username}/followers")
    public void getFollowersForUser(@Path("username") String userLogin,
                                    Callback<List<User>> callback);

    @GET("/users/{username}/following")
    public void getFollowingForUser(@Path("username") String userLogin,
                                    Callback<List<User>> callback);
}
