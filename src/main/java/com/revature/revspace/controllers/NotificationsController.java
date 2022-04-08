package com.revature.revspace.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.revspace.models.Notifications;
import com.revature.revspace.services.NotificationService;
import com.revature.revspace.services.UserService;

@RestController
@CrossOrigin(origins = "*")
public class NotificationsController {

	private NotificationService nServ;
	private UserService uServ;

	public NotificationsController() {

	}

	@Autowired
	public NotificationsController(NotificationService nServ, UserService uServ) {
		super();
		this.nServ = nServ;
		this.uServ = uServ;

	}

	/*
	 * @CrossOrigin(origins="*")
	 * 
	 * @GetMapping(value = "/notifications") public
	 * ResponseEntity<List<Notifications>> getNotificationById() { return
	 * ResponseEntity.status(200).body(nServ.getAllNotifications()); }
	 */
	// returns all notis by user in descending order
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/notifications/{userId}")
	public ResponseEntity<List<Notifications>> getNotificationById(@PathVariable("userId") String userId) {
		int numberToSend = 2;
		int numToEnter = 0;
		System.out.println("called");
		int userId1 = Integer.parseInt(userId);
		List<Notifications> listToSend = new ArrayList<>();
		List<Notifications> userNotiList = nServ.getNotificationByUser(userId1);
		if(userNotiList.size() != 0)
		while (numberToSend > 0) {
			listToSend.add(userNotiList.get(numToEnter));
			numToEnter++;
				numberToSend--;
				System.out.println(numToEnter);
				System.out.println(numberToSend);
			}

		return ResponseEntity.status(200).body(listToSend);

	}

	// creates noti
	@PostMapping(value = "/notifications", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Notifications> addNotification(@RequestBody Notifications nmodl) {
		System.out.println(nmodl);
		nServ.addNotification(nmodl);
		return ResponseEntity.status(201).body(nmodl);
	}

	
	@DeleteMapping("/notifications/{notiId}")
	public ResponseEntity<Notifications> deleteNotification(@PathVariable("notId") String notiId) {
		Optional<Notifications> notiOpt = Optional.ofNullable(nServ.getNotificationById(notiId));
		if (notiOpt.isPresent()) {
			nServ.deleteNotification(notiOpt.get());
			return ResponseEntity.status(200).body(notiOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

}
