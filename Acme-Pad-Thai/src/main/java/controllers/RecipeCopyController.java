package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import domain.RecipeCopy;

@Controller
@RequestMapping("/recipeCopy")
public class RecipeCopyController {


	// Services ----------------------------------------------------------
	@Autowired
	private ContestService contestService;
	
	// Constructors ----------------------------------------------------------
	public RecipeCopyController(){
		super();
	}
	
	// Listing ----------------------------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=true) int contestId){
		
		ModelAndView result;
		Collection<RecipeCopy> recipeCopies;
		
		recipeCopies = contestService.findRecipeCopiesByContest(contestId);
				
		result = new ModelAndView("recipeCopy/list");
		result.addObject("recipeCopies", recipeCopies);
		result.addObject("requestURI", "recipeCopy/list.do");
		
		return result;
	}
	
}
