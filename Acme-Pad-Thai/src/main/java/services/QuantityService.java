package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.QuantityRepository;
import domain.Quantity;
import domain.Recipe;

@Service
@Transactional
public class QuantityService {
	
	//Managed repository
	
	@Autowired
	private QuantityRepository quantityRepository;
	
	// Supporting services
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;
	
	//Constructors
	public QuantityService(){
		super();
	}
	
	// Simple CRUD methods
	public Quantity create(int recipeId){
		Quantity result;
		Recipe r;
		
		r = recipeService.findOneToEdit(recipeId);
		result = new Quantity();
		result.setRecipe(r);
		
		return result;
	}
	
	public Quantity save(Quantity quantity){
		Assert.notNull(quantity);
		
		Quantity result;
		Recipe r;
		
		if (quantity.getId() == 0) {
			r = quantity.getRecipe();
			result = quantityRepository.save(quantity);
			r.addQuantity(result);
		} else {
			result = quantityRepository.save(quantity);
		}
		
		return result;
	}
	
	public void flush() {
		quantityRepository.flush();
	}
	
	public void delete(Quantity quantity){
		Assert.notNull(quantity);
		Assert.isTrue(quantity.getId()!=0);
		Assert.isTrue(quantityRepository.exists(quantity.getId()));
		
		quantityRepository.delete(quantity);
	}
	
	public Quantity findOne(int id){
		Assert.notNull(id);
		Assert.isTrue(id!=0);
		
		Quantity result;
		
		result = quantityRepository.findOne(id);
		Assert.notNull(result);
		
		Assert.isTrue(userService.findByPrincipal().getRecipes().contains(result.getRecipe()));
		
		return result;
	}

	public Quantity createDefaultQuantity() {
		Quantity res;
		
		res = new Quantity();
				
		res.setIngredient(ingredientService.findOne(154));
		res.setValue(1.0);
		res.setUnit(unitService.findOne(34));
		
		return res;
	}
	
}