package com.atlassian.tutorial.myPlugin.entity;
import com.atlassian.activeobjects.tx.Transactional;
import net.java.ao.Entity;

@Transactional
public interface User extends Entity
{
    String getKey();
    void setKey(String key);

    String getDisplayName();
    void setDisplayName(String displayName);

    String getEmailAddress();
    void setEmailAddress(String emailAddress);
}

