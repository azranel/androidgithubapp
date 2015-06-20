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
import com.example.azranel.githubapp.asynctasks.UsersListSetterTask;
import com.example.azranel.githubapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by azranel on 10.05.15.
 */
public class UserholderFragment extends FragmentHolder {
    private ImageView userPhoto;
    private TextView userLogin;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getArguments().getSerializable(User.USER_KEY);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(User.USER_KEY, user);
        Log.e("GITHUB", "Saving state of UserholderFragment");
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_section, container, false);

        injectViews(view);

        Picasso.with(view.getContext()).load(user.getAvatarUrl()).into(userPhoto);
        userLogin.setText(user.getLogin());

        return view;
    }

    private void injectViews(View view) {
        userPhoto = (ImageView) view.findViewById(R.id.user_photo);
        userLogin = (TextView) view.findViewById(R.id.login_textview);
    }

    public static UserholderFragment newInstance(User user) {
        UserholderFragment fragment = new UserholderFragment();
        Bundle args = new Bundle();
        args.putSerializable(User.USER_KEY, user);
        fragment.setArguments(args);
        return fragment;
    }

    public UserholderFragment() {
        this.sectionName = User.SECTIONS_NAME;
    }

}
