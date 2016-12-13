package controllers.sponsor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BillService;
import services.CampaignService;
import domain.Bill;

@Controller
@RequestMapping("/bill/sponsor")
public class BillSponsorController {


	// Services ----------------------------------------------------------
	@Autowired
	private BillService billService;
	@Autowired
	private CampaignService campaignService;
	
	// Constructors ----------------------------------------------------------
	public BillSponsorController(){
		super();
	}
	
	// Paid ----------------------------------------------------------
		@RequestMapping(value="/paid", method = RequestMethod.GET)
		public ModelAndView paid(@RequestParam(required=true) int billId){
			ModelAndView result;
			
			billService.setPaid(billId);
			Integer campaignId = billService.findOne(billId).getCampaign().getId();
			
			result = new ModelAndView("redirect:/bill/sponsor/list.do?campaignId="+campaignId);
						
			return result;
		}
	
	// Listing ----------------------------------------------------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=true) int campaignId){
		
		ModelAndView result;
		Collection<Bill> bills;
		
		bills = campaignService.findOne(campaignId).getBills();
				
		result = new ModelAndView("bill/list");
		result.addObject("bills", bills);
		result.addObject("requestURI", "bill/list.do");
		
		return result;
	}
	
}
