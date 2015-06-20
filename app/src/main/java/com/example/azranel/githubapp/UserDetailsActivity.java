package com.example.azranel.githubapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.azranel.githubapp.adapters.SectionsPagerAdapter;
import com.example.azranel.githubapp.asynctasks.DownloadReposAndSetThemOnAdapterTask;
import com.example.azranel.githubapp.asynctasks.UsersListSetterTask;
import com.example.azranel.githubapp.fragments.FragmentHolder;
import com.example.azranel.githubapp.fragments.UsersListHolderFragment;
import com.example.azranel.githubapp.models.User;

import java.util.List;


public class UserDetailsActivity extends ActionBarActivity implements ActionBar.TabListener {


    private SectionsPagerAdapter pagerAdapter;
    private User user;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        user = getUserFromIntentOrLoggedIn();
        injectViews();
        initializeView(this);
    }

    private void injectViews() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
    }

    private void initializeView(final Context context) {
        setTitle(user.getLogin());
        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), context);
        pagerAdapter.addNewSection(User.SECTIONS_NAME, user);
        getFollowersAndAddThemToAdapter();
        getFollowingAndAddThemToAdapter();
        getReposAndAddThemToAdapter();
    }

    private void getFollowingAndAddThemToAdapter() {
        UsersListSetterTask task = new UsersListSetterTask(user, this, false) {
            @Override
            protected void onPostExecute(List<User> users) {
                FragmentHolder followingFragment =
                        UsersListHolderFragment.newInstance(users, "Following");
                pagerAdapter.addFragment(followingFragment);
            }
        };
        task.execute();
    }

    private void getFollowersAndAddThemToAdapter() {
        UsersListSetterTask task = new UsersListSetterTask(user, this, true) {
            @Override
            protected void onPostExecute(List<User> users) {
                FragmentHolder followersFragment =
                        UsersListHolderFragment.newInstance(users, "Followers");
                pagerAdapter.addFragment(followersFragment);
            }
        };
        task.execute();
    }

    private User getUserFromIntentOrLoggedIn() {
        User user = null;
        Intent intent = getIntent();
        if(intent.hasExtra(User.USER_KEY))
            user = (User) intent.getSerializableExtra(User.USER_KEY);
        else user = User.getLoggedInUser();
        return user;
    }

    private void getReposAndAddThemToAdapter() {
        DownloadReposAndSetThemOnAdapterTask task = new DownloadReposAndSetThemOnAdapterTask(pagerAdapter, user) {
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
