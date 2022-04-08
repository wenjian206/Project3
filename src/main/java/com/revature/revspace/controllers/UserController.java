package com.revature.revspace.controllers;

import com.revature.revspace.models.Credentials;
import com.revature.revspace.models.User;
import com.revature.revspace.services.CredentialsService;
import com.revature.revspace.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.LinkedHashMap;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

@RestController
public class UserController
{
	
    @Autowired
    private UserService us;
    @Autowired
    private CredentialsService cs;

    public static User loginUser;

    @GetMapping("/login")
    public User getCurrentUser()
    {
        return this.us.getLoggedInUser();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable(name = "id") String id)
    {
        //parsing int from string can(should) be done somewhere else
        int safeId;
        try
        {
            safeId = Integer.parseInt(id);
        }catch (NumberFormatException e)
        {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User foundUser = us.get(safeId);
        if (null != foundUser)
        {
            return foundUser;
        }else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable(name = "email") String email)
    {
        User foundUser = us.getUserByEmail(email);
        if (null != foundUser)
        {
            return foundUser;
        }else
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/password/{email}")
    public String getUserPassword(@PathVariable(name = "email") String email) {
    
    	String returnedPW = cs.getPasswordByEmail(email);
    	return returnedPW;
    }
    
    /**
     * Adds a given user through a surrounding credentials object
     * gives a 409 for duplicate username, 422 for incomplete input
     * @param creds a credentials object represented in JSON
     * @return the user to be added without updated id
     */
    @PostMapping(value ="/users", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody Credentials creds)
    {
        User updatedUser = null;
        try
        {
            if(!Objects.equals(creds.getPassword(), "")
                    && creds.getPassword() != null
                    && creds.getUser() != null
                    && !Objects.equals(creds.getUser().getEmail(), "")
                    && !Objects.equals(creds.getUser().getFirstName(), "")
                    && !Objects.equals(creds.getUser().getLastName(), "")
                    && us.getUserByEmail(creds.getUser().getEmail()) == null)
            {
                updatedUser = us.add(creds.getUser());
                cs.add(creds);
            }else if(creds.getUser() != null && us.getUserByEmail(creds.getUser().getEmail()) != null)
            {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }else
            {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }catch (IllegalArgumentException ignored) {}
        return updatedUser;
    }
    
    @PutMapping(value="/follow/{fId}")
    public User followUser(@PathVariable("fId") String fId, @RequestBody User loggedUser) {
    	User newLoggedUser = us.get(loggedUser.getUserId());
    	List<User> lfUser = newLoggedUser.getFollowing();
    	User resultUser; 
        //parsing int from string, can(should) be done somewhere else
        int safeId;
        try
        {
            safeId = Integer.parseInt(fId);
        }catch (NumberFormatException e)
        {
            safeId = 0;
        }
    	User followUser = us.get(safeId);
        for(User verify : lfUser) {
        	if(followUser.getUserId() == verify.getUserId())
            {
        		lfUser.remove(verify);
                newLoggedUser.setFollowing(lfUser);
                followUser.getFollowers().remove(newLoggedUser);        
                resultUser = us.update(newLoggedUser);

                return resultUser;
            }
        }
        lfUser.add(followUser);
        newLoggedUser.setFollowing(lfUser);      
        resultUser = us.update(newLoggedUser);
        if (resultUser == null || followUser == null)
        {
            throw new ResponseStatusException
                    (
                        (safeId == 0)?HttpStatus.NOT_FOUND:HttpStatus.UNPROCESSABLE_ENTITY
                    );
        }
        return resultUser;

    }

    @PutMapping(value = "/users/{id}", consumes = "application/json")
    public User updateUser(@PathVariable("id") String id, @RequestBody User newUser)
    {
        User resultUser;
        //parsing int from string, can(should) be done somewhere else
        int safeId;
        try
        {
            safeId = Integer.parseInt(id);
        }catch (NumberFormatException e)
        {
            safeId = 0;
        }
        newUser.setUserId(safeId);
        resultUser = us.update(newUser);
        if (resultUser == null)
        {
            throw new ResponseStatusException
                    (
                        (safeId == 0)?HttpStatus.NOT_FOUND:HttpStatus.UNPROCESSABLE_ENTITY
                    );
        }
        return resultUser;
        
    }

    @DeleteMapping(value = "/users/{id}")
    public boolean deleteUser(@PathVariable("id") String id)
    {
        boolean idFound;
        //parsing int from string, can(should) be done somewhere else
        int safeId;
        Integer credsId;
        try
        {
            safeId = Integer.parseInt(id);
        }catch (NumberFormatException e)
        {
            safeId = 0;
        }
        credsId = cs.getIdByUserId(safeId);
        cs.delete((credsId != null)?credsId:0);
        idFound = us.delete(safeId);
        if(!idFound)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return true;
    }
    
    @PostMapping(value="/users/password")
    public ResponseEntity<User> changePassword(@RequestBody LinkedHashMap<String, String> bodyMap){
    	User user = this.us.getLoggedInUser();
    	if(cs.getByEmail(bodyMap.get("email")).getPassword().equals(bodyMap.get("oldPassword"))) {
    		cs.changePassword(Integer.parseInt(bodyMap.get("id")), bodyMap.get("newPassword"));
    		return ResponseEntity.status(201).body(user);
    	}else {
    		return ResponseEntity.badRequest().build();
    	}    	
    }
    
    @GetMapping("/users/all")
	public ResponseEntity<List<User>> findAllUsers(){
		return ResponseEntity.status(200).body(this.us.getAll());
	}
}
	
	  
