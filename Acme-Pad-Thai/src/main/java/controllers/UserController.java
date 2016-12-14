package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;

import domain.SocialIdentity;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services

	@Autowired
	private UserService userService;

	// Constructors

	public UserController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;

		users = userService.findAll();
		result = new ModelAndView("user/list");
		result.addObject("requestURI", "user/list.do");
		result.addObject("users", users);

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, params = "search")
	public ModelAndView search(@RequestParam String user) {
		ModelAndView result;
		Collection<User> users;
		Collection<User> searched;

		users = userService.findAll();

		if (user == "") {
			result = new ModelAndView("user/list");
			result.addObject("requestURI", "user/list.do");
			result.addObject("users", users);
		} else {
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

	// Creation-----------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		User user;

		user = userService.create();
		result = createEditModelAndView(user);

		return result;
	}

	//Edition
	
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit() {
			ModelAndView result;
			User principal;

			principal = userService.findByPrincipal();
			result = createEditModelAndView(principal);

			return result;
		}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid User user, BindingResult binding) {
		ModelAndView result;
		String password;
		String newPassword;
		Md5PasswordEncoder encoder;

		if (binding.hasErrors()) {
			result = createEditModelAndView(user);
		} else {
			try {
				encoder = new Md5PasswordEncoder();
				password = user.getUserAccount().getPassword();
				newPassword = encoder.encodePassword(password, null);
				user.getUserAccount().setPassword(newPassword);
				
				userService.save(user);
				result = new ModelAndView("redirect:../welcome/index.do");
				result.addObject("messageStatus", "user.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(user, "user.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(User user) {
		ModelAndView result;

		result = createEditModelAndView(user, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(User user, String message) {
		ModelAndView result;
		
		result = new ModelAndView("user/edit");
		result.addObject("user", user);
		result.addObject("message", message);

		return result;
	}

}
