package com.example.myapplication_1;

public class SingleItem {
    String postTitle;
    String storeName;
    String postTime;
    int postNum;
    public SingleItem(String postTitle,String storeName,String postTime,int postNum){
        this.postTitle=postTitle;
        this.storeName=storeName;
        this.postTime=postTime;
        this.postNum=postNum;
    }
    public String getPostTitle(){
        return postTitle;
    }
    public void setPostTitle(String postTitle){
        this.postTitle=postTitle;
    }

    public String getStoreName(){
        return storeName;
    }
    public void setStoreName(String storeName){
        this.storeName=storeName;
    }

    public String getPostTime(){
        return postTime;
    }
    public void setPostTime(String postTime){
        this.postTime=postTime;
    }

    public int getPostNum(){
        return postNum;
    }
    public void setPostNum(int postNum){
        this.postNum=postNum;
    }
}
