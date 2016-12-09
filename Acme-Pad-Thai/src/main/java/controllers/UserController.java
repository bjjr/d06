package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;

import domain.SocialIdentity;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{
	
	//Services
	
	@Autowired
	private UserService userService;
	
	// Constructors
	
	public UserController(){
		super();
	}
	
	//Listing
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<User> users;
		
		users = userService.findAll();
		result = new ModelAndView("user/list");
		result.addObject("requestURI", "user/list.do");
		result.addObject("users", users);
		
		return result;

	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@RequestParam String user){
		ModelAndView result;
		Collection<User> users;
		Collection<User> searched;
		
		users = userService.findAll();
		
		if(user==""){
			result = new ModelAndView("user/list");
			result.addObject("requestURI", "user/list.do");
			result.addObject("users", users);
		}
		else{
			searched = userService.findByKeyword(user);
			
			result = new ModelAndView("user/list");
			result.addObject("requestURI", "user/list.do");
			result.addObject("users", searched);
		}
		
		return result;		
	}
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int userId) {
		ModelAndView result;
		User user;
		Collection<SocialIdentity> socialIdentities;
		

		user = userService.findOne(userId);
		socialIdentities = user.getSocialIdentities();
		
		result = new ModelAndView("user/display");
		result.addObject("requestURI", "user/display.do");
		result.addObject("user", user);
		result.addObject("socialIdentities", socialIdentities);

		return result;
	}	
}


