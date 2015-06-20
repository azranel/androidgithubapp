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
public class Issue implements Serializable {
    public static final String LIST_SECTION_NAME = "Issues list";
    public static final String ISSUE_KEY = "Single_issue";
    public static final String OPEN = "open";
    public static final String CLOSED = "closed";
    public static final String LIST_OF_ISSUES = "IssuesList";
    private String state;
    private String title;
    private String body;
    private String repoName;
    private User issueRepoOwner;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int number;
    private User user;
    private User assignee;
    private int comments;

    public static Issue fromJSON(String json) throws JSONException {
        JSONObject issueJson = new JSONObject(json);
        return fromJSONObject(issueJson);
    }

    public static Issue fromJSONObject(JSONObject object) throws JSONException {
        Issue issue = new Issue();
        issue.setBody(object.getString("body"));
        issue.setTitle(object.getString("title"));
        issue.setState(object.getString("state"));
        issue.setNumber(object.getInt("number"));
        issue.setUser(User.fromJSONObject(object.getJSONObject("user"), true));
        if(!object.isNull("assignee"))
            issue.setAssignee(User.fromJSONObject(object.getJSONObject("assignee"), true));
        issue.setComments(object.getInt("comments"));
        return issue;
    }

    public static List<Issue> listFromJSON(String jsonString) throws JSONException {
        List<Issue> issues = new LinkedList<>();
        JSONArray issuesArray = new JSONArray(jsonString);
        for(int i = 0; i < issuesArray.length(); i++) {
            JSONObject singleIssue = issuesArray.getJSONObject(i);
            issues.add(fromJSONObject(singleIssue));
        }
        return issues;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setIssueRepoOwner(User issueRepoOwner) {
        this.issueRepoOwner = issueRepoOwner;
    }

    public User getIssueRepoOwner() {
        return issueRepoOwner;
    }
}
