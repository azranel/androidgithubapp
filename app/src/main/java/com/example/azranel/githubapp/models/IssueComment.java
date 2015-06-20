package com.example.azranel.githubapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by azranel on 04.06.15.
 */
public class IssueComment implements Serializable {
    private String body;
    private User user;

    public static IssueComment fromJSON(String json) throws JSONException {
        JSONObject object = new JSONObject(json);
        return fromJSONObject(object);
    }

    public static IssueComment fromJSONObject(JSONObject object) throws JSONException {
        IssueComment comment = new IssueComment();
        comment.setBody(object.getString("body"));
        comment.setUser(User.fromJSONObject(object.getJSONObject("user"), true));
        return comment;
    }

    public static List<IssueComment> listFromJSON(String json) throws JSONException {
        List<IssueComment> comments = new LinkedList<>();
        JSONArray commentsArray = new JSONArray(json);
        for(int i = 0; i < commentsArray.length(); i++) {
            JSONObject comment = commentsArray.getJSONObject(i);
            comments.add(fromJSONObject(comment));
        }
        return comments;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
