package com.example.azranel.githubapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.adapters.UsersAdapter;
import com.example.azranel.githubapp.api.EndpointBuilder;
import com.example.azranel.githubapp.api.GithubEndpoint;
import com.example.azranel.githubapp.asynctasks.UsersListSetterTask;
import com.example.azranel.githubapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by azranel on 10.05.15.
 */
public class UserholderFragment extends FragmentHolder {
    private ImageView userPhoto;
    private TextView userLogin;
    private TextView followersNumber;
    private TextView followingNumber;
    private ListView followersList;
    private ListView followingList;

    private final Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_section, container, false);
        injectViews(view);

        User user = User.getLoggedInUser();

        Picasso.with(view.getContext()).load(user.getAvatarUrl()).into(userPhoto);
        userLogin.setText(user.getLogin());
        followersNumber.setText("Followers: " + user.getFollowers());
        followingNumber.setText("Following: " + user.getFollowing());

//        new UsersListSetterTask(user, context).setFollowersNumber(followersNumber)
//                .setFollowingNumber(followingNumber).setFollowersListView(followersList)
//                .setFollowingListView(followingList).execute();
        addAdaptersToListViews(user);

        return view;
    }

    private void injectViews(View view) {
        userPhoto = (ImageView) view.findViewById(R.id.user_photo);
        userLogin = (TextView) view.findViewById(R.id.login_textview);
        followersNumber = (TextView) view.findViewById(R.id.followers_number_textview);
        followingNumber = (TextView) view.findViewById(R.id.following_number_textview);
        followersList = (ListView) view.findViewById(R.id.followers_list);
        followingList = (ListView) view.findViewById(R.id.following_list);

    }

    private void addAdaptersToListViews(final User user) {
        new UsersListSetterTask(user, context, true) {
            @Override
            protected void onPostExecute(List<User> users) {
                Log.v("GITHUB", "Downloaded followers");
                followersList.setAdapter(new UsersAdapter(users, context));
            }
        }.setUsersListView(followersList).execute();
        new UsersListSetterTask(user, context, false) {
            @Override
            protected void onPostExecute(List<User> users) {
                Log.v("GITHUB", "Downloaded following");
                followingList.setAdapter(new UsersAdapter(users, context));
            }
        }.setUsersListView(followingList).execute();

//        GithubEndpoint api = new EndpointBuilder().createServiceWithAuth(user.getLogin(), user.getPassword());
//        api.getFollowersForUser(user.getLogin(), new Callback<List<User>>() {
//            @Override
//            public void success(List<User> users, Response response) {
//                Log.v("GITHUB", "Downloaded followers");
//                followersList.setAdapter(new UsersAdapter(users, context));
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e("GITHUB", "Failed to download followers");
//                error.printStackTrace();
//            }
//        });
//        api.getFollowingForUser(user.getLogin(), new Callback<List<User>>() {
//            @Override
//            public void success(List<User> users, Response response) {
//                Log.v("GITHUB", "Downloaded following");
//                followingList.setAdapter(new UsersAdapter(users, context));
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e("GITHUB", "Failed to download following");
//                error.printStackTrace();
//            }
//        });
    }

    public UserholderFragment(String sectionName, Context context) {
        this.sectionName = sectionName;
        this.context = context;
    }
}
