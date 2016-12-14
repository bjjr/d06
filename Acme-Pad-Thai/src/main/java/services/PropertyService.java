package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PropertyRepository;
import domain.Ingredient;
import domain.Property;

@Service
@Transactional
public class PropertyService {

	// Managed repository --------------------------------

	@Autowired
	private PropertyRepository propertyRepository;

	// Supporting services -------------------------------

	@Autowired
	private ActorService actorService;
	
	// Constructors --------------------------------------

	public PropertyService() {
		super();
	}

	// Simple CRUD methods -------------------------------
	
	public Property create() {
		Assert.isTrue(actorService.checkAuthority("NUTRITIONIST"));
		
		Property res;
		res = new Property();
		
		Collection<Ingredient> ingredients;
		ingredients = new ArrayList<Ingredient>();
		
		res.setIngredients(ingredients);
		
		return res;
	}
	
	public Property save(Property p) {
		Assert.isTrue(actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(p);
		
		Property res;
		res = propertyRepository.save(p);
		
		return res;
	}
	
	public Property findOne(int id){
		Assert.notNull(id);
		Assert.isTrue(id!=0);
		
		Property result;
		
		result = propertyRepository.findOne(id);
		Assert.notNull(result);
		
		return result;
		
	}
	
	public void flush() {
		propertyRepository.flush();
	}
	
	public void delete(Property p) {
		Assert.isTrue(actorService.checkAuthority("NUTRITIONIST"));
		Assert.notNull(p);
		Assert.isTrue(p.getId() != 0);
		Assert.isTrue(propertyRepository.exists(p.getId()));
		Assert.isTrue(p.getIngredients().size() == 0, "The property to be deleted must not define any ingredient");
		
		propertyRepository.delete(p);
	}

	// Other business methods ----------------------------
	
	public Collection<Property> findAvailableProperties(Ingredient ingredient){
		Assert.notNull(ingredient);
		
		Collection<Property> used;
		Collection<Property> result;
		
		used = ingredient.getProperties();
		result = propertyRepository.findAll();
		result.removeAll(used);
		
		return result;
		
	
	}
}
