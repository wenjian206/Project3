package com.revature.revspace.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.LinkedHashMap;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.revspace.app.RevSpaceWebServiceApplication;
import com.revature.revspace.models.Credentials;
import com.revature.revspace.models.User;
import com.revature.revspace.services.CredentialsService;
import com.revature.revspace.services.UserService;
import com.revature.revspace.testutils.ModelGenerators;

@AutoConfigureMockMvc
@SpringBootTest(classes = RevSpaceWebServiceApplication.class)
@TestPropertySource("classpath:application-test.properties")
public class UserControllerTests {
	private static final String TEST_EMAIL = "testemail@revature.net";

	@MockBean
	private UserService service;

	@MockBean
	private CredentialsService cs;

	@Autowired
	private MockMvc mvc;

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void getCurrentUser() throws Exception {
		User user = ModelGenerators.makeRandomUser();

		Mockito.when(service.getLoggedInUser()).thenReturn(user);
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/login"));
		actions.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void getUserById() throws Exception {
		int id = 1;
		User user = ModelGenerators.makeRandomUser(1);

		Mockito.when(service.get(id)).thenReturn(user);
		ResultActions actions = mvc
				.perform(MockMvcRequestBuilders.get("/users/1").contentType("application/json").content("{}"));
		actions.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void getUserDoesntGetUserIfUnauthorized() throws Exception {
		int id = 1;
		User user = ModelGenerators.makeRandomUser(1);

		Mockito.when(service.get(id)).thenReturn(user);
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/users/1"));
		actions.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void getUserByIdBadDatatype() throws Exception {
		ResultActions actions = mvc
				.perform(MockMvcRequestBuilders.get("/users/string").contentType("application/json").content("{}"));
		actions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void getUserByIdNotFound() throws Exception {
		int id = 1;

		Mockito.when(service.get(id)).thenReturn(null);
		ResultActions actions = mvc
				.perform(MockMvcRequestBuilders.get("/users/1").contentType("application/json").content("{}"));
		actions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void addUser() throws Exception {
		Credentials creds = ModelGenerators.makeRandomCredentials();

		Mockito.when(service.add(creds.getUser())).thenReturn(creds.getUser());
		Mockito.when(cs.add(creds)).thenReturn(creds);
		ResultActions actions = mvc
				.perform(MockMvcRequestBuilders.post("/users").contentType("application/json").content(
						"{\"user\":{\"email\": \"notNullUnique\",\"firstName\": \"notNull\",\"lastName\":\"notNull\"},\"password\":\"notNull\"}"));
		actions.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	void addUserBadCredentialsObject() throws Exception {
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/users").contentType("application/json")
				.content("{\"user\":{\"email\": \"notNullUnique\",\"name\": \"notNull\"}}"));
		actions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void addUserNoUserObject() throws Exception {
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/users").contentType("application/json")
				.content("{\"password\":\"notNull\"}"));
		actions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	void addUserDuplicate() throws Exception {
		Credentials creds = ModelGenerators.makeRandomCredentials();

		Mockito.when(service.getUserByEmail(creds.getUser().getEmail())).thenReturn(creds.getUser());
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/users").contentType("application/json")
				.content("{\"user\":{\"email\": \"" + creds.getUser().getEmail()
						+ "\",\"firstName\": \"notNull\",\"lastName\":\"notNull\"},\"password\":\"notNull\"}"));
		actions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void updateUser() throws Exception {
		User user = ModelGenerators.makeRandomUser();

		Mockito.when(service.update(new User())).thenReturn(user);
		ResultActions actions = mvc
				.perform(MockMvcRequestBuilders.put("/users/0").contentType("application/json").content("{}"));
		actions.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void updateUserBadId() throws Exception {
		User user = ModelGenerators.makeRandomUser();

		Mockito.when(service.add(new User())).thenReturn(user);
		ResultActions actions = mvc
				.perform(MockMvcRequestBuilders.put("/users/string").contentType("application/json").content("{}"));
		actions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void deleteUser() throws Exception {
		User user = ModelGenerators.makeRandomUser();

		Mockito.when(service.delete(user.getUserId())).thenReturn(true);
		ResultActions actions = mvc.perform(MockMvcRequestBuilders.delete("/users/" + user.getUserId())
				.contentType("application/json").content("{}"));
		actions.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void deleteUserBadId() throws Exception {
		Credentials creds = ModelGenerators.makeRandomCredentials();

		Mockito.when(service.delete(0)).thenReturn(false);
		Mockito.when(cs.delete(creds.getCredentialsId())).thenReturn(false);
		ResultActions actions = mvc
				.perform(MockMvcRequestBuilders.delete("/users/string").contentType("application/json").content("{}"));
		actions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void changePasswordSuccess() throws Exception {
		
		User user = ModelGenerators.makeRandomUser();
		user.setEmail(TEST_EMAIL);
		Credentials creds = ModelGenerators.makeRandomCredentials(user);
		
		LinkedHashMap<String, String> newMap = new LinkedHashMap<String, String>();
		newMap.put("email", creds.getUser().getEmail());
		newMap.put("oldPassword", creds.getPassword());
		newMap.put("newPassword", "password");
		newMap.put("id", String.valueOf(creds.getCredentialsId()));
		
		System.out.println(creds);
		System.out.println(newMap.toString());
		
		Mockito.when(service.getLoggedInUser()).thenReturn(creds.getUser());
		Mockito.when(cs.getByEmail(creds.getUser().getEmail())).thenReturn(creds);
		Mockito.when(cs.changePassword(creds.getCredentialsId(), creds.getPassword())).thenReturn(creds.getPassword());

		ResultActions actions = mvc.perform(MockMvcRequestBuilders.post("/users/password")
				.contentType("application/json").content(new ObjectMapper().writeValueAsString(newMap)));
		actions.andExpect(MockMvcResultMatchers.status().isCreated());

	}

	@Test
	@WithMockUser(username = TEST_EMAIL)
	void changePasswordFailed() throws Exception {
		Credentials creds = ModelGenerators.makeRandomCredentials();

		Mockito.when(service.getLoggedInUser()).thenReturn(creds.getUser());
		Mockito.when(cs.getByEmail(TEST_EMAIL)).thenReturn(null);
//		Mockito.when(cs.changePassword(creds.getCredentialsId(), creds.getPassword()))
//		.thenReturn(creds.getPassword());

		ResultActions actions = mvc
				.perform(MockMvcRequestBuilders.post("/users/password").contentType("application/json"));
		actions.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
