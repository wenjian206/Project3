package com.revature.revspace.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import com.revature.revspace.app.RevSpaceWebServiceApplication;
import com.revature.revspace.models.User;
import com.revature.revspace.repositories.UserRepo;
import com.revature.revspace.testutils.ModelGenerators;

@TestPropertySource("classpath:application-test.properties")
@SpringBootTest(classes = RevSpaceWebServiceApplication.class)
public class UserServiceImplTests {
	private static Random RANDOM = new Random(System.currentTimeMillis());

	@Autowired
	UserService userService;

	@MockBean
	UserRepo repo;

	@Test
	void getRepoGetsRepo() {
		Assertions.assertNotNull(this.userService.getRepo());
	}

	@Test
	void getIdForUserGetsIdForUser() {
		int expectedId = RANDOM.nextInt(100) + 1; // random int in range [1,100]
		User user = ModelGenerators.makeRandomUser(expectedId);
		int actualId = this.userService.getIDFor(user);
		Assertions.assertEquals(expectedId, actualId);
	}

	@Test
	void getUserByEmailGetsUser() {
		User expectedUser = ModelGenerators.makeRandomUser(1);
		String email = expectedUser.getEmail();
		Mockito.when(this.repo.findByEmail(email)).thenReturn(expectedUser);
		User actualUser = this.userService.getUserByEmail(email);
		Assertions.assertEquals(expectedUser, actualUser);
	}

	@Test
	void getUserByEmailDoesntGetUserIfNoUserWithEmailExists() {
		String email = ModelGenerators.makeRandomAlphaString(5, 10);
		Mockito.when(this.repo.findByEmail(email)).thenReturn(null);
		User actualUser = this.userService.getUserByEmail(email);
		Assertions.assertNull(actualUser);
	}

	@Test
	void getUserByNullEmailDoesntGetUser() {
		Mockito.when(this.repo.findByEmail(null)).thenReturn(null);
		User actualUser = this.userService.getUserByEmail(null);
		Assertions.assertNull(actualUser);
	}

	@Test
	void getLoggedInUserDoesntGetUserOutsideRequestScope() {
		Assertions.assertNull(this.userService.getLoggedInUser());
	}
	
	@Test
	void getAllUserByNameGetsUser()
	{
		User expectedUser = ModelGenerators.makeRandomUser();
		List<User> uList = new ArrayList<User>();
		uList.add(expectedUser);
		Mockito.when(this.repo.findByFirstName(expectedUser.getFirstName())).thenReturn(uList);
		List<User> actualUser = this.userService.getAllUserByName(expectedUser.getFirstName(), expectedUser.getLastName());
		Assertions.assertEquals(uList, actualUser);
		
	}
	
	@Test
	void getAllUserByEmailGetsUser()
	{
		User expectedUser = ModelGenerators.makeRandomUser();
		List<User> uList = new ArrayList<User>();
		uList.add(expectedUser);
		Mockito.when(this.repo.findAll()).thenReturn(uList);
		List<User> actualUser = this.userService.getAllUserByEmail(expectedUser.getEmail());
		Assertions.assertEquals(uList, actualUser);
		
	}
}
