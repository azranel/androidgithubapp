package com.example.azranel.githubapp.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by azranel on 10.05.15.
 */
public abstract class FragmentHolder extends Fragment {
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    protected String sectionName;

}
