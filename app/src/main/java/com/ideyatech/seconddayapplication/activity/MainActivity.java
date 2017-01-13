package com.ideyatech.seconddayapplication.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.ideyatech.seconddayapplication.R;
import com.ideyatech.seconddayapplication.util.ConnectUtil;
import com.ideyatech.seconddayapplication.util.User;
import com.ideyatech.seconddayapplication.util.UserAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            new UserRetriever().execute();
        } catch (Exception e) {
            Toast.makeText(this, "SOMETHING HAPPENED", Toast.LENGTH_SHORT).show();
        }

        ListView userList = (ListView) findViewById(R.id.userListView);
    }

    class UserRetriever extends AsyncTask<Void, Void, ArrayList<User>> {
        @Override
        protected void onPreExecute(){
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("loading");
            pd.show();

        }

        @Override
        protected ArrayList<User> doInBackground(Void... params) {
            try {
                ArrayList<User> users = ConnectUtil.getUsers();

                //for(int i = 0; i < users.size(); i++){
                //    Log.wtf("USER INFO", users.get(i).getEmail());
                //}

                return users;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<User> users) {

            ListView userListView = (ListView) findViewById(R.id.userListView);
            userListView.setAdapter(new UserAdapter(getBaseContext(), users));
            pd.dismiss();
        }
    }
}
