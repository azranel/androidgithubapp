package com.example.azranel.githubapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.azranel.githubapp.adapters.CommentsAdapter;
import com.example.azranel.githubapp.adapters.IssueAdapter;
import com.example.azranel.githubapp.asynctasks.AddCommentToIssueTask;
import com.example.azranel.githubapp.asynctasks.DownloadCommentsTask;
import com.example.azranel.githubapp.models.Issue;
import com.example.azranel.githubapp.models.IssueComment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class IssueDetailsActivity extends ActionBarActivity {
    private ListView commentsListView;
    private Issue issue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_details);
        issue = getIssueFromIntent();
        injectViews();
        setupView();
    }

    private void setupView() {
        setTitle("Issue " + issue.getNumber());
        final Context context = this;
        new DownloadCommentsTask(issue) {
            @Override
            protected void onPostExecute(List<IssueComment> issueComments) {
                commentsListView.setAdapter(new CommentsAdapter(issueComments, context));
            }
        }.execute();
    }

    private void injectViews() {
        commentsListView = (ListView) findViewById(R.id.comments_listview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_issue_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_comment_button) {
            showNewCommentDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showNewCommentDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_comment);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_comment, null);
        final Context context = this;
        builder.setView(dialogView);
        builder.setPositiveButton(getString(R.string.dialog_add_comment_positive_button_label),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText commentContent =
                                (EditText) dialogView.findViewById(R.id.dialog_add_comment_edittext);
                        String body = commentContent.getText().toString();
                        JSONObject object = new JSONObject();
                        try {
                            object.put("body", body);
                            AddCommentToIssueTask task = new AddCommentToIssueTask(issue) {
                                @Override
                                protected void onPostExecute(IssueComment issueComment) {
                                    CommentsAdapter adapter = (CommentsAdapter) commentsListView.getAdapter();
                                    adapter.addComment(issueComment);
                                    issue.setComments(issue.getComments() + 1);
                                }
                            };
                            task.execute(object);

                        } catch (JSONException e) {
                            Log.e("GITHUB", "Problem with building JSONObject");
                            e.printStackTrace();
                        }
                    }
                });
        builder.setNegativeButton(getString(R.string.dialog_add_comment_negative_button_label),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    private Issue getIssueFromIntent() {
        return (Issue) getIntent().getSerializableExtra(Issue.ISSUE_KEY);
    }
}
