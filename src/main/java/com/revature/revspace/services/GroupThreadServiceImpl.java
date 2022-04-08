package com.revature.revspace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.revspace.models.GroupThread;
import com.revature.revspace.repositories.GroupInfoRepository;
import com.revature.revspace.repositories.GroupThreadRepository;

@Service
public class GroupThreadServiceImpl implements GroupThreadService 
{
	@Autowired
	GroupThreadRepository repo;
	
	@Autowired
	GroupInfoRepository infoRepo;
	
	@Override
	public GroupThread addGroupThread(GroupThread obj) 
	{
		GroupThread res = null;
		if(obj != null)
		{
			infoRepo.save(obj.getGroupInfo());
			res = repo.save(obj);
		}
		return res;
	}

	@Override
	public boolean isGroupExists(int groupId) {
		Optional<GroupThread> group = repo.findById(groupId);
		return group.isPresent();
	}

	@Override
	public String deleteGroup() {
		repo.deleteAll();
		return "Group deleted successfully!";
	}

	@Override
	public List<GroupThread> getGroups() {
		return (List<GroupThread>) repo.findAll();
	}

}