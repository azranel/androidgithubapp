package com.example.azranel.githubapp.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by azranel on 10.05.15.
 */
public abstract class FragmentHolder extends Fragment {
    protected static final String ADAPTER_KEY = "Adapter_key";
    protected static final String DATA_KEY = "Data_key";
    protected static final String SECTION_NAME_KEY = "SectionName_key";
    protected static final String CONTEXT_KEY = "Context_key";



    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    protected String sectionName;

}
