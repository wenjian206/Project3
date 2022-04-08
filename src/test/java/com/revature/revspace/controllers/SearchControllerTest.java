package com.revature.revspace.controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.revature.revspace.app.RevSpaceWebServiceApplication;
import com.revature.revspace.models.User;
import com.revature.revspace.services.UserService;
import com.revature.revspace.services.UserServiceImpl;
import com.revature.revspace.testutils.ModelGenerators;

@AutoConfigureMockMvc
@SpringBootTest(classes = RevSpaceWebServiceApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class SearchControllerTest {
	
	private static final String TEST_EMAIL = "testemail@revature.net";
	
	private static final String TEST_FIRSTNAME = "Search";
	
	private static final String TEST_LASTNAME = "Feature";
	
	@MockBean
	private UserService service;
	
//	@MockBean
//	private UserServiceImpl serviceI;
	
	@Autowired
	private MockMvc mvc;
	
	@Test
	@WithMockUser(username=TEST_EMAIL)
	void getAllUserByEmail() throws Exception
	{
		User user = ModelGenerators.makeRandomUser();
		List<User> uList = new ArrayList<User>();
		uList.add(user);
		
		Mockito.when(service.getAllUserByEmail(TEST_EMAIL)).thenReturn(uList);
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/users/search/email?email=testemail@revature.net")
			.contentType("application/json")
			.content(TEST_EMAIL));
		actions.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser(username=TEST_EMAIL)
	void getAllUserByName() throws Exception
	{
		User user = ModelGenerators.makeRandomUser();
		
		List<User> uList = new ArrayList<User>();
		uList.add(user);
		String FULL_NAME = TEST_FIRSTNAME+TEST_LASTNAME;

		Mockito.when(service.getAllUserByName(TEST_FIRSTNAME, TEST_LASTNAME)).thenReturn(uList);
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/users/search/name?firstname=Search&lastname=Feature")
			.contentType("application/json")
			.content(FULL_NAME));
		actions.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
