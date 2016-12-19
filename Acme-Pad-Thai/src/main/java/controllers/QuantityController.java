package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.IngredientService;
import services.QuantityService;
import services.UnitService;
import domain.Ingredient;
import domain.Quantity;
import domain.Unit;

@Controller
@RequestMapping("/quantity")
public class QuantityController extends AbstractController {

	// Services ------------------------------------------
	
	@Autowired
	private QuantityService quantityService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private UnitService unitService;
	
	// Constructor ---------------------------------------
	
	public QuantityController() {
		super();
	}
	
	// Creating ------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "add")
	public ModelAndView create(int recipeId) {
		ModelAndView res;
		Quantity quantity;
		
		quantity = quantityService.create(recipeId);
		
		res = createEditModelAndView(quantity);
		res.addObject("recipeId", recipeId);
		
		return res;
	}
	
	// Editing -------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "edit")
	public ModelAndView edit(int recipeId, int quantityId) {
		ModelAndView res;
		Quantity quantity;
		
		quantity = quantityService.findOne(quantityId);
		res = createEditModelAndView(quantity);
		res.addObject("recipeId", recipeId);
		
		return res;
	}
	
	// Saving --------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Quantity quantity, BindingResult binding) {
		ModelAndView res;
		
		if (binding.hasErrors()) {
			res = createEditModelAndView(quantity);
		} else {
			try {
				quantityService.save(quantity);
				res = new ModelAndView("redirect:/recipe/display.do?recipeId=" 
									   + String.valueOf(quantity.getRecipe().getId()));
			} catch (Throwable th) {
				res = createEditModelAndView(quantity, "quantity.commit.error");
			}
		}
		
		return res;
		
	}
	
	// Deleting ------------------------------------------
	
	@RequestMapping(value="/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Quantity quantity, BindingResult binding) {
		ModelAndView res;
		
		try {
			quantityService.delete(quantity);
			res = new ModelAndView("redirect:/recipe/display.do?recipeId=" 
					   + String.valueOf(quantity.getRecipe().getId()));
		} catch (Throwable th) {
			res = createEditModelAndView(quantity, "quantity.commit.error");
		}
		
		return res;
	}
	
	// Ancillary methods ---------------------------------
	
	protected ModelAndView createEditModelAndView(Quantity quantity) {
		ModelAndView res;

		res = createEditModelAndView(quantity, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Quantity quantity, String message) {
		ModelAndView res;
		Collection<Ingredient> ingredients;
		Collection<Unit> units;

		res = new ModelAndView("quantity/edit");
		
		ingredients = ingredientService.findAll();
		units = unitService.findAll();
		
		res.addObject("quantity", quantity);
		res.addObject("ingredients", ingredients);
		res.addObject("units", units);
		res.addObject("message", message);

		return res;
	}
}
