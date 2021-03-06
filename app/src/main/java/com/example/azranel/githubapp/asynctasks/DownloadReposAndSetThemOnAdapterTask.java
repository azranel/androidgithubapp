package com.example.azranel.githubapp.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.azranel.githubapp.adapters.SectionsPagerAdapter;
import com.example.azranel.githubapp.api.GithubClient;
import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.models.User;
import com.example.azranel.githubapp.utils.CharStreams;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

/**
 * Created by azranel on 01.06.15.
 */
public class DownloadReposAndSetThemOnAdapterTask extends AsyncTask {
    private final SectionsPagerAdapter adapter;
    private User user;

    public DownloadReposAndSetThemOnAdapterTask(SectionsPagerAdapter adapter, User user) {
        this.adapter = adapter;
        this.user = user;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        GithubClient client = new GithubClient();
        InputStream is = client.getRepos(user.getLogin());
        //InputStream is = client.getResource(GithubClient.REPOS_PATH, false);
        String responseBody = CharStreams.toString(is);
        try {
            List<Repo> repos = Repo.listFromJSON(responseBody);
            adapter.addNewSection(Repo.LIST_SECTIONS_NAME, repos);
        } catch (JSONException e) {
            Log.e("GITHUB", "Failed to download repos");
            e.printStackTrace();
        }
        return null;
    }
}
