package com.revature.revspace.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "groupthread")
public class GroupThread 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupId;
	
	@OneToOne
	@JoinColumn(name="infoId")
	private GroupInfo groupInfo;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

}

