package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.RecipeService;
import services.StepService;
import controllers.AbstractController;
import domain.Step;

@Controller
@RequestMapping("/step")
public class StepUserController extends AbstractController {
	// Services ----------------------------------------------------------

	@Autowired
	private StepService stepService;
	
	@Autowired
	private RecipeService recipeService;

	// Constructors ----------------------------------------------------------

	public StepUserController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "add")
	public ModelAndView create(int recipeId) {
		ModelAndView result;
		Step step;

		step = stepService.create();
		result = createEditModelAndView(step);
		result.addObject("recipeId", recipeId);

		return result;
	}

	// Edition ----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params="edit")
	public ModelAndView edit(int recipeId, int stepId) {
		ModelAndView result;
		Step step;

		step = stepService.findOne(stepId);
		result = createEditModelAndView(step);
		result.addObject("recipeId", recipeId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Step step, int recipeId, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(step);
		} else {
			try {
				stepService.save(step, recipeId);
				result = new ModelAndView(
						"redirect:/recipe/display.do?recipeId=" + recipeId);
			} catch (Throwable oops) {
				result = createEditModelAndView(step, "step.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Step step, int recipeId, BindingResult binding) {
		ModelAndView res;
		
		try {
			recipeService.deleteStep(step);
			res = new ModelAndView("redirect:/recipe/display.do?recipeId="+ recipeId);
		} catch (Throwable th) {
			res = createEditModelAndView(step, "step.commit.error");
		}
		
		return res;
	}

	// Ancillary Methods
	// ----------------------------------------------------------

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
