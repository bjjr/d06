package controllers.actor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.MasterClassService;
import domain.MasterClass;

@Controller
@RequestMapping("/masterClass/actor")
public class MasterClassActorController extends AbstractController {

	// Services ----------------------------------------
	@Autowired
	private MasterClassService masterClassService;
	
	// Constructor -------------------------------------
	
	public MasterClassActorController() {
		super();
	}
	
	// Listing -----------------------------------------
	
	@RequestMapping(value = "/list-unregistered", method = RequestMethod.GET)
	public ModelAndView listUnregistered() {
		ModelAndView res;
		String requestURI;
		Collection<MasterClass> masterClasses;
		Boolean registered;
		
		masterClasses = masterClassService.findPrincipalNotRegisteredMasterClasses();
		requestURI = "/masterClass/actor/list-unregistered.do";
		registered = false;
		
		res = new ModelAndView("masterClass/list");
		res.addObject("requestURI", requestURI);
		res.addObject("masterClasses", masterClasses);
		res.addObject("registered", registered);
		
		return res;
	}
	
	@RequestMapping(value = "/list-registered", method = RequestMethod.GET)
	public ModelAndView listRegistered() {
		ModelAndView res;
		String requestURI;
		Collection<MasterClass> masterClasses;
		Boolean registered;
		
		masterClasses = masterClassService.findPrincipalRegisteredMasterClasses();
		requestURI = "/masterClass/actor/list-registered.do";
		registered = true;
		
		res = new ModelAndView("masterClass/list");
		res.addObject("requestURI", requestURI);
		res.addObject("masterClasses", masterClasses);
		res.addObject("registered", registered);
		
		return res;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam int masterClassId) {
		ModelAndView res;
		
		masterClassService.registerActor(masterClassId);
		
		res = new ModelAndView("redirect:list-unregistered.do");
		
		return res;
	}
}
