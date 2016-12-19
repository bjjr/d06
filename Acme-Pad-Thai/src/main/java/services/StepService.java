package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StepRepository;
import domain.Recipe;
import domain.Step;


@Service
@Transactional
public class StepService {
	
	//Managed repository
	
	@Autowired
	private StepRepository stepRepository;
	
	// Supporting services
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private RecipeService recipeService;
	
	
	//Constructors
	
	public StepService(){
		super();
	}
	
	// Simple CRUD methods
	
	public Step create(){
		Step result;
		
		result = new Step();
		
		return result;
	}
	
	public Step save(Step step, int recipeId){
		Assert.notNull(step);
		
		Step result;
		Recipe recipe;
		
		recipe = recipeService.findOne(recipeId);
		result = stepRepository.save(step);
		recipe.addStep(result);
		recipeService.save(recipe);
		
		return result;
		
	}
	
	public void flush() {
		stepRepository.flush();
	}
	
	public void delete(Step step){
		Assert.notNull(step);
		Assert.isTrue(step.getId()!=0);
		Assert.isTrue(stepRepository.exists(step.getId()));
		
		stepRepository.delete(step.getId());
		
	}
	
	public Step findOne(int id){
		Assert.notNull(id);
		Assert.isTrue(id!=0);
		
		Step result;
		
		result = stepRepository.findOne(id);
		Assert.notNull(result);
		
		return result;
		
	}
	
	// Other business methods
	
	public Double findAvgStepsRecipe(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		
		Double result;
		
		result = stepRepository.findAvgStepsRecipe();
		Assert.notNull(result);
		
		return result;
		
	}
	
	public Double findStandardDeviationStepsRecipe(){
		Assert.isTrue(actorService.checkAuthority("ADMINISTRATOR"));
		
		Double result;
		
		result = stepRepository.findStandardDeviationStepsRecipe();
		Assert.notNull(result);
		
		return result;
	}
	
	public Recipe findRecipeByStep(int stepId){
		Recipe result;
		
		result = stepRepository.findRecipeByStep(stepId);
		Assert.notNull(result);
		
		return result;
}
	public Step createDefaultStep() {
		Assert.isTrue(actorService.checkAuthority("USER"));
		
		Step res;
		
		res = create();
		
		res.setDescription("This is an example step / Esto es un paso de ejemplo");
		
		return res;
	}
	
}
