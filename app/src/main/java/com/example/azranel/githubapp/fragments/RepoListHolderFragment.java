package com.example.azranel.githubapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.RepoDetailsActivity;
import com.example.azranel.githubapp.adapters.ReposAdapter;
import com.example.azranel.githubapp.models.Repo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by azranel on 10.05.15.
 */
public class RepoListHolderFragment extends FragmentHolder {



    public BaseAdapter getListAdapter() {
        return listAdapter;
    }

    private List<Repo> repos;
    private BaseAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repos = (List<Repo>) getArguments().getSerializable(Repo.REPOS_KEY);
        if(listAdapter == null) {
            listAdapter = new ReposAdapter(getActivity(), repos);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Repo.LIST_SECTIONS_NAME, (Serializable) repos);
    }

    public static RepoListHolderFragment newInstance(List<Repo> repos) {
        RepoListHolderFragment fragment = new RepoListHolderFragment();
        Bundle args = new Bundle();
        args.putSerializable(Repo.REPOS_KEY, (Serializable) repos);
        fragment.setArguments(args);
        return fragment;
    }

    public RepoListHolderFragment() {
        this.sectionName = Repo.LIST_SECTIONS_NAME;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_list_section, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Repo repo = (Repo) listAdapter.getItem(position);

                Intent intent = new Intent(context, RepoDetailsActivity.class);
                intent.putExtra(Repo.REPO_KEY, repo);
                startActivity(intent);
            }
        });
        listView.setAdapter(listAdapter);
        return rootView;
    }
}
