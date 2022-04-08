package com.revature.revspace.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "groupinfo")
public class GroupInfo implements Serializable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int infoId;
	
	private String groupName;
	private String description;
	private String interests;
	private String dateCreated;
	private String groupType;
	
	@OneToOne
  //@JoinTable(name = "followers", joinColumns = @JoinColumn(name = "followerId"), inverseJoinColumns = @JoinColumn(name = "userId"))
  @JoinTable(name = "users", joinColumns = @JoinColumn(name = "user_id"))
	private User owner;
}
