package controllers.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RecipeService;
import services.StepService;
import services.UserService;
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
	
	@Autowired
	private UserService userService;

	// Constructors ----------------------------------------------------------

	public StepUserController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int recipeId) {
		ModelAndView result;
		Step step;

		Assert.isTrue(recipeService.findOne(recipeId).getUser().equals(userService.findByPrincipal()));
		step = stepService.create();
		result = createEditModelAndView(step, recipeId);

		return result;
	}

	// Edition ----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int recipeId, @RequestParam int stepId) {
		ModelAndView result;
		Step step;

		Assert.isTrue(recipeService.findOne(recipeId).getUser().equals(userService.findByPrincipal()));
		step = stepService.findOne(stepId);
		result = createEditModelAndView(step, recipeId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Step step, BindingResult binding, @RequestParam int recipeId) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(step, recipeId);
		} else {
			try {
				stepService.save(step, recipeId);
				result = new ModelAndView("redirect:/recipe/display.do?recipeId=" + recipeId);
			} catch (Throwable oops) {
				result = createEditModelAndView(step, recipeId, "step.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
	public ModelAndView delete(Step step, BindingResult binding, @RequestParam int recipeId) {
		ModelAndView res;
		
		try {
			recipeService.deleteStep(step);
			res = new ModelAndView("redirect:/recipe/display.do?recipeId=" + recipeId);
		} catch (Throwable th) {
			res = createEditModelAndView(step, recipeId, "step.commit.error");
		}
		
		return res;
	}

	// Ancillary Methods
	// ----------------------------------------------------------

	protected ModelAndView createEditModelAndView(Step step, int recipeId) {
		ModelAndView result;

		result = createEditModelAndView(step, recipeId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Step step, int recipeId, String message) {
		ModelAndView result;

		result = new ModelAndView("step/edit");
		result.addObject("step", step);
		result.addObject("message", message);
		result.addObject("recipeId", recipeId);

		return result;
	}
}
