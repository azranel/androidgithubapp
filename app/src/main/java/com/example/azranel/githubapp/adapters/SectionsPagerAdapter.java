package com.example.azranel.githubapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.azranel.githubapp.fragments.FragmentHolder;
import com.example.azranel.githubapp.fragments.RepoHolderFragment;
import com.example.azranel.githubapp.fragments.RepoListHolderFragment;
import com.example.azranel.githubapp.fragments.UserholderFragment;
import com.example.azranel.githubapp.models.Repo;
import com.example.azranel.githubapp.models.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by azranel on 10.05.15.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private List<FragmentHolder> list = new LinkedList<>();
    private Context context;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public <T> void addNewSection(String sectionName, Object data)
            throws IllegalArgumentException {
        FragmentHolder fragment = null;
        switch (sectionName) {
            case Repo.LIST_SECTIONS_NAME:
                fragment = new RepoListHolderFragment(context, sectionName, new ReposAdapter(context, (List<Repo>) data));
                break;
            case User.SECTIONS_NAME:
                fragment = new UserholderFragment(sectionName, context);
                break;
            case Repo.SINGLE_SECTION_NAME:
                fragment = new RepoHolderFragment(context,(Repo) data, sectionName);
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
