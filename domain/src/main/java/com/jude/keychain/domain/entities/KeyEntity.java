package com.jude.keychain.domain.entities;

import java.io.Serializable;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class KeyEntity implements Serializable{
    private int id = -1;
    private String name;
    private String account;
    private String password;
    private String note;
    private int type;
    private long time;

    public int getId() {
        return id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
