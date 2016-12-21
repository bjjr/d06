package controllers.administrator;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ContestService;
import controllers.AbstractController;
import domain.Contest;

	@Controller
	@RequestMapping("/contest/administrator")
	public class ContestAdministratorController extends AbstractController {
		//Services ----------------------------------------------------------
		
		@Autowired
		private ContestService contestService;
		
		//Constructors ----------------------------------------------------------

		public ContestAdministratorController() {
			super();
		}
		
				
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Contest contest;

			contest = contestService.create();
			result = createEditModelAndView(contest);

			return result;
		}
		
		//Edition ----------------------------------------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int contestId) {
			ModelAndView result;
			Contest contest;
			
			contest = contestService.findOne(contestId);
			Assert.notNull(contest);
			result = createEditModelAndView(contest);
			
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Contest contest, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(contest);
			} else {
				try {
					contestService.save(contest);		
					result = new ModelAndView("redirect:/contest/list.do");
					result.addObject("message", "contest.commit.ok");
				} catch (Throwable oops) {
					result = createEditModelAndView(contest, "contest.commit.error");				
				}
			}

			return result;
		}
		// Deleting ------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Contest contest, BindingResult binding) {
			ModelAndView res;
			
			try {
				contestService.delete(contest);
				res = new ModelAndView("redirect:/contest/list.do");
			} catch (Throwable th) {
				res = createEditModelAndView(contest, "contest.commit.error");
			}
			
			return res;
	}
		
		// Set winners ----------------------------------------------------------
		@RequestMapping(value="/set", method = RequestMethod.GET)
		public ModelAndView set(){
			ModelAndView result;

			try {
				contestService.setWinners();
				result = new ModelAndView("redirect:/contest/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/contest/list.do");
			}
		

			
			return result;
		}

				
		//Ancillary Methods ----------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Contest contest) {
			ModelAndView result;
			
			result = createEditModelAndView(contest, null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Contest contest, String message) {
			ModelAndView result;
		
			result = new ModelAndView("contest/edit");
			result.addObject("contest", contest);
			result.addObject("message", message);
			
			return result;
		}	
	}
