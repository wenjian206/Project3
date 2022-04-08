package com.revature.revspace.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.revspace.models.GroupThread;
import com.revature.revspace.services.GroupThreadService;

@RestController
@CrossOrigin(origins = "http//localhost:4200")
@RequestMapping("groupcreated")

public class GroupCreatedController {

	@Autowired
	GroupThreadService groupThreadService;
	
	/************************************************************************************************************/
	// Posts
	
	@PostMapping()
	public ResponseEntity<GroupThread> saveGroup(@RequestBody GroupThread group)
	{
		ResponseEntity<GroupThread> responseEntity = null;
		GroupThread result = null;
		if(groupThreadService.isGroupExists(group.getGroupId())) {
			responseEntity = new ResponseEntity<GroupThread>(HttpStatus.ALREADY_REPORTED);
		}else {
			result = groupThreadService.addGroupThread(group);
			responseEntity = new ResponseEntity<GroupThread>(result,HttpStatus.NOT_FOUND);; 
		}
		return responseEntity;
	}
	
	
	/************************************************************************************************************/
	// Puts	
	
//	@PutMapping("{groupId}")
//	public ResponseEntity<String> updateGroup(@PathVariable ("groupId") int groupId, 
//			@RequestBody GroupMembership group)
//	{
//		ResponseEntity<String> responseEntity = null;
//		String result = null;
//		if(groupService.isGroupExists(group.getGroupId())) {
//			result = groupService.updateGroup(groupId, group);
//			responseEntity = new ResponseEntity<String>(result,HttpStatus.ALREADY_REPORTED);
//		}	else {
//			result = "Group with the ID of "+group.getGroupId()+" Already In The System"; 
//			responseEntity = new ResponseEntity<String>(result,HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
	
	/************************************************************************************************************/
	// Deletes
	
	@DeleteMapping("{groupId}")
	public String  deleteGroup(@PathVariable("groupId") int groupId)
	{
		return "Deleting a group by groupId: "+groupId;
	}


	


/************************************************************************************************************/
// Gets

	
	@GetMapping
	public ResponseEntity<List<GroupThread>> getGroups()
	{
		ResponseEntity<List<GroupThread>> responseEntity = null;
		
		List<GroupThread> result = null;
		
		if(groupThreadService.getGroups().equals(null)) {
			responseEntity = new ResponseEntity<List<GroupThread>>(result,HttpStatus.NO_CONTENT);
		}else {
			result = groupThreadService.getGroups();
			responseEntity = new ResponseEntity<List<GroupThread>>(result,HttpStatus.OK);
		}
		return responseEntity;
	}

	
	
	
	
	
	
	
//@GetMapping("{groupId}")
//public ResponseEntity<GroupMembership> getGroupById(@PathVariable("groupId") int groupId)
//{
//	ResponseEntity<GroupMembership> responseEntity = null;
//	GroupMembership group = new GroupMembership();
//
//	if(groupService.isGroupExists(groupId)) {
//		group = groupService.getGroup(groupId);
//		responseEntity = new ResponseEntity<GroupMembership>(group, HttpStatus.OK);
//	} else {
//		responseEntity = new ResponseEntity<GroupMembership>(group, HttpStatus.NO_CONTENT);
//	}
//	return responseEntity;
//}


//List<GroupInfo> getGroup(GroupInfo group )
//public List<Doctor> getDoctoryBySpecialty(String spec); //Doctor.SpecialtyType type);
//public List<GroupInfo> getDoctoryByType(String type);
}
