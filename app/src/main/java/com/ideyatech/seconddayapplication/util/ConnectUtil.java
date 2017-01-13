package com.ideyatech.seconddayapplication.util;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 1/12/2017.
 */

public class ConnectUtil {

    private static final String TAG = "ConnectUtil";

    private final String posts = "posts";
    private final String comments = "comments";
    private final String albums = "albums";
    private final String photos = "photos";


    public static final int AUTHENTICATION_TIMEOUT = 30 * 1000;

    private static HttpClient mHttpClient;

    public static void createHttpClient() {
        if(mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, AUTHENTICATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, AUTHENTICATION_TIMEOUT);
        }
    }


    public static ArrayList<Comment> getComments(int postId){
        String url = "http://jsonplaceholder.typicode.com/";
        url = url + "comments?postId=" + postId;

        ArrayList<Comment> comments = invokeRestRetrieveCommentsService(new ArrayList<NameValuePair>(), url);
        Log.wtf("URL", url);
        for(int i = 0; i < comments.size(); i++){
            Log.wtf("COMMENT:", comments.get(i).getEmail());
            Log.wtf("BODY:", comments.get(i).getBody());
        }
        Log.wtf("END", "reached end of list for getComment()");
        return comments;
    }

    public static ArrayList<Post> getPosts(int userId){
        String url = "http://jsonplaceholder.typicode.com/";
        url = url + "posts?userId=" + userId;

        ArrayList<Post> posts = invokeRestRetrievePostsService(new ArrayList<NameValuePair>(), url);
        Log.wtf("URL", url);
        for(int i = 0; i < posts.size(); i++){
            Log.wtf("POSTS:", posts.get(i).getTitle());
            Log.wtf("BODY:", posts.get(i).getBody());
        }
        Log.wtf("END", "reached end of list for getPosts()");
        return posts;
    }

    public static ArrayList<User> getUsers() {
        String url = "http://jsonplaceholder.typicode.com/";
        ArrayList<User> users = invokeRestRetrieveUserService(new ArrayList<NameValuePair>(), url + "users");
/*
        for(int i = 0; i < users.size(); i++){
            users.get(i)
                    .setPosts(invokeRestRetrievePostsService(new ArrayList<NameValuePair>(), url+ "posts?" + "userId=" + users.get(i).getId()));
            for(int j = 0; j < users.get(i).getPosts().size(); j++){
                Log.wtf("POST:", users.get(i).getPosts().get(j).getTitle());
                Log.wtf("BODY:", users.get(i).getPosts().get(j).getBody());
                users.get(i).getPosts().get(j)
                        .setComments(invokeRestRetrieveCommentsService(new ArrayList<NameValuePair>(), url + "comments?" + "postId=" + users.get(i).getPosts().get(j).getPost_id() ));
                for(int k = 0; k < users.get(i).getPosts().get(j).getComments().size(); k++){
                    Log.wtf("COMMENT:", users.get(i).getPosts().get(j).getComments().get(k).getName());
                    Log.wtf("BODY   :", users.get(i).getPosts().get(j).getComments().get(k).getBody());
                }
            }
        }
*/
        return users;
    }

    public static ArrayList<User> invokeRestRetrieveUserService(ArrayList<NameValuePair> params, String url) {
        if(params == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        final HttpResponse response;
        HttpEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(params);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }

        final HttpGet get = new HttpGet(url);
        get.addHeader(entity.getContentType());
        createHttpClient();

        try {
            response = mHttpClient.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, Object>> map = mapper.readValue(response.getEntity().getContent(),
                        new TypeReference<List<Map<String, Object>>>(){});

                ArrayList<User> userList = new ArrayList<User>();

                for(int i = 0; i < map.size(); i++){
                        Log.wtf("NAME: ", "" + map.get(i).toString());

                        User newUser = new User();
                        Map<String, Object> obj = map.get(i);

                        newUser.setId(Integer.parseInt(obj.get("id").toString()));
                        newUser.setName(obj.get("name").toString());
                        newUser.setUsername(obj.get("username").toString());
                        newUser.setEmail(obj.get("email").toString());
                        newUser.setPhone(obj.get("phone").toString());
                        newUser.setWebsite(obj.get("website").toString());

                        userList.add(newUser);
                }

                return userList;

            } else {
                Log.wtf(TAG, "Response: " + response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (ClientProtocolException e) {
            Log.e(TAG, "Client protocol exception: ", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "IO exception: ", e);
            return null;
        }
    }

    public static ArrayList<Post> invokeRestRetrievePostsService(ArrayList<NameValuePair> params, String url){
        if(params == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        final HttpResponse response;
        HttpEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(params);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }

        final HttpGet get = new HttpGet(url);
        get.addHeader(entity.getContentType());
        createHttpClient();

        try {
            response = mHttpClient.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, Object>> map = mapper.readValue(response.getEntity().getContent(),
                        new TypeReference<List<Map<String, Object>>>(){});

                ArrayList<Post> postList = new ArrayList<Post>();

                for(int i = 0; i < map.size(); i++){
                    Log.wtf("NAME: ", "" + map.get(i).toString());

                    Post newPost  = new Post();
                    Map<String, Object> obj = map.get(i);

                    newPost.setUser_id(Integer.parseInt(obj.get("userId").toString()));
                    newPost.setPost_id(Integer.parseInt(obj.get("id").toString()));
                    newPost.setTitle(obj.get("title").toString());
                    newPost.setBody(obj.get("body").toString());

                    postList.add(newPost);
                }

                return postList;

            } else {
                Log.wtf(TAG, "Response: " + response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (ClientProtocolException e) {
            Log.e(TAG, "Client protocol exception: ", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "IO exception: ", e);
            return null;
        }
    }

    public static ArrayList<Comment> invokeRestRetrieveCommentsService(ArrayList<NameValuePair> params, String url){
        if(params == null) {
            throw new IllegalArgumentException("Params cannot be null.");
        }
        final HttpResponse response;
        HttpEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(params);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }

        final HttpGet get = new HttpGet(url);
        get.addHeader(entity.getContentType());
        createHttpClient();

        try {
            response = mHttpClient.execute(get);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, Object>> map = mapper.readValue(response.getEntity().getContent(),
                        new TypeReference<List<Map<String, Object>>>(){});

                ArrayList<Comment> commentList = new ArrayList<Comment>();

                for(int i = 0; i < map.size(); i++){
                    Log.wtf("NAME: ", "" + map.get(i).toString());

                    Comment newComment = new Comment();
                    Map<String, Object> obj = map.get(i);

                    newComment.setPost_id(Integer.parseInt(obj.get("postId").toString()));
                    newComment.setComment_id(Integer.parseInt(obj.get("id").toString()));
                    newComment.setName(obj.get("name").toString());
                    newComment.setEmail(obj.get("email").toString());
                    newComment.setBody(obj.get("body").toString());

                    commentList.add(newComment);
                }

                return commentList;

            } else {
                Log.wtf(TAG, "Response: " + response.getStatusLine().getStatusCode());
                return null;
            }
        } catch (ClientProtocolException e) {
            Log.e(TAG, "Client protocol exception: ", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "IO exception: ", e);
            return null;
        }
    }


}
