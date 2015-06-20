package com.example.azranel.githubapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.asynctasks.ReadmeDownloadAndSetTask;
import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.models.User;
import com.example.azranel.githubapp.utils.CharStreams;


import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by azranel on 31.05.15.
 */
public class RepoHolderFragment extends FragmentHolder {
    private Repo repo;

    private TextView author;
    private TextView language;
    private TextView description;
    private TextView title;
    private WebView readmeContent;

    public RepoHolderFragment() {
        this.sectionName = Repo.SINGLE_SECTION_NAME;
    }


    public static RepoHolderFragment newInstance(Repo repo) {
        RepoHolderFragment fragment = new RepoHolderFragment();
        Bundle args = new Bundle();
        args.putSerializable(Repo.REPO_KEY, repo);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repo = (Repo) getArguments().getSerializable(Repo.REPO_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Repo.REPO_KEY, repo);
        super.onSaveInstanceState(outState);
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
        readmeContent = (WebView) view.findViewById(R.id.repo_details_readme);
    }

    private void setupView() {
        author.setText(repo.getOwner().getLogin());
        language.setText(repo.getLanguage());
        description.setText(repo.getDescription());
        title.setText(repo.getName());
        readmeContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });
        setReadmeContent();
    }

    private void setReadmeContent() {
        new ReadmeDownloadAndSetTask(readmeContent, repo).execute();
    }
}
