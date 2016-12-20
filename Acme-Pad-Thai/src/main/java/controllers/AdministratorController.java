/* AdministratorController.java
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BillService;
import services.CampaignService;
import services.ContestService;
import services.SponsorService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------
	
	public AdministratorController() {
		super();
	}
	//Services ----------------------------------------------------------
	
	@Autowired
	private ContestService contestService;
	@Autowired
	private CampaignService campaignService;
	@Autowired
	private SponsorService sponsorService;
	@Autowired
	private BillService billService;
	
	// Action-1 ---------------------------------------------------------------		

	@RequestMapping("/action-1")
	public ModelAndView action1() {
		ModelAndView result;
				
		result = new ModelAndView("administrator/action-1");
		
		return result;
	}
	
	// Action-2 ---------------------------------------------------------------
	
	@RequestMapping("/action-2")
	public ModelAndView action2() {
		ModelAndView result;
				
		result = new ModelAndView("administrator/action-2");
		
		return result;
	}
	//Dashboard
	@RequestMapping(value="/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard(){
		
		ModelAndView result;
		Map<String,String> queries = new HashMap<String, String>();
		
		queries.put("administrator.avgRCpC",contestService.avgRecipeCopyPerContest().toString());
		queries.put("administrator.minRCpC",contestService.minRecipeCopyPerContest().toString());
		queries.put("administrator.maxRCpC",contestService.maxRecipeCopyPerContest().toString());
		queries.put("administrator.CmRCc",contestService.findContestMoreRecipesQualified().toString());
		queries.put("administrator.avgCpS",campaignService.avgCampignsPerSponsor().toString());
		queries.put("administrator.maxCpS",campaignService.maxCampignsPerSponsor().toString());
		queries.put("administrator.minCpS",campaignService.minCampignsPerSponsor().toString());
		queries.put("administrator.avgACpS",campaignService.avgActiveCampignsPerSponsor().toString());
		queries.put("administrator.maxACpS",campaignService.maxActiveCampignsPerSponsor().toString());
		queries.put("administrator.minACpS",campaignService.minActiveCampignsPerSponsor().toString());
		queries.put("administrator.avgPB",billService.avgPaidBills().toString());
		queries.put("administrator.stdPB",billService.stddevPaidBills().toString());
		queries.put("administrator.avgUB",billService.avgUnpaidBills().toString());
		queries.put("administrator.stdUB",billService.stddevUnpaidBills().toString());
		
		Map<String,Collection<String>> listQueries = new HashMap<String, Collection<String>>();
		
		listQueries.put("administrator.CbnC",sponsorService.companiesByNumCampaigns());
		listQueries.put("administrator.CbnB",sponsorService.companiesByNumBills());
		listQueries.put("administrator.inacS",sponsorService.inactiveSponsors());
		listQueries.put("administrator.CsltA",sponsorService.companiesSpentLessThanAverage());
		listQueries.put("administrator.CsalN",sponsorService.companiesSpentAtLeastNinety());
		
		result = new ModelAndView("administrator/dashboard");
		result.addObject("queries", queries);
		result.addObject("listQueries", listQueries);
		
		return result;
	}
	
}