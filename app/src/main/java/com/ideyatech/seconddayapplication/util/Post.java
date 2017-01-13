package com.ideyatech.seconddayapplication.util;

import java.util.ArrayList;

/**
 * Created by HP on 1/13/2017.
 */

public class Post {
    private int user_id;
    private int post_id;
    private String title;
    private String body;
    private ArrayList<Comment> comments;

    public Post(){
        this.comments = new ArrayList<Comment>();
    }

    public Post(int user_id, int post_id, String title, String body){
        this.user_id = user_id;
        this.post_id = post_id;
        this.title = title;
        this.body = body;
        this.comments = new ArrayList<Comment>();
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
