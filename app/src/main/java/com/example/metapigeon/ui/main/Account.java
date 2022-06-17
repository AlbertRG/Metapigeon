package com.example.metapigeon.ui.main;

public class Account {

    private String user;
    private String email;
    private String password;
    private int[] charsID;
    private int maxChars;

    public Account(String user, String email, String password, int[] charsID, int maxChars) {
        this.user = user;
        this.email = email;
        this.password = password;
        this.charsID = charsID;
        this.maxChars = maxChars;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int[] getCharsID() {
        return charsID;
    }

    public void setCharsID(int[] charsID) {
        this.charsID = charsID;
    }

    public int getMaxChars() {
        return maxChars;
    }

    public void setMaxChars(int maxChars) {
        this.maxChars = maxChars;
    }

}
