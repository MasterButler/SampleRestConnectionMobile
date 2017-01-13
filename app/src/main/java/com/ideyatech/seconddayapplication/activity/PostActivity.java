package com.ideyatech.seconddayapplication.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.ideyatech.seconddayapplication.R;
import com.ideyatech.seconddayapplication.util.ConnectUtil;
import com.ideyatech.seconddayapplication.util.Post;
import com.ideyatech.seconddayapplication.util.PostAdapter;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    private ProgressDialog pd;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHome

        Bundle b = getIntent().getExtras();

        if(b == null){
            userId= -1;
        }else{
            userId = b.getInt("userId");
            new PostRetriever().execute();
        }
    }

    class PostRetriever extends AsyncTask<Void, Void, ArrayList<Post>> {
        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(PostActivity.this);
            pd.setMessage("loading");
            pd.show();

        }

        @Override
        protected ArrayList<Post> doInBackground(Void... params) {
            try {

                ArrayList<Post> posts = ConnectUtil.getPosts(userId);

                for(int i = 0; i < posts.size(); i++){
                    Log.wtf("POST INFO IN ACTIVITY: ", posts.get(i).getTitle());
                }

                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Post> posts) {
            Log.wtf("HERE", "A");
            ListView postListView = (ListView) findViewById(R.id.postListView);
            Log.wtf("HERE", "B");
            postListView.setAdapter(new PostAdapter(getBaseContext(), posts));
            Log.wtf("HERE", "C");
            pd.dismiss();

        }
    }
}
