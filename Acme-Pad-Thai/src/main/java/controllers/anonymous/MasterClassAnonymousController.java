package controllers.anonymous;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.MasterClassService;
import domain.MasterClass;

@Controller
@RequestMapping("/masterClass")
public class MasterClassAnonymousController extends AbstractController {

	// Services -------------------------------------------------
	
	@Autowired
	private MasterClassService masterClassService;
	
	// Constructors ---------------------------------------------
	
	public MasterClassAnonymousController() {
		super();
	}
	
	// Listing --------------------------------------------------
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<MasterClass> masterClasses;
		String requestURI;
		Boolean registered;
		
		masterClasses = masterClassService.findAll();
		requestURI = "/masterClass/list.do";
		registered = null;
		
		res = new ModelAndView("masterClass/list");
		res.addObject("masterClasses", masterClasses);
		res.addObject("requestURI", requestURI);
		res.addObject("registered", registered);
		
		return res;
	}
}
