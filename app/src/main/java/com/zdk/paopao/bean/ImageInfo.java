package com.zdk.paopao.bean;

import com.zdk.paopao.gson.Image;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class ImageInfo {
    private String avatar;
    private String username;
    private String content;
    private String publishTime;
    private int agree;
    private int comment;
    private String imageUrl;
    private List<String> imageUrls;
    public void parseImage(Image imageData, int position){
        avatar=imageData.data.get(position).avatar;
        username=imageData.data.get(position).nickname;
        content=imageData.data.get(position).txt;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
