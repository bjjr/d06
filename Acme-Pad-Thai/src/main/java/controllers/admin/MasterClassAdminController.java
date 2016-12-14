package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MasterClassService;
import controllers.AbstractController;
import domain.MasterClass;

@Controller
@RequestMapping("/masterClass/admin")
public class MasterClassAdminController extends AbstractController {

	// Services -----------------------------------------
	
	@Autowired
	private MasterClassService masterClassService;
	
	// Constructor --------------------------------------
	
	public MasterClassAdminController() {
		super();
	}
	
	// Listing ------------------------------------------
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		String requestURI;
		Collection<MasterClass> masterClasses;
		
		requestURI = "masterClass/admin/list.do";
		masterClasses = masterClassService.findAll();
		
		res = new ModelAndView("masterClass/list");
		res.addObject("masterClasses", masterClasses);
		res.addObject("requestURI", requestURI);
		res.addObject("registered", null);
		
		return res;
	}
	
	// Promoting -----------------------------------------
	
	@RequestMapping(value = "/promote", method = RequestMethod.GET)
	public ModelAndView promote(@RequestParam int masterClassId) {
		ModelAndView res;
		
		masterClassService.promote(masterClassId);
		
		res = new ModelAndView("redirect:list.do");
		
		return res;
	}
	
	// Promoting -----------------------------------------
	
	@RequestMapping(value = "/demote", method = RequestMethod.GET)
	public ModelAndView demote(@RequestParam int masterClassId) {
		ModelAndView res;
		
		masterClassService.demote(masterClassId);
		
		res = new ModelAndView("redirect:list.do");
		
		return res;
	}
}
