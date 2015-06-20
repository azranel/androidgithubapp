package com.example.azranel.githubapp.asynctasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.azranel.githubapp.UserDetailsActivity;
import com.example.azranel.githubapp.adapters.UsersAdapter;
import com.example.azranel.githubapp.api.GithubClient;
import com.example.azranel.githubapp.models.User;
import com.example.azranel.githubapp.utils.CharStreams;

import org.json.JSONException;

import java.io.InputStream;
import java.util.List;

/**
 * Created by azranel on 30.05.15.
 */
public class UsersListSetterTask extends AsyncTask<Object, Integer, List<User>> {
    private User user;
    private ListView usersListView;
    private TextView numberTextView;
    private Context context;
    /**
     * If true - download followers, if false - download following
     */
    private boolean isFollowers;

    public UsersListSetterTask(User user, Context context, boolean isFollowers) {
        this.user = user;
        this.context = context;
        this.isFollowers = isFollowers;
    }

    public UsersListSetterTask setUsersListView(ListView listView) {
        this.usersListView = listView;
        setOnItemListener(this.usersListView);
        return this;
    }

    public UsersListSetterTask setNumberTextView(TextView textView) {
        this.numberTextView = textView;
        return this;
    }

    @Override
    protected void onPostExecute(List<User> users) {
        if(users != null) {
            usersListView.setAdapter(new UsersAdapter(users, context));
            if(numberTextView != null) {
                String whatTextView = isFollowers ? "Followers: " : "Following: ";
                numberTextView.setText(whatTextView + String.valueOf(users.size()));
            }
        } else throw new NullPointerException("users in onPostExecute is null");
    }

    private List<User> getUsersForFollowingList() {
        GithubClient client = new GithubClient();
        InputStream followingStream = client.getFollowing(user.getLogin());
        List<User> following = null;
        try {
            following = User.listFromJSON(CharStreams.toString(followingStream));
        } catch (JSONException e) {
            Log.e("GITHUB", "Unable to download followers");
            e.printStackTrace();
        }
        return following;
    }

    private List<User> getUsersForFollowersList() {
        GithubClient client = new GithubClient();
        InputStream followersStream = client.getFollowers(user.getLogin());
        List<User> followers = null;
        try {
            followers = User.listFromJSON(CharStreams.toString(followersStream));
        } catch (JSONException e) {
            Log.e("GITHUB", "Unable to download followers");
            e.printStackTrace();
        }
        return followers;
    }

    private void setOnItemListener(final ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, UserDetailsActivity.class);
                intent.putExtra(User.USER_KEY, (User) listView.getItemAtPosition(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected List<User> doInBackground(Object[] params) {
        List<User> users = null;
        users = isFollowers ? getUsersForFollowersList() : getUsersForFollowingList();
        return users;
    }
}
