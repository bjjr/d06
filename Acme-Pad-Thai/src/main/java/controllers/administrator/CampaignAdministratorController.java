package controllers.administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CampaignService;
import controllers.AbstractController;
import domain.Fee;

	@Controller
	@RequestMapping("/campaign/administrator")
	public class CampaignAdministratorController extends AbstractController {
		//Services ----------------------------------------------------------
		
		@Autowired
		private CampaignService campaignService;
		
		//Constructors ----------------------------------------------------------

		public CampaignAdministratorController() {
			super();
		}
		
		
		@RequestMapping(value = "/generate")
		public ModelAndView generateBill() {
			ModelAndView result;

				try {
					campaignService.generateBills();
					result = new ModelAndView("redirect:/");
				} catch (Throwable oops) {
					result = new ModelAndView("redirect:/");
				}
			

			return result;
		}
		
		//Ancillary Methods ----------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Fee fee) {
			ModelAndView result;
			
			result = createEditModelAndView(fee, null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Fee fee, String message) {
			ModelAndView result;
		
			result = new ModelAndView("fee/edit");
			result.addObject("fee", fee);
			result.addObject("message", message);
			
			return result;
		}	
	}
