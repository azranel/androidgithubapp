package com.example.azranel.githubapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by azranel on 30.05.15.
 */
public class UsersAdapter extends BaseAdapter {
    private List<User> usersList;
    private Context context;

    public UsersAdapter(List<User> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return usersList.size();
    }

    @Override
    public Object getItem(int position) {
        return usersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = (User) getItem(position);
        View finalView;
        if(convertView==null) {
            finalView = LayoutInflater.from(context).inflate(R.layout.listview_user_item, parent, false);
        } else finalView = convertView;

        TextView userLogin = (TextView) finalView.findViewById(R.id.user_login);
        ImageView userImage = (ImageView) finalView.findViewById(R.id.user_image);

        userLogin.setText(user.getLogin());
        Picasso.with(context).load(user.getAvatarUrl()).into(userImage);

        return finalView;
    }
}
