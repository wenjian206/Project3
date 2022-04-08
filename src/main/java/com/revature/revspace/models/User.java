package com.revature.revspace.models;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.revature.revspace.util.UserSerializer;


@Entity
@Table(name="users")
@JsonSerialize(using = UserSerializer.class)
public class User  implements Serializable
{
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="user_id", updatable=false, nullable = false, unique=true)
	private int userId;

	@Column(name="email", length=100, nullable=false, unique=true)
	private String email;

	@Column(name="first_name", length=50, nullable = false)
	private String firstName;

	@Column(name="last_name", length=50, nullable = false)
	private String lastName;

	@Column(name="birthday")
	private Long birthday;

	@Column(name="revature_join_date")
	private Long revatureJoinDate;

	@Column(name="github_username", length=50, nullable=false)
	private String githubUsername;

	@Column(name="title", length=100, nullable=false)
	private String title;

	@Column(name="location", length=500, nullable=false)
	private String location;

	@Column(name="aboutme", length=1000, nullable = false)
	private String aboutMe;
	
	@OneToMany(mappedBy="userReceive", fetch=FetchType.EAGER)
	@JsonBackReference
	private List<Notifications> notifications;
	
	@ManyToMany
    @JoinTable(name = "followers", joinColumns = @JoinColumn(name = "followerId"), inverseJoinColumns = @JoinColumn(name = "userId"))
    private List<User> followers;
    
    @ManyToMany
    @JoinTable(name = "followers", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "followerId"))
    private List<User> following;

	public User() {
		this("", "", "", null, null, "", "", "", "");
	}
	
	public User(int userId, String email, String firstName, String lastName, Long birthday, Long revatureJoinDate,
			String githubUsername, String title, String location, String aboutMe, List<User> followers,
			List<User> following) {
		super();
		this.userId = userId;
	}
	
	public User(String email, String firstName, String lastName, Long birthday, Long revatureJoinDate, 
			String githubUsername, String title, String location, String aboutMe, List<User> followers, 
			List<User> following) {
		
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.revatureJoinDate = revatureJoinDate;
		this.githubUsername = githubUsername;
		this.title = title;
		this.location = location;
		this.aboutMe = aboutMe;
		this.followers = followers;
		this.following = following;
	}

	public User(String email, String firstName, String lastName, Long birthday, Long revatureJoinDate, String githubUsername, String title, String location, String aboutMe)
	{
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.revatureJoinDate = revatureJoinDate;
		this.githubUsername = githubUsername;
		this.title = title;
		this.location = location;
		this.aboutMe = aboutMe;

	}


	
	public User(int userId, String email, String firstName, String lastName, Long birthday, Long revatureJoinDate, String githubUsername, String title, String location, String aboutMe)
	{
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.revatureJoinDate = revatureJoinDate;
		this.githubUsername = githubUsername;
		this.title = title;
		this.location = location;
		this.aboutMe = aboutMe;
	}

	public int getUserId()
	{
		return userId;
	}

	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Long getBirthday()
	{
		return birthday;
	}

	public void setBirthday(Long birthday)
	{
		this.birthday = birthday;
	}

	public Long getRevatureJoinDate()
	{
		return revatureJoinDate;
	}

	public void setRevatureJoinDate(Long revatureJoinDate)
	{
		this.revatureJoinDate = revatureJoinDate;
	}

	public String getGithubUsername()
	{
		return githubUsername;
	}

	public void setGithubUsername(String githubUsername)
	{
		this.githubUsername = githubUsername;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getAboutMe()
	{
		return aboutMe;
	}

	public void setAboutMe(String aboutMe)
	{
		this.aboutMe = aboutMe;
	}
	

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public List<User> getFollowing() {
		return following;
	}
	
	


	public void setFollowing(List<User> following) {
		this.following = following;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return getUserId() == user.getUserId() && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getBirthday(), user.getBirthday()) && Objects.equals(getRevatureJoinDate(), user.getRevatureJoinDate()) && Objects.equals(getGithubUsername(), user.getGithubUsername()) && Objects.equals(getTitle(), user.getTitle()) && Objects.equals(getLocation(), user.getLocation()) && Objects.equals(getAboutMe(), user.getAboutMe());
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(getUserId(), getEmail(), getFirstName(), getLastName(), getBirthday(), getRevatureJoinDate(), getGithubUsername(), getTitle(), getLocation(), getAboutMe());
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", birthday=" + birthday + ", revatureJoinDate=" + revatureJoinDate + ", githubUsername="
				+ githubUsername + ", title=" + title + ", location=" + location + ", aboutMe=" + aboutMe
				+ ", followers=" + followers + ", following=" + following + "]";
	}
	

	}	
	



