package controllers.sponsor;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Campaign;

	@Controller
	@RequestMapping("/campaign/sponsor")
	public class CampaignSponsorController extends AbstractController {
		//Services ----------------------------------------------------------
		
		@Autowired
		private CampaignService campaignService;
		@Autowired
		private SponsorService sponsorService;
		
		//Constructors ----------------------------------------------------------

		public CampaignSponsorController() {
			super();
		}
		
			
		// Listing ----------------------------------------------------------
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(){
			
			ModelAndView result;
			Collection<Campaign> campaigns;
			
			campaigns = sponsorService.findByPrincipal().getCampaigns();
					
			result = new ModelAndView("campaign/list");
			result.addObject("campaigns", campaigns);
			result.addObject("requestURI", "campaign/list.do");
			
			return result;
		}
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Campaign campaign;

			campaign = campaignService.create();
			result = createEditModelAndView(campaign);

			return result;
		}
		
		//Edition ----------------------------------------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int campaignId) {
			ModelAndView result;
			Campaign campaign;
			
			campaign = campaignService.findOne(campaignId);
			Assert.notNull(campaign);
			result = createEditModelAndView(campaign);
			
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Campaign campaign, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(campaign);
			} else {
				try {
					campaignService.save(campaign);		
					result = new ModelAndView("redirect:/campaign/sponsor/list.do");
					result.addObject("message", "campaign.commit.ok");
				} catch (Throwable oops) {
					result = createEditModelAndView(campaign, "campaign.commit.error");				
				}
			}

			return result;
		}
		
		// Deleting ------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Campaign campaign, BindingResult binding) {
			ModelAndView res;
			
			try {
				campaignService.delete(campaign);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable th) {
				res = createEditModelAndView(campaign, "campaign.commit.error");
			}
			
			return res;
	}
				
		//Ancillary Methods ----------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Campaign campaign) {
			ModelAndView result;
			
			result = createEditModelAndView(campaign, null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Campaign campaign, String message) {
			ModelAndView result;
		
			result = new ModelAndView("campaign/edit");
			result.addObject("campaign", campaign);
			result.addObject("message", message);
			
			return result;
		}	
	}
