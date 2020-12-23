package com.atlassian.tutorial.myPlugin.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class UserDTO {
    private String key;
    private String displayName;
    private String emailAddress;
    private int ID;
    public UserDTO(String key, String displayName, String emailAddress, int ID){
        this.key = key;
        this.displayName = displayName;
        this.emailAddress = emailAddress;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setKey(String key){
        this.key = key;
    }

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    public String getKey(){
        return this.key;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}