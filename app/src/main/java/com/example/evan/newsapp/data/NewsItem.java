package com.example.evan.newsapp.data;


public class NewsItem {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public NewsItem(String author, String title, String description, String url, String urlToImage, String publishedAt)
    {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
    }

    public String getPublishedAt() { return publishedAt; }

    public String getUrlToImage() { return urlToImage; }

    public String getUrl() { return url; }

    public String getAuthor(){
        return author;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }
}
