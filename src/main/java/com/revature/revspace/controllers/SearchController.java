package com.revature.revspace.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.revspace.models.User;
import com.revature.revspace.services.UserService;
import com.revature.revspace.services.UserServiceImpl;

@RestController
@RequestMapping(value = "/users")
@CrossOrigin(origins = "*")
public class SearchController {
	
	private UserService uServ;
	
	private UserServiceImpl uServI;
	
	public SearchController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	public SearchController(UserService uServ) {
		super();
		this.uServ = uServ;
	}
	
	@GetMapping("/search/email")
	public ResponseEntity<List<User>> findUserByEmail(@RequestParam("email") String email){
		return ResponseEntity.status(200).body(this.uServ.getAllUserByEmail(email));
	}
	
	@GetMapping("/search/name")
	public ResponseEntity<List<User>> findUserByName(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname){
//		String[] userName = name.split("\\s+");
		return ResponseEntity.status(200).body(this.uServ.getAllUserByName(firstname, lastname));
	}
	
	

}
