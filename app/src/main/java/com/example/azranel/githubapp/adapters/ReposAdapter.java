package com.example.azranel.githubapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.models.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by azranel on 09.05.15.
 */
public class ReposAdapter extends BaseAdapter {
    public final static String SECTIONS_NAME = "Repos";
    private List<Repo> repoList;
    private Context context;

    public ReposAdapter(final Context context, List<Repo> repoList) {
        this.context = context;
        this.repoList = repoList;
    }

    @Override
    public int getCount() {
        return repoList.size();
    }

    @Override
    public Object getItem(int position) {
        return repoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Repo repo = (Repo) getItem(position);
        View finalView;
        if(convertView==null) {
            finalView = LayoutInflater.from(context).inflate(R.layout.listview_repo_item, parent, false);
        } else finalView = convertView;

        TextView title = (TextView) finalView.findViewById(R.id.repo_item_title);
        TextView language = (TextView) finalView.findViewById(R.id.repo_item_language);

        title.setText(repo.getName());
        language.setText(repo.getLanguage());

        return finalView;
    }
}
