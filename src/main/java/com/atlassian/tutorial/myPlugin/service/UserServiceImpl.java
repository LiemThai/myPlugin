package com.atlassian.tutorial.myPlugin.service;

import com.atlassian.activeobjects.external.ActiveObjects;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.tutorial.myPlugin.entity.User;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Scanned
@Named
public class UserServiceImpl implements UserService
{
    @ComponentImport
    private final ActiveObjects ao;

    @Inject
    public UserServiceImpl(ActiveObjects ao)
    {
        this.ao = checkNotNull(ao);
    }

    @Override
    public User add(String key, String displayName, String email)
    {
        final User row = ao.create(User.class);
        row.setKey(key);
        row.setEmailAddress(email);
        row.setDisplayName(displayName);
        row.save();
        return row;
    }

    @Override
    public User get(int id)
    {
        return ao.get(User.class, id);
    }

    @Override
    public void remove(User row)
    {
        ao.delete(row);
    }

    @Override
    public void update(String key, String displayName, String email, int id) {
        User row = get(id);
        if(!key.equals("")){
            row.setKey(key);
        }
        if(!displayName.equals("")){
            row.setDisplayName(displayName);
        }
        if(!email.equals("")){
            row.setEmailAddress(email);
        }
        row.save();
    }

    @Override
    public List<User> all()
    {
        return newArrayList(ao.find(User.class));
    }
}

