package ru.itmo.webmail.model.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private Long id;
    private String login;
    private Date creationTime;
    private String email;
    private boolean confirmed = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setConfirmed(boolean confirmed){
        this.confirmed = confirmed;
    }

    public boolean getConfirmed(){
        return confirmed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
