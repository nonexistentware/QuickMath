package com.nonexistentware.quickmath.Model;

public class AppNewsModel {

    private String newsImage;
    private String newsTitle;
    private String newsBody;
    private String newsDateTime;

    public AppNewsModel() {

    }

    public AppNewsModel(String newsImage, String newsTitle, String newsBody, String newsDateTime) {
        this.newsImage = newsImage;
        this.newsTitle = newsTitle;
        this.newsBody = newsBody;
        this.newsDateTime = newsDateTime;
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

    public String getNewsDateTime() {
        return newsDateTime;
    }

    public void setNewsDateTime(String newsDateTime) {
        this.newsDateTime = newsDateTime;
    }
}
