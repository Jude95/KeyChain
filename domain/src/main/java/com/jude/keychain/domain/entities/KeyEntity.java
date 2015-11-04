package com.jude.keychain.domain.entities;

/**
 * Created by zhuchenxi on 15/11/3.
 */
public class KeyEntity {
    private String name;
    private String account;
    private String password;
    private String note;
    private int type;

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