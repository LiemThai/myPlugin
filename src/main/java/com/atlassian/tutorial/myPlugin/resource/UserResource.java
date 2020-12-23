package com.atlassian.tutorial.myPlugin.resource;

import com.atlassian.tutorial.myPlugin.service.UserServiceImpl;
import com.atlassian.tutorial.myPlugin.entity.User;
import com.atlassian.tutorial.myPlugin.bean.UserBean;
import com.atlassian.tutorial.myPlugin.dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("users")
public class UserResource {
    private final UserServiceImpl ser;

    public UserResource(UserServiceImpl ser) {
        this.ser = ser;
    }

    public User check(String key) {
        int id;
        try {
            id = Integer.parseInt(key);
        } catch (NumberFormatException nfe) {
            id = 0;
        }
        User row = ser.get(id);
        return row;
    }

    @GET
//    @AnonymousAllowed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user/{id}")
    public Response loadUser(@PathParam("id") String key) {
        User row = check(key);
        UserDTO userdto;
        if (row != null) {
            userdto = new UserDTO(row.getKey(), row.getDisplayName(), row.getEmailAddress(), row.getID());
        } else {
            userdto = new UserDTO("null", "null", "null", 0);
        }
        return Response.ok(userdto).build();
    }

    private String getKeyFromKey(String key) {
        return key;
    }

    @GET
//    @AnonymousAllowed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allusers")
    public Response loadAllUsers() {
        List<User> totakes = new ArrayList<User>();
        List<UserDTO> userdto = new ArrayList<UserDTO>();
        for (User row : ser.all()) {
            totakes.add(row);
            userdto.add(new UserDTO(row.getKey(), row.getDisplayName(), row.getEmailAddress(), row.getID()));
        }
        return Response.ok(userdto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user")
    public Response createUser(UserBean userBean) {
        User row = ser.add(userBean.getKey(),userBean.getDisplayName(),userBean.getEmailAddress());
        UserDTO userdto = new UserDTO(row.getKey(), row.getDisplayName(), row.getEmailAddress(), row.getID());
        return Response.ok(userdto).build();
    }

    @DELETE
    @Path("{id}")
    public void removeUser(@PathParam("id") String key){
        User row = check(key);
        ser.remove(row);
    }

    @PUT
//    @AnonymousAllowed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/{id}")
    public Response updateUser(UserBean userBean, @PathParam("id") String id) {
        User row = check(id);
        if(row.getID()!=0){
            ser.update(userBean.getKey(),userBean.getDisplayName(),userBean.getEmailAddress(), row.getID());
            return Response.ok().build();
        }
        else {
            row = ser.add(userBean.getKey(),userBean.getDisplayName(),userBean.getEmailAddress());
            UserDTO userdto = new UserDTO(row.getKey(), row.getDisplayName(), row.getEmailAddress(), row.getID());
            return Response.ok(userdto).build();
        }
    }
}
