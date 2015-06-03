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
import com.example.azranel.githubapp.models.Repo;

/**
 * Created by azranel on 10.05.15.
 */
public class RepoListHolderFragment extends FragmentHolder {


    private final Context context;

    public BaseAdapter getListAdapter() {
        return listAdapter;
    }

    private final BaseAdapter listAdapter;

    public RepoListHolderFragment(Context context, String sectionName, BaseAdapter adapter) {
        this.context = context;
        this.sectionName = sectionName;
        this.listAdapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_repolist_section, container, false);
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
