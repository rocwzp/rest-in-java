package com.sivalabs.springmvcrest.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sivalabs.springmvcrest.entities.User;
import com.sivalabs.springmvcrest.services.UserService;

/**
 * @author Siva
 *
 */
@Controller
public class WelcomeController
{
	@Autowired
	private UserService userService;
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping("/welcome")
	public String welcome(Model model)
	{
		model.addAttribute("userName", "Siva");
		
		try {
			List<User> users = userService.findAllUsers();
			model.addAttribute("users", users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "welcome";
	}
	
	@RequestMapping(value="/createUser", method=RequestMethod.GET)
	public String createUserForm(Model model)
	{
		model.addAttribute("user", new User());
		return "createUser";
	}
	
	@RequestMapping(value="/createUser", method=RequestMethod.POST)
	public String createUser(@ModelAttribute("user") User user, Model model)
	{
		try {
			userService.createUser(user);
			return "redirect:welcome";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("ERROR", e.getMessage());
			return "createUser";
		}
	}
	
	
}
