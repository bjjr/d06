package controllers.actor;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialIdentityService;
import controllers.AbstractController;
import domain.SocialIdentity;

	@Controller
	@RequestMapping("/socialIdentity/actor")
	public class SocialIdentityActorController extends AbstractController {
		//Services ----------------------------------------------------------
		
		@Autowired
		private SocialIdentityService socialIdentityService;
		@Autowired
		private ActorService actorService;
		
		//Constructors ----------------------------------------------------------

		public SocialIdentityActorController() {
			super();
		}
		
			
		// Listing ----------------------------------------------------------
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list(@RequestParam int actorId,@RequestParam(required=false) String message){
			
			ModelAndView result;
			Collection<SocialIdentity> socialIdentities;
			
			socialIdentities = actorService.findOne(actorId).getSocialIdentities();
					
			result = new ModelAndView("socialIdentity/list");
			result.addObject("socialIdentities", socialIdentities);
			result.addObject("requestURI", "socialIdentity/list.do");
			result.addObject("message", message);
			
			return result;
		}
		
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			SocialIdentity socialIdentity;

			socialIdentity = socialIdentityService.create();
			result = createEditModelAndView(socialIdentity);

			return result;
		}
		
		//Edition ----------------------------------------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int socialIdentityId) {
			ModelAndView result;
			SocialIdentity socialIdentity;
			
			socialIdentity = socialIdentityService.findOne(socialIdentityId);
			result = createEditModelAndView(socialIdentity);
			
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid SocialIdentity socialIdentity, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(socialIdentity);
			} else {
				try {
					socialIdentityService.save(socialIdentity);		
					result = new ModelAndView("redirect:/socialIdentity/actor/list.do?actorId="+actorService.findByPrincipal().getId());
					result.addObject("message", "socialIdentity.commit.ok");
				} catch (Throwable oops) {
					result = createEditModelAndView(socialIdentity, oops.getMessage());				
				}
			}

			return result;
		}
		
		// Deleting ------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(SocialIdentity socialIdentity, BindingResult binding) {
			ModelAndView res;
			
			try {
				socialIdentityService.delete(socialIdentity);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable th) {
				res = createEditModelAndView(socialIdentity);
			}
			
			return res;
	}
				
		//Ancillary Methods ----------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity) {
			ModelAndView result;
			
			result = createEditModelAndView(socialIdentity, null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(SocialIdentity socialIdentity, String message) {
			ModelAndView result;
		
			result = new ModelAndView("socialIdentity/edit");
			result.addObject("socialIdentity", socialIdentity);
			result.addObject("message", message);
			
			return result;
		}	
	}
