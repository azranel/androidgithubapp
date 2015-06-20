package com.example.azranel.githubapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Repo implements Serializable{

    public static final String REPO_KEY = "REPO_KEY_312";
    public static final String LIST_SECTIONS_NAME = "Repos";
    public static final String SINGLE_SECTION_NAME = "Repo";
    public static final String REPOS_KEY = "Repos_KEY";

    private String name;
    private String fullName;

    public String getLanguage() {
        if(language.contains("null") || language == null)
            return "No language";
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private String language;
    private User owner;
    private Boolean _private;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Boolean is_private() {
        return _private;
    }

    public void set_private(Boolean _private) {
        this._private = _private;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String htmlUrl;
    private String description;


    public static List<Repo> listFromJSON(String responseBody) throws JSONException {
        List<Repo> list = new LinkedList<>();
        JSONArray reposArray = new JSONArray(responseBody);
        for(int i = 0; i < reposArray.length(); i++) {
            JSONObject repo = reposArray.getJSONObject(i);
            list.add(fromJSONObject(repo));
        }
        return list;
    }

    private static Repo fromJSONObject(JSONObject repoObject) throws JSONException {
        Repo repo = new Repo();
        repo.setOwner(User.fromJSONObject(repoObject.getJSONObject("owner"), true));
        repo.setDescription(repoObject.getString("description"));
        repo.setLanguage(repoObject.getString("language"));
        repo.setName(repoObject.getString("name"));
        return repo;
    }
}
