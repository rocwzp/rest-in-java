package com.sivalabs.springmvcrest.web.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.sivalabs.springmvcrest.entities.User;

/**
 * @author Siva
 *
 */
@XmlRootElement(name="UsersResponse")
public class UsersResource
{
	
	private List<User> users;
	public void setUsers(List<User> users)
	{
		this.users = users;
	}
	
	@XmlElementWrapper(name="users")
	@XmlElement(name="user")
	public List<User> getUsers()
	{
		return users;
	}
}
