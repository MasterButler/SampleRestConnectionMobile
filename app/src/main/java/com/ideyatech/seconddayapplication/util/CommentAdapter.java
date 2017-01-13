package com.ideyatech.seconddayapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ideyatech.seconddayapplication.R;

import java.util.ArrayList;

/**
 * Created by HP on 1/13/2017.
 */

public class CommentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Comment> comments;
    private static LayoutInflater inflater = null;

    public CommentAdapter(Context context, ArrayList<Comment> comments){
        this.context = context;
        this.comments = new ArrayList<Comment>();
        for(int i = 0; i < comments.size(); i++){
            this.comments.add(comments.get(i));
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Context getContext(){
        return this.context;
    }

    public ArrayList<Comment> getComments(){
        return this.comments;
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
    public long getItemId(int position) { return comments.get(position).getComment_id(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflater.inflate(R.layout.comment_row, null);
        }


        TextView name = (TextView) v.findViewById(R.id.commentName);
        TextView email = (TextView) v.findViewById(R.id.commentEmail);
        TextView body = (TextView) v.findViewById(R.id.commentBody);

        final Comment found = comments.get(position);

        name.setText(found.getName());
        email.setText(found.getEmail());
        body.setText(found.getBody());

        return v;
    }
}
