package com.ideyatech.seconddayapplication.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.ideyatech.seconddayapplication.R;
import com.ideyatech.seconddayapplication.util.Comment;
import com.ideyatech.seconddayapplication.util.CommentAdapter;
import com.ideyatech.seconddayapplication.util.ConnectUtil;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    private ProgressDialog pd;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Log.wtf("START:", "COMMENT ACTIVITY REACHED");


        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHome

        Bundle b = getIntent().getExtras();

        if(b == null){
            postId= -1;
        }else{
            postId = b.getInt("postId");
            new CommentRetriever().execute();
        }
    }

    class CommentRetriever extends AsyncTask<Void, Void, ArrayList<Comment>> {
        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(CommentActivity.this);
            pd.setMessage("loading");
            pd.show();

        }

        @Override
        protected ArrayList<Comment> doInBackground(Void... params) {
            try {
                ArrayList<Comment> comments = ConnectUtil.getComments(postId);

                for(int i = 0; i < comments.size(); i++){
                    Log.wtf("COMMENT INFO IN ACTIVITY: ", comments.get(i).getEmail());
                }

                return comments;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Comment> comments) {
            Log.wtf("HERE", "A");
            ListView commentListView = (ListView) findViewById(R.id.commentListView);
            Log.wtf("HERE", "B");
            commentListView.setAdapter(new CommentAdapter(getBaseContext(), comments));
            Log.wtf("HERE", "C");
            pd.dismiss();
        }
    }
}
