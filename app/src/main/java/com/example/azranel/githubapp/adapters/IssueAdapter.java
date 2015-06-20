package com.example.azranel.githubapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.models.Issue;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by azranel on 04.06.15.
 */
public class IssueAdapter extends BaseAdapter implements Serializable {
    private final Context context;
    private List<Issue> issues;
    public IssueAdapter(Context context, List<Issue> issues) {
        this.context = context;
        this.issues = issues;
        Collections.sort(issues, new Comparator<Issue>() {
            @Override
            public int compare(Issue lhs, Issue rhs) {
                if(lhs.getState().equals(Issue.OPEN))
                    return -1;
                else return 1;
            }
        });
    }

    @Override
    public int getCount() {
        return issues.size();
    }

    @Override
    public Object getItem(int position) {
        return issues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View finalView = null;
        if(convertView == null) {
            finalView = LayoutInflater.from(context).inflate(R.layout.listview_issue_item, parent, false);
        } else finalView = convertView;

        Issue issue = (Issue) getItem(position);

        TextView author = (TextView) finalView.findViewById(R.id.issue_author);
        TextView assignee = (TextView) finalView.findViewById(R.id.issue_assignee);
        TextView title = (TextView) finalView.findViewById(R.id.issue_title);
        TextView comments = (TextView) finalView.findViewById(R.id.issue_comments);
        String state = issue.getState();
        if(state.equals(Issue.CLOSED)) {
            finalView.setBackgroundColor(Color.GRAY);
        } else finalView.setBackgroundColor(Color.WHITE);

        author.setText("Author: " + issue.getUser().getLogin());

        String assigneeText = "Assignee: ";
        if(issue.getAssignee() != null) {
            assigneeText += issue.getAssignee().getLogin();
        } else assigneeText += "none";
        assignee.setText(assigneeText);

        title.setText(issue.getTitle());
        comments.setText("Comments: " + issue.getComments());


        return finalView;
    }
}
