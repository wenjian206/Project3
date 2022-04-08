package com.revature.revspace.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.revature.revspace.models.Credentials;
import com.revature.revspace.models.Like;
import com.revature.revspace.models.Post;
import com.revature.revspace.models.User;
import com.revature.revspace.repositories.CredentialsRepo;
import com.revature.revspace.repositories.PostRepo;
import com.revature.revspace.services.CredentialsService;
import com.revature.revspace.services.PostService;
import com.revature.revspace.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = com.revature.revspace.app.RevSpaceWebServiceApplication.class)
@TestPropertySource("classpath:application.properties")
@TestPropertySource("classpath:application-test.properties")
public class PostControllerTest {
    private static final String TEST_EMAIL = "testemail@revature.net";
    @Autowired
    UserService us;

    @MockBean
    PostService service;

    @MockBean
    PostRepo repo;

    @Autowired
    CredentialsService credentialsService;

    @MockBean
    CredentialsRepo credentialsRepo;
    @MockBean
    CredentialsService cs;

    @Autowired
    MockMvc mvc;

    @Autowired
    Gson gson;

    User user = new User(1,"email@revature.net","firstname", "lastname", 12332254L, 1232222L,"username","title", "location", "aboutme");
    Credentials credentials  =new Credentials(1,user,"password");
    Post post = new Post(1,user,"body","image",1234569L,true,null);
    Like like = new Like(1,user,post);


    @Test
    @WithMockUser(username=TEST_EMAIL)
    void addPost() throws Exception
    {
        User user = new User("abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(user,"password");
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);
        Post post = new Post(credentials.getUser(), "body","image",1234569L,true,null);
        Mockito.when(service.add(new Post()))
                .thenReturn(post);
        System.out.println(post);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(post)));
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    @WithMockUser(username=TEST_EMAIL)
    void getPostById() throws Exception
    {

        User user = new User(1,"abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(1,user,"password");
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);
        Post post = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);
        Mockito.when(service.get(1))
                .thenReturn(post);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(post)));
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }



    @Test
    @WithMockUser(username=TEST_EMAIL)
    void updatePost() throws Exception
    {
        User user = new User(1,"abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(1,user,"password");
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);
        Post post = new Post( credentials.getUser(), "body","image",1234569L,true,null);
        Mockito.when(service.update(new Post()))
                .thenReturn(post);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.put("/posts")
                .contentType("application/json")
                .content(gson.toJson(post)));
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username=TEST_EMAIL)
    void deletePost() throws Exception
    {
        User user = new User(1,"abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(1,user,"password");
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);
        Mockito.when(service.delete(1))
                .thenReturn(true);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(post)));
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username=TEST_EMAIL)
    void getPostByIdWithoutInt() throws Exception
    {
        String id = "id";
        User user = new User(1,"abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(1,user,"password");
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);
        Post post = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);
        Mockito.when(service.get(1))
                .thenReturn(post);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/posts/{id}","a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(post)));
        actions.andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    @WithMockUser(username=TEST_EMAIL)
    void getNextTen() throws Exception
    {

        User user = new User(1,"abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(1,user,"password");
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);

        Post p1 = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);
        Post p2 = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);


        List<Post> post1 = new ArrayList<>();
        post1.add(p1);
        post1.add(p2);

        List<List<Post>> postList = new ArrayList<>();
        postList.add(post1);

        Mockito.when(service.pullPostsList(1, user)).thenReturn(postList);

        ResultActions ra = mvc.perform(MockMvcRequestBuilders.get("/posts").header("lastPostIdOnThePage", 1));
        ra.andExpect(status().isOk());


    }


    @Test
    @WithMockUser(username=TEST_EMAIL)
    void getNextTenWithoutInt() throws Exception
    {

        User user = new User(1,"abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(1,user,"password");
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);
//        Post post = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);

        Post p1 = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);
        Post p2 = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);


        List<Post> post1 = new ArrayList<>();
        post1.add(p1);
        post1.add(p2);

        List<List<Post>> postList = new ArrayList<>();
        postList.add(post1);


        Mockito.when(service.pullPostsList(1, user)).thenReturn(postList);


        ResultActions ra = mvc.perform(MockMvcRequestBuilders.get("/posts").header("lastPostIdOnThePage", -1));
        ra.andExpect(status().is(400));


    }

    @Test
    @WithMockUser(username=TEST_EMAIL)
    void getNextTenWithString() throws Exception
    {

        User user = new User(1,"abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(1,user,"password");
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);
//        Post post = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);

        Post p1 = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);
        Post p2 = new Post(1, credentials.getUser(), "body","image",1234569L,true,null);


        List<Post> post1 = new ArrayList<>();
        post1.add(p1);
        post1.add(p2);

        List<List<Post>> postList = new ArrayList<>();
        postList.add(post1);


        Mockito.when(service.pullPostsList(1, user)).thenReturn(postList);


        ResultActions ra = mvc.perform(MockMvcRequestBuilders.get("/posts").header("lastPostIdOnThePage", "a"));
        ra.andExpect(status().is(400));


    }

    @Test
    @WithMockUser(username=TEST_EMAIL)
    void getNextTenNoContent() throws Exception
    {

        ResultActions ra = mvc.perform(MockMvcRequestBuilders.get("/posts").header("lastPostIdOnThePage", 1));
        ra.andExpect(status().isNoContent());


    }


    @Test
    @WithMockUser(username=TEST_EMAIL)
    void getPostByIdNull() throws Exception
    {
        User user = new User(1,"abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(1,user,"password");
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);
        Post post = null;
        Mockito.when(service.get(1))
                .thenReturn(post);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(post)));
        actions.andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    @WithMockUser(username=TEST_EMAIL)
    void deletePostWithoutId() throws Exception
    {
        User user = new User(1,"abc@email.com", "name","name", 8708779L, 234243234L, "git", "title", "location", "aboutme");
        Credentials credentials = new Credentials(1,user,"password");
        System.out.println(credentials);
        Mockito.when(credentialsRepo.findByUserEmail(user.getEmail())).thenReturn(credentials);
        Mockito.when(service.delete(1))
                .thenReturn(true);
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/posts/{id}","a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(post)));
        actions.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
