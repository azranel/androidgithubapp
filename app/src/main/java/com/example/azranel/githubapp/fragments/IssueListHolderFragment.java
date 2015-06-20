package com.example.azranel.githubapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.azranel.githubapp.IssueDetailsActivity;
import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.RepoDetailsActivity;
import com.example.azranel.githubapp.adapters.IssueAdapter;
import com.example.azranel.githubapp.models.Issue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by azranel on 04.06.15.
 */
public class IssueListHolderFragment extends FragmentHolder {
    private BaseAdapter adapter;
    private List<Issue> issues;

    public IssueListHolderFragment() {
        this.sectionName = Issue.LIST_SECTION_NAME;
    }

    public static IssueListHolderFragment newInstance(List<Issue> issues) {
        IssueListHolderFragment fragment = new IssueListHolderFragment();
        Bundle args = new Bundle();
        args.putSerializable(Issue.LIST_OF_ISSUES, (Serializable) issues);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        issues = (List<Issue>) getArguments().getSerializable(Issue.LIST_OF_ISSUES);
        if(adapter == null) {
            adapter = new IssueAdapter(getActivity(), issues);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Issue.LIST_OF_ISSUES, (Serializable) issues);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_section, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Issue issue = (Issue) adapter.getItem(position);

                Intent intent = new Intent(getActivity(), IssueDetailsActivity.class);
                intent.putExtra(Issue.ISSUE_KEY, issue);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
        return rootView;
    }
}
