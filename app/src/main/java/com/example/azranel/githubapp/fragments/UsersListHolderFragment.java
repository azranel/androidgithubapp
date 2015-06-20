package com.example.azranel.githubapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.RepoDetailsActivity;
import com.example.azranel.githubapp.UserDetailsActivity;
import com.example.azranel.githubapp.adapters.UsersAdapter;
import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.models.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by azranel on 19.06.15.
 */
public class UsersListHolderFragment extends FragmentHolder {
    private UsersAdapter adapter;
    private List<User> users;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = (List<User>) getArguments().getSerializable(User.USER_LIST_KEY);
        sectionName = getArguments().getString(User.LIST_SECTION_NAME);
        if(adapter == null) {
            adapter = new UsersAdapter(users, getActivity());
        }
    }

    public static UsersListHolderFragment newInstance(List<User> users, String sectionName) {
        UsersListHolderFragment fragment = new UsersListHolderFragment();
        fragment.sectionName = sectionName;
        Bundle args = new Bundle();
        args.putSerializable(User.USER_LIST_KEY, (Serializable) users);
        args.putString(User.LIST_SECTION_NAME, sectionName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(User.USER_LIST_KEY, (Serializable) users);
        outState.putString(User.LIST_SECTION_NAME, sectionName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final Context context = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_list_section, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.fragment_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = (User) adapter.getItem(position);

                Intent intent = new Intent(context, UserDetailsActivity.class);
                intent.putExtra(User.USER_KEY, user);
                startActivity(intent);
            }
        });
        listView.setAdapter(adapter);
        return rootView;
    }
}
