package com.example.azranel.githubapp.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by azranel on 04.05.15.
 */
public class User implements Serializable {
    public static final String SECTIONS_NAME = "User";
    public static final String USER_LIST_KEY = "USERS_LIST_KEY";
    public static final String LIST_SECTION_NAME = "LIST_SECTION_NAME";
    private static User loggedInUser;

    public static final String USER_KEY = "USER_KEY_123";
    private String login;

    public String getLogin() {
        if(login != null)
            return login;
        else return "None";
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    private String password;
    private String avatarUrl;
    private String name;
    private int followers;
    private int following;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        User.loggedInUser = loggedInUser;
    }


    public static User fromJSON(String responseBody) throws JSONException {
        JSONObject userObject = new JSONObject(responseBody);
        return fromJSONObject(userObject, false);
    }

    public static User fromJSONObject(JSONObject userObject, boolean isOwner) throws JSONException {
        User user = new User();
        user.setLogin(userObject.getString("login"));
        user.setAvatarUrl(userObject.getString("avatar_url"));
        if(!isOwner) {
            user.setFollowers(userObject.getInt("followers"));
            user.setFollowing(userObject.getInt("following"));
        }
        return user;
    }

    public static List<User> listFromJSON(String jsonString) throws JSONException {
        List<User> users = new LinkedList<>();
        JSONArray usersArray = new JSONArray(jsonString);
        for(int i = 0; i < usersArray.length(); i++) {
            JSONObject singleUser = usersArray.getJSONObject(i);
            users.add(fromJSONObject(singleUser, true));
        }
        return users;
    }
}
