package com.example.azranel.githubapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.azranel.githubapp.asynctasks.UsersListSetterTask;
import com.example.azranel.githubapp.models.User;
import com.squareup.picasso.Picasso;



public class UserDetailsActivity extends ActionBarActivity {
    private ImageView userPhoto;
    private TextView userLogin;
    private TextView followersNumber;
    private TextView followingNumber;
    private ListView followersList;
    private ListView followingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_section);
        injectViews();
        setupView();
    }

    private void injectViews() {
        userPhoto = (ImageView) findViewById(R.id.user_photo);
        userLogin = (TextView) findViewById(R.id.user_login);
        followersNumber = (TextView) findViewById(R.id.followers_number_textview);
        followingNumber = (TextView) findViewById(R.id.following_number_textview);
        followersList = (ListView) findViewById(R.id.followers_list);
        followingList = (ListView) findViewById(R.id.following_list);
    }

    private void setupView() {
        User user = getUserFromIntent();
        setTitle(user.getLogin());
        userLogin.setText(user.getLogin());
        followersNumber.setText("Followers: " + String.valueOf(user.getFollowers()));
        followingNumber.setText("Following: " + String.valueOf(user.getFollowing()));
        Picasso.with(this).load(user.getAvatarUrl()).into(userPhoto);
        new UsersListSetterTask(user, this, true).setUsersListView(followersList).execute();
        new UsersListSetterTask(user, this, false).setUsersListView(followingList).execute();
//        new UsersListSetterTask(user, this).setFollowersListView(followersList)
//                .setFollowingListView(followingList).setFollowersNumber(followersNumber)
//                .setFollowingNumber(followingNumber).execute();
    }

    public User getUserFromIntent() {
        return (User) getIntent().getSerializableExtra(User.USER_KEY);
    }
}
