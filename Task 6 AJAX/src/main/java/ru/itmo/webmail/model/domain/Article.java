package ru.itmo.webmail.model.domain;

import java.util.Date;

public class Article {
    private long id;
    private Long userId;
    private String title;
    private String text;
    private Date creationTime;
    private boolean hidden;


    public long getId() {
        return id;
    }

    public boolean isHidden(){
        return hidden;
    }
    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userId;
    }

    public void setUserid(long userid) {
        this.userId = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
