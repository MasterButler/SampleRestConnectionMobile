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

import com.ideyatech.seconddayapplication.R;
import com.ideyatech.seconddayapplication.activity.PostActivity;

import java.util.ArrayList;

/**
 * Created by HP on 1/13/2017.
 */

public class UserAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> users;
    private static LayoutInflater inflater = null;

    public UserAdapter(Context context, ArrayList<User> users){
        this.context = context;
        this.users = new ArrayList<User>();
        for(int i = 0; i < users.size(); i++){
            this.users.add(users.get(i));
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Context getContext(){
        return this.context;
    }

    public ArrayList<User> getUsers(){
        return this.users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = inflater.inflate(R.layout.user_row, null);
        }


        TextView name = (TextView) v.findViewById(R.id.user_name);
        TextView email = (TextView) v.findViewById(R.id.user_email);
        Button posts = (Button) v.findViewById(R.id.btn_Posts);

        final User found = users.get(position);

        name.setText(found.getName());
        email.setText(found.getName());
        posts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ProgressDialog pd = new ProgressDialog(v.getContext());
                //pd.setMessage("Loading");
                //pd.show();

                Intent intent = new Intent(v.getContext(), PostActivity.class);
                intent.putExtra("userId", found.getId());
                Log.wtf("ACTION", "Getting the posts of user " + found.getId());
                v.getContext().startActivity(intent);

                //pd.dismiss();
            }
        });


        return v;
    }
}
