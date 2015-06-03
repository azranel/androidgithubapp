package com.example.azranel.githubapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.azranel.githubapp.adapters.SectionsPagerAdapter;
import com.example.azranel.githubapp.api.EndpointBuilder;
import com.example.azranel.githubapp.api.GithubEndpoint;
import com.example.azranel.githubapp.asynctasks.DownloadReposAndSetThemOnAdapterTask;
import com.example.azranel.githubapp.models.User;


public class LoggedInActivity extends ActionBarActivity implements ActionBar.TabListener {


    SectionsPagerAdapter pagerAdapter;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        injectViews();
        initializeView(this);
    }

    private void injectViews() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    private void initializeView(final Context context) {
        User user = User.getLoggedInUser();
        setTitle(user.getLogin());
        GithubEndpoint api = new EndpointBuilder().createServiceWithAuth(user.getLogin(), user.getPassword());
        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), context);
        pagerAdapter.addNewSection(User.SECTIONS_NAME, null);
        getReposAndAddThemToAdapter(api);
    }

    private void getReposAndAddThemToAdapter(GithubEndpoint api) {
        DownloadReposAndSetThemOnAdapterTask task = new DownloadReposAndSetThemOnAdapterTask(pagerAdapter) {
            @Override
            protected void onPostExecute(Object o) {
                setupUI();
            }
        };
        task.execute();
    }

    private void setupUI() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager.setAdapter(pagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(pagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }




    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
