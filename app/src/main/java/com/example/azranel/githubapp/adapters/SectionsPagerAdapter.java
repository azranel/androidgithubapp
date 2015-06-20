package com.example.azranel.githubapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.azranel.githubapp.fragments.FragmentHolder;
import com.example.azranel.githubapp.fragments.IssueListHolderFragment;
import com.example.azranel.githubapp.fragments.RepoHolderFragment;
import com.example.azranel.githubapp.fragments.RepoListHolderFragment;
import com.example.azranel.githubapp.fragments.UserholderFragment;
import com.example.azranel.githubapp.models.Issue;
import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.models.User;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by azranel on 10.05.15.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter implements Serializable {
    private List<FragmentHolder> list = new LinkedList<>();
    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public void addFragment(FragmentHolder fragment) throws IllegalArgumentException {
        if(fragment != null)
            list.add(fragment);
        else
            throw new IllegalArgumentException("fragment is null");
    }

    public <T> void addNewSection(String sectionName, Object data)
            throws IllegalArgumentException {
        FragmentHolder fragment = null;
        switch (sectionName) {
            case Repo.LIST_SECTIONS_NAME:
                fragment = RepoListHolderFragment.newInstance((List<Repo>) data);
                break;
            case User.SECTIONS_NAME:
                fragment = UserholderFragment.newInstance((User) data);
                break;
            case Repo.SINGLE_SECTION_NAME:
                fragment = RepoHolderFragment.newInstance((Repo) data);
                break;
            case Issue.LIST_SECTION_NAME:
                //fragment = new IssueListHolderFragment(context, sectionName, new IssueAdapter(context, (List<Issue>) data));
                fragment = IssueListHolderFragment.newInstance((List<Issue>) data);
                break;
        }
        if(fragment != null)
            list.add(fragment);
        else throw new IllegalArgumentException(("sectionName " + sectionName + " is invalid"));
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        String title = list.get(position).getSectionName().toUpperCase(l);

        return title;
    }
}
