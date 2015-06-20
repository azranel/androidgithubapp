package com.example.azranel.githubapp.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.azranel.githubapp.adapters.SectionsPagerAdapter;
import com.example.azranel.githubapp.api.GithubClient;
import com.example.azranel.githubapp.models.Issue;
import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.utils.CharStreams;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

/**
 * Created by azranel on 04.06.15.
 */
public class DownloadIssuesForRepo extends AsyncTask<Object, Integer, List<Issue>> {

    private Repo repo;
    private SectionsPagerAdapter adapter;

    public DownloadIssuesForRepo(Repo repo) {
        this.repo = repo;
    }

    @Override
    protected List<Issue> doInBackground(Object... params) {
        GithubClient client = new GithubClient();
        InputStream stream = client.getIssues(repo.getOwner().getLogin(), repo.getName());
        String json = CharStreams.toString(stream);
        List<Issue> issues = null;
        try {
            issues = Issue.listFromJSON(json);
        } catch (JSONException e) {
            Log.e("GITHUB", "Can't download issues");
            e.printStackTrace();
        }
        for(Issue i : issues) {
            i.setRepoName(repo.getName());
            i.setIssueRepoOwner(repo.getOwner());
        }
        return issues;
    }
}
