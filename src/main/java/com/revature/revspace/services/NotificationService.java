package com.revature.revspace.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.revature.revspace.models.Notifications;
import com.revature.revspace.repositories.NotificationsRepo;
import com.revature.revspace.repositories.UserRepo;

@Service
public class NotificationService {
	
	private NotificationsRepo nRepo;
	private UserRepo uRepo;
	
	public NotificationService () {
		
	}

	@Autowired
	public NotificationService (NotificationsRepo nRepo, UserRepo uRepo) {
		super ();
		this.nRepo = nRepo;
		this.uRepo = uRepo;
		
	}

	public List<Notifications> getAllNotifications() {
		// TODO Auto-generated method stub
		return this.nRepo.findAll();
	}
	
	public Notifications getNotificationById(String notiId) {
		// TODO Auto-generated method stub
		List<Notifications> nList = this.nRepo.findAll();
		int notificationId = Integer.parseInt(notiId);
		for (Notifications noti: nList) {
			if (noti.getNotiId() == notificationId) {
				return noti;
			}
		}
		return null;
	}
	
	public List<Notifications> getNotificationByUser(int userId) {
		return nRepo.findAllNotificationsByUserReceive(userId, Sort.by("notiId").descending());
	}

	public void addNotification(Notifications nmodl) {
		nmodl.setDateAndTime(LocalDate.now());
		// TODO Auto-generated method stub
		nRepo.save(nmodl);
		
	}


	public void deleteNotification(Notifications notifications) {
		// TODO Auto-generated method stub
		nRepo.delete(notifications);
		
	}

	
}
