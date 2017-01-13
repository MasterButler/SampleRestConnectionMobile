package com.ideyatech.seconddayapplication.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ideyatech.seconddayapplication.R;
import com.ideyatech.seconddayapplication.activity.CommentActivity;

import java.util.ArrayList;

/**
 * Created by HP on 1/13/2017.
 */

public class PostAdapter extends BaseAdapter {
    Context context;
    ArrayList<Post> posts;
    private static LayoutInflater inflater = null;

    public PostAdapter(Context context, ArrayList<Post> posts){
        this.context = context;
        this.posts = new ArrayList<Post>();
        for(int i = 0; i < posts.size(); i++){
            this.posts.add(posts.get(i));
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Context getContext(){
        return this.context;
    }

    public ArrayList<Post> getPosts(){
        return this.posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) { return posts.get(position).getPost_id(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflater.inflate(R.layout.post_row, null);
        }


        TextView title = (TextView) v.findViewById(R.id.postTitle);
        TextView body = (TextView) v.findViewById(R.id.postBody);
        Button comments = (Button) v.findViewById(R.id.btn_Comments);

        final Post found = posts.get(position);

        title.setText(found.getTitle());
        body.setText(found.getBody());
        comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Hai", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), CommentActivity.class);
                intent.putExtra("postId", found.getPost_id());
                Log.wtf("ACTION", "Getting the comments of post " + found.getPost_id());
                v.getContext().startActivity(intent);

            }
        });


        return v;
    }
}
