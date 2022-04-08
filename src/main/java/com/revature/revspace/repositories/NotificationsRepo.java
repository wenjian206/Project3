package com.revature.revspace.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import com.revature.revspace.models.Notifications;

public interface NotificationsRepo extends JpaRepository<Notifications, Integer> {

	public List<Notifications> findAll();
	public Notifications getNotificationBynotiId (String notiId);
	public List<Notifications> findAllNotificationsByUserReceive (int userId, Sort sort);
}
