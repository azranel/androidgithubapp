package com.example.azranel.githubapp.asynctasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.azranel.githubapp.UserDetailsActivity;
import com.example.azranel.githubapp.api.GithubClient;
import com.example.azranel.githubapp.models.User;
import com.example.azranel.githubapp.utils.CharStreams;

import org.json.JSONException;

import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by azranel on 01.06.15.
 */
public class LoginToGitHubTask extends AsyncTask<String, Integer, User> {

    private final Context context;
    private boolean isAuthenticated;

    public LoginToGitHubTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(User user) {
        if(isAuthenticated) {
            Intent intent = new Intent(context, UserDetailsActivity.class);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Wrong login or password!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected User doInBackground(String... params) {
        GithubClient client = new GithubClient();
        String username = params[0];
        String password = params[1];
        InputStream is = client.login(username, password);
        String responseBody = CharStreams.toString(
                new InputStreamReader(is));
        Log.v("GITHUB", "Response stream: " + responseBody);
        User user = null;
        try {
            user = User.fromJSON(responseBody);
            user.setPassword(password);
            User.setLoggedInUser(user);
            isAuthenticated = true;
        } catch (JSONException e) {
            Log.e("GITHUB", "Can't map user from json");
            e.printStackTrace();
            isAuthenticated = false;
        }
        return user;
    }
}
