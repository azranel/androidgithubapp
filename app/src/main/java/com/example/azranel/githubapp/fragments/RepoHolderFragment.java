package com.example.azranel.githubapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.api.EndpointBuilder;
import com.example.azranel.githubapp.api.GithubEndpoint;
import com.example.azranel.githubapp.asynctasks.ReadmeDownloadAndSetTask;
import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.models.User;
import com.example.azranel.githubapp.utils.CharStreams;


import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by azranel on 31.05.15.
 */
public class RepoHolderFragment extends FragmentHolder {
    private final Context context;
    private final Repo repo;

    private TextView author;
    private TextView language;
    private TextView description;
    private TextView title;
    private TextView readmeContent;

    public RepoHolderFragment(Context context, Repo repo, String sectionName) {
        this.context = context;
        this.repo = repo;
        this.sectionName = "Repo";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_repo_section, container, false);
        injectViews(rootView);
        setupView();
        return rootView;
    }

    private void injectViews(View view) {
        author = (TextView) view.findViewById(R.id.repo_details_author);
        language = (TextView) view.findViewById(R.id.repo_details_language);
        description = (TextView) view.findViewById(R.id.repo_details_desrciption);
        title = (TextView) view.findViewById(R.id.repo_details_title);
        readmeContent = (TextView) view.findViewById(R.id.repo_details_readme);
    }

    private void setupView() {
        author.setText(repo.getOwner().getLogin());
        language.setText(repo.getLanguage());
        description.setText(repo.getDescription());
        title.setText(repo.getName());
        setReadmeContent();
    }

    private void setReadmeContent() {
        new ReadmeDownloadAndSetTask(readmeContent, repo).execute();
//        GithubEndpoint api = new EndpointBuilder().createServiceWithAuth(
//                User.getLoggedInUser().getLogin(), User.getLoggedInUser().getPassword());
//
//
//        api.getReadmeForOwnerRepo(repo.getOwner().getLogin(), repo.getName(),
//                new Callback<Response>() {
//                    @Override
//                    public void success(Response response, Response response2) {
//                        String result = null;
//                        try {
//                            result = CharStreams.toString(new InputStreamReader(response.getBody().in()));
//                        } catch (IOException e) {
//                            Log.e("GITHUB", "Could not parse README.md from response");
//                            e.printStackTrace();
//                        }
//
//
//                        readmeContent.setText(Html.fromHtml(result));
//                    }
//
//                    @Override
//                    public void failure(RetrofitError error) {
//                        Log.e("GITHUB", "Getting README.md failed");
//                        readmeContent.setText("No README.md");
//                        error.printStackTrace();
//                    }
//                });
    }
}
