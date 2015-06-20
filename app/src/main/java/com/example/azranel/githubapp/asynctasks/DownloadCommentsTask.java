package com.example.azranel.githubapp.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.azranel.githubapp.adapters.CommentsAdapter;
import com.example.azranel.githubapp.api.GithubClient;
import com.example.azranel.githubapp.models.Issue;
import com.example.azranel.githubapp.models.IssueComment;
import com.example.azranel.githubapp.utils.CharStreams;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

/**
 * Created by azranel on 04.06.15.
 */
public class DownloadCommentsTask extends AsyncTask<Object, Integer, List<IssueComment>> {

    private final Issue issue;

    public DownloadCommentsTask(Issue issue) {
        this.issue = issue;
    }

    @Override
    protected List<IssueComment> doInBackground(Object... params) {
        GithubClient client = new GithubClient();
        InputStream commentsStream = client.getCommentsForIssue(issue.getIssueRepoOwner().getLogin(),
                issue.getRepoName(), issue.getNumber());
        String json = CharStreams.toString(commentsStream);
        List<IssueComment> comments = null;
        try {
            comments = IssueComment.listFromJSON(json);
        } catch (JSONException e) {
            Log.e("GITHUB", "Failed to download comments");
            e.printStackTrace();
        }

        return comments;
    }
}
