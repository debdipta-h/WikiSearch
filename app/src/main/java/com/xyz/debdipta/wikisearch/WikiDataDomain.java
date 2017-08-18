package com.xyz.debdipta.wikisearch;

/**
 * Created by debdipta on 16-08-2017.
 */

public class WikiDataDomain {
    private String title;
    private String description;
    private String link;

    public WikiDataDomain(String title,String description,String link) {
        setTitle(title);
        setDescription(description);
        setLink(link);
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    private void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return getTitle()+"\n"+getDescription()+"\n"+getLink();
    }


}