package controllers.user;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StepService;
import controllers.AbstractController;
import domain.Step;

	@Controller
	@RequestMapping("/step/user")
	public class StepUserController extends AbstractController {
		//Services ----------------------------------------------------------
		
		@Autowired
		private StepService stepService;
		
		//Constructors ----------------------------------------------------------

		public StepUserController() {
			super();
		}
		
				
		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Step step;

			step = stepService.create();
			result = createEditModelAndView(step);

			return result;
		}
		
		//Edition ----------------------------------------------------------
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int stepId) {
			ModelAndView result;
			Step step;
			
			step = stepService.findOne(stepId);
			result = createEditModelAndView(step);
			
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Step step, BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors()) {
				result = createEditModelAndView(step);
			} else {
				try {
					stepService.save(step);		
					result = new ModelAndView("redirect:/recipe/display.do?recipeId="+stepService.findRecipeByStep(step.getId()));
				} catch (Throwable oops) {
					result = createEditModelAndView(step, oops.getMessage());				
				}
			}

			return result;
		}
				
		//Ancillary Methods ----------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Step step) {
			ModelAndView result;
			
			result = createEditModelAndView(step, null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(Step step, String message) {
			ModelAndView result;
		
			result = new ModelAndView("step/edit");
			result.addObject("step", step);
			result.addObject("message", message);
			
			return result;
		}	
	}
