package com.example.whatsapp.Model;

public class Users {
    String profilepic, username,mail,password,userid,lastmassage;

    public Users(String profilepic, String username, String mail, String password, String userid, String lastmassage) {
        this.profilepic = profilepic;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userid = userid;
        this.lastmassage = lastmassage;
    }
    public Users(){}

    //signup constucture
    public  Users(String username, String mail, String password){
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLastmassage() {
        return lastmassage;
    }

    public void setLastmassage(String lastmassage) {
        this.lastmassage = lastmassage;
    }



}
