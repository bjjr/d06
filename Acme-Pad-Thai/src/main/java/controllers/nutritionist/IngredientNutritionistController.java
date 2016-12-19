package controllers.nutritionist;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.IngredientService;
import services.PropertyService;

import controllers.AbstractController;
import domain.Ingredient;
import domain.Property;

@Controller
@RequestMapping("/ingredient/nutritionist")
public class IngredientNutritionistController extends AbstractController {

	// Services

	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private PropertyService propertyService;

	// Constructors

	public IngredientNutritionistController() {
		super();
	}

	// Listing

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Ingredient> ingredients;

		ingredients = ingredientService.findAll();

		result = new ModelAndView("ingredient/list");
		result.addObject("requestURI", "ingredient/nutritionist/list.do");
		result.addObject("ingredients", ingredients);

		return result;
	}

	// Creation
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Ingredient ingredient;

		ingredient = ingredientService.create();
		result = createEditModelAndView(ingredient);

		return result;
	}

	// Edition

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int ingredientId) {
		ModelAndView result;
		Ingredient ingredient;

		ingredient = ingredientService.findOne(ingredientId);
		result = createEditModelAndView(ingredient);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Ingredient ingredient, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(ingredient);
		} else {
			try {
				ingredientService.save(ingredient);

				result = new ModelAndView("redirect:list.do");
				result.addObject("message", "ingredient.commit.ok");
			} catch (Throwable oops) {
				result = createEditModelAndView(ingredient, "ingredient.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Ingredient ingredient, BindingResult binding) {
		ModelAndView result;

		try {
			ingredientService.delete(ingredient);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(ingredient, "ingredient.commit.error");
		}

		return result;
	}
	
	//Add properties
	
	@RequestMapping(value = "/addProperty", method = RequestMethod.GET)
	public ModelAndView addProperty(@RequestParam int ingredientId) {
		ModelAndView result;
		Ingredient ingredient;
		Collection<Property> properties;
		
		ingredient = ingredientService.findOne(ingredientId);
		properties = propertyService.findAvailableProperties(ingredient);
	
		result = new ModelAndView("ingredient/addProperty");
		result.addObject("ingredient", ingredient);
		result.addObject("properties", properties);

		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam int propertyId, @RequestParam int ingredientId) {
		ModelAndView result;
		Ingredient ingredient;
		Property property;
		Collection<Property> properties;
		
		ingredient = ingredientService.findOne(ingredientId);
		property = propertyService.findOne(propertyId);
		
			try {
				ingredientService.addProperty(ingredient, property);
				result = new ModelAndView("redirect:list.do");
				result.addObject("message", "ingredient.commit.ok");
			} catch (Throwable oops) {
				properties = propertyService.findAvailableProperties(ingredient);
				result = new ModelAndView("ingredient/addProperty");
				result.addObject("ingredient", ingredient);
				result.addObject("properties", properties);
				result.addObject("message", "ingredient.commit.error");
			}

		return result;
	}
	
	//Remove properties
	
	@RequestMapping(value = "/removeProperty", method = RequestMethod.GET)
	public ModelAndView removeProperty(@RequestParam int ingredientId) {
		ModelAndView result;
		Ingredient ingredient;
		Collection<Property> properties;
		
		ingredient = ingredientService.findOne(ingredientId);
		properties = ingredient.getProperties();
		result = new ModelAndView("ingredient/removeProperty");
		result.addObject("ingredient", ingredient);
		result.addObject("properties", properties);

		return result;
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam int propertyId, @RequestParam int ingredientId) {
		ModelAndView result;
		Ingredient ingredient;
		Property property;
		Collection<Property> properties;
		
		ingredient = ingredientService.findOne(ingredientId);
		property = propertyService.findOne(propertyId);
			try {
				ingredientService.removeProperty(ingredient, property);
				result = new ModelAndView("redirect:list.do");
				result.addObject("message", "ingredient.commit.ok");
			} catch (Throwable oops) {
				properties = ingredient.getProperties();
				result = new ModelAndView("ingredient/removeProperty");
				result.addObject("ingredient", ingredient);
				result.addObject("properties", properties);
				result.addObject("message", "ingredient.commit.error");
			}

		return result;
	}
	

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Ingredient ingredient) {
		ModelAndView result;

		result = createEditModelAndView(ingredient, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Ingredient ingredient,
			String message) {
		ModelAndView result;

		result = new ModelAndView("ingredient/edit");
		result.addObject("ingredient", ingredient);
		result.addObject("message", message);

		return result;
	}

}
