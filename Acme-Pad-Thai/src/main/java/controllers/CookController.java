package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CookService;
import domain.Cook;

@Controller
@RequestMapping("/cook")
public class CookController extends AbstractController {

	// Services ---------------------------------------
	
	@Autowired
	private CookService cookService;
	
	// Constructors -----------------------------------
	
	public CookController() {
		super();
	}
	
	// Create -----------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Cook cook;
		
		cook = cookService.create();
		res = createEditModelAndView(cook);
		
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;
		Cook cook;
		
		cook = cookService.findByPrincipal();
		
		res = createEditModelAndView(cook);
		
		return res;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Cook cook, BindingResult binding) {
		ModelAndView res;
		String password;
		String newPassword;
		Md5PasswordEncoder encoder;
		
		if (binding.hasErrors()) {
			res = createEditModelAndView(cook);
		} else {
			try {
				encoder = new Md5PasswordEncoder();
				password = cook.getUserAccount().getPassword();
				newPassword = encoder.encodePassword(password, null);
				cook.getUserAccount().setPassword(newPassword);
				
				cookService.save(cook);
				res = new ModelAndView("redirect:../welcome/index.do");
				res.addObject("messageStatus", "user.commit.ok");
			} catch (Throwable oops) {
				res = createEditModelAndView(cook, "user.commit.error");
			}
		}
		
		return res;
	}
	
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Cook cook) {
		ModelAndView result;

		result = createEditModelAndView(cook, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Cook cook, String message) {
		ModelAndView result;

		result = new ModelAndView("cook/edit");
		result.addObject("cook", cook);
		result.addObject("message", message);

		return result;
	}
	
}
