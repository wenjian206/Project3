package com.revature.revspace.services;

import java.util.List;

import com.revature.revspace.models.GroupThread;

public interface GroupThreadService {
	
	
	public GroupThread addGroupThread(GroupThread obj) ;
	
//	public String updateGroup(int groupId, GroupMembership group);
//	
//	
	public boolean isGroupExists(int groupId);
//	
	public String deleteGroup();
//	public String deleteGroup(int groupId);
//	
//	
//	public GroupMembership getGroup(int groupId);
//	
	public List<GroupThread> getGroups();
//	public List<GroupMembership> getGroupByName(String groupName);
//	public List<GroupMembership> getGroupByType(String groupType);
	  
}
