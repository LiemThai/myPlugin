package com.atlassian.tutorial.myPlugin.service;
import com.atlassian.activeobjects.tx.Transactional;
import com.atlassian.tutorial.myPlugin.entity.User;

import java.util.List;

@Transactional
public interface UserService
{
    User add(String key, String displayName, String emailAddress);
    User get(int id);
    void remove(User create);
    void update(String key, String displayName, String emailAddress, int id);
    List<User> all();
}


