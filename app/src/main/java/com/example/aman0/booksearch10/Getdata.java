package com.example.aman0.booksearch10;

/**
 * Created by aman0 on 23-06-2017.
 */

public class Getdata {

    private String Title;
    //    private String Subtitle;
    private String Author;
    private String Publisher;
    private String Url;
    private String imageurl;

    public Getdata(String title,String author,String publisher,String url,String image){
        Title = title;
//        Subtitle = subtitle;
        Author = author;
        Publisher = publisher;
        Url = url;
        imageurl=image;
    }

    public String getTitle() {
        return Title;
    }

//    public String getSubtitle() {
//        return Subtitle;
//    }

    public String getAuthor() {
        return Author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getUrl() {
        return Url;
    }

    public String getImageurl() {
        return imageurl;
    }
}
