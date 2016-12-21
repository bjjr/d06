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
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CampaignService;
import services.MasterClassService;
import domain.MasterClass;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	@Autowired
	private MasterClassService masterClassService;
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private ActorService actorService;

	// Constructors -----------------------------------------------------------
	
	public WelcomeController() {
		super();
	}
		
	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		MasterClass promotedMasterClass;
		String banner;
		String name;
		try{
			name = actorService.findByPrincipal().getName();
		}catch (Exception e) {
			name = "";
		}
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		promotedMasterClass = masterClassService.findRandomPromotedMasterClass();
		banner = campaignService.displayBannerStar();
		
		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("moment", moment);
		result.addObject("banner", banner);
		if (promotedMasterClass != null)
			result.addObject("masterClass", promotedMasterClass.getTitle());

		return result;
	}
}