package com.example.azranel.githubapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azranel.githubapp.R;
import com.example.azranel.githubapp.models.IssueComment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by azranel on 04.06.15.
 */
public class CommentsAdapter extends BaseAdapter {
    public CommentsAdapter(List<IssueComment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    private List<IssueComment> comments;
    private Context context;

    public void addComment(IssueComment comment) {
        comments.add(comment);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View finalView = null;
        if(convertView == null) {
            finalView = LayoutInflater.from(context)
                    .inflate(R.layout.listview_comment_item, parent, false);
        } else finalView = convertView;

        IssueComment comment = (IssueComment) getItem(position);

        ImageView userImage = (ImageView) finalView.findViewById(R.id.user_image);
        TextView userLogin = (TextView) finalView.findViewById(R.id.user_login);
        TextView commentBody = (TextView) finalView.findViewById(R.id.comment_body);

        Picasso.with(context).load(comment.getUser().getAvatarUrl()).into(userImage);
        userLogin.setText(comment.getUser().getLogin());
        commentBody.setText(comment.getBody());

        return finalView;
    }
}
