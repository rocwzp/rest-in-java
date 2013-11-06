package com.sivalabs.springmvcrest.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sivalabs.springmvcrest.entities.User;
import com.sivalabs.springmvcrest.services.UserService;
import com.sivalabs.springmvcrest.web.model.AjaxResponse;
import com.sivalabs.springmvcrest.web.model.AjaxResponseBuilder;
import com.sivalabs.springmvcrest.web.model.UsersResource;

/**
 * @author Siva
 *
 */
@Controller
@RequestMapping("/users")
public class UserEndPoint
{
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping(value="/", method=RequestMethod.GET, 
			produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public UsersResource welcome()
	{
		List<User> users = userService.findAllUsers();
		UsersResource resource = new UsersResource();
		resource.setUsers(users);
		return resource;
	}
	
	@ResponseBody
	@RequestMapping(value="/", method=RequestMethod.POST,
			consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public AjaxResponse createUser(@RequestBody User user)
	{
		try {
			userService.createUser(user);
			return new AjaxResponseBuilder().ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return new AjaxResponseBuilder().notOk().error(e.getMessage()).build();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/checkUserNameExists", produces="application/json")
	public AjaxResponse checkUserNameExists(@RequestParam("userName") String userName)
	{
		boolean exists = userService.checkUserNameExists(userName);
		if(exists){
			return new AjaxResponseBuilder().notOk().error("UserName ["+userName+"] already exist").build();
		} else {
			return new AjaxResponseBuilder().ok().build();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/checkEmailExists", produces="application/json")
	public AjaxResponse checkEmailExists(@RequestParam("email") String email)
	{
		boolean exists = userService.checkEmailExists(email);
		if(exists){
			return new AjaxResponseBuilder().notOk().error("Email ["+email+"] already exist").build();
		} else {
			return new AjaxResponseBuilder().ok().build();
		}
	}
	
}
