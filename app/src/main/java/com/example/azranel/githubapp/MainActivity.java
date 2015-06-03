package com.example.azranel.githubapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.azranel.githubapp.asynctasks.LoginToGitHubTask;


public class MainActivity extends Activity {


    private EditText login;
    private EditText password;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        injectViews();
    }

    private void injectViews() {
        login = (EditText) findViewById(R.id.login_activity_login_edittext);
        password = (EditText) findViewById(R.id.login_activity_password_edittext);
        title = (TextView) findViewById(R.id.login_activity_title);
    }

    public void loginToGitHub(View view) {
        final String userLogin = login.getText().toString();
        final String userPassword = password.getText().toString();

        LoginToGitHubTask task = new LoginToGitHubTask(this);
        task.execute(userLogin, userPassword);
    }


}
