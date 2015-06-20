package com.example.azranel.githubapp.asynctasks;

import android.os.AsyncTask;
import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.azranel.githubapp.api.GithubClient;
import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.utils.CharStreams;

import java.io.InputStream;

/**
 * Created by azranel on 03.06.15.
 */
public class ReadmeDownloadAndSetTask extends AsyncTask<Object, Integer, String> {
    private WebView readmeView;
    private Repo repo;

    @Override
    protected void onPostExecute(String readmeHTML) {
//        readmeView.setText(Html.fromHtml(readmeHTML));
        readmeView.loadData(readmeHTML, "text/html", "utf-8");
    }

    public ReadmeDownloadAndSetTask(WebView readmeView, Repo repo) {
        this.readmeView = readmeView;
        this.repo = repo;
    }


    @Override
    protected String doInBackground(Object[] params) {
        GithubClient client = new GithubClient();
        InputStream stream = client.getReadme(repo.getOwner().getLogin(), repo.getName());
        String readme = CharStreams.toString(stream);
        readme = checkIfNotError(readme);
        return readme;
    }

    private String checkIfNotError(String readme) {
        if(readme.contains("Not Found") || readme.contains("This repository is empty"))
            return "<h2>No README.md</h2>";
        else return readme;
    }
}
