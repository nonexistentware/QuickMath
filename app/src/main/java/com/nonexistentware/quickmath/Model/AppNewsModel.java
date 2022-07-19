package com.nonexistentware.quickmath.Model;

public class AppNewsModel {

    private String newsImage;
    private String newsTitle;
    private String newsBody;

    public AppNewsModel() {

    }

    public AppNewsModel(String newsImage, String newsTitle, String newsBody) {
        this.newsImage = newsImage;
        this.newsTitle = newsTitle;
        this.newsBody = newsBody;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }
}
