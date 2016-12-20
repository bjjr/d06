/* WelcomeController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MasterClassService;
import domain.MasterClass;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	@Autowired
	private MasterClassService masterClassService;

	// Constructors -----------------------------------------------------------
	
	public WelcomeController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required=false, defaultValue="John Doe") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		MasterClass promotedMasterClass;
		
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		promotedMasterClass = masterClassService.findRandomPromotedMasterClass();
				
		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		if (promotedMasterClass != null)
			result.addObject("masterClass", promotedMasterClass.getTitle());

		return result;
	}
}