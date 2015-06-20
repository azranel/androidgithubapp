package com.example.azranel.githubapp.asynctasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;

import com.example.azranel.githubapp.adapters.IssueAdapter;
import com.example.azranel.githubapp.api.GithubClient;
import com.example.azranel.githubapp.models.Issue;
import com.example.azranel.githubapp.models.IssueComment;
import com.example.azranel.githubapp.utils.CharStreams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by azranel on 04.06.15.
 */
public class AddCommentToIssueTask extends AsyncTask<JSONObject, Integer, IssueComment> {
    private Issue issue;


    public AddCommentToIssueTask(Issue issue) {
        this.issue = issue;
    }

    @Override
    protected IssueComment doInBackground(JSONObject... params) {
        JSONObject body = params[0];
        GithubClient client = new GithubClient();
        InputStream stream = client.postCommentToIssue(issue.getIssueRepoOwner().getLogin(),
                issue.getRepoName(), issue.getNumber(), body);
        String response = CharStreams.toString(stream);
        IssueComment comment = null;
        try {
            comment = IssueComment.fromJSON(response);
        } catch (JSONException e) {
            Log.e("GITHUB", "Failed to parse JSON into POJO");
            e.printStackTrace();
        }
        return comment;
    }
}
