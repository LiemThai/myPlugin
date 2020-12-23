package com.atlassian.tutorial.myPlugin.bean;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class UserBean {
    private String key;
    private String displayName;
    private String emailAddress;

    public UserBean(String key, String displayName, String emailAddress){
        this.key = key;
        this.displayName = displayName;
        this.emailAddress = emailAddress;
    }

    public UserBean() {
    }

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public String getDisplayName(){
        return displayName;
    }

    public void setDisplayName(String displayName){
        this.displayName=displayName;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress=emailAddress;
    }
}