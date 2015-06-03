package com.example.azranel.githubapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.example.azranel.githubapp.adapters.SectionsPagerAdapter;
import com.example.azranel.githubapp.models.Repo;



public class RepoDetailsActivity extends ActionBarActivity implements ActionBar.TabListener {

    private ViewPager pager;
    private SectionsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);
        injectViews();
        initializeView();
    }

    private void injectViews() {
        pager = (ViewPager) findViewById(R.id.pager);
    }

    private void initializeView() {
        Repo repo = getRepoFromIntent();
        setTitle(repo.getName());

        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        pagerAdapter.addNewSection(Repo.SINGLE_SECTION_NAME, repo);

        pager.setAdapter(pagerAdapter);
        setupPager();

    }
    private void setupPager() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for(int i = 0; i < pagerAdapter.getCount(); i++) {
            actionBar.addTab(
                actionBar.newTab()
                        .setText(pagerAdapter.getPageTitle(i))
                        .setTabListener(this));
        }

    }

    public Repo getRepoFromIntent() {
        return (Repo) getIntent().getSerializableExtra(Repo.REPO_KEY);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
       pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
